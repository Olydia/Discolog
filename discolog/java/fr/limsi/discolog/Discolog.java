package fr.limsi.discolog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;
import alice.tuprolog.Var;
import edu.wpi.cetask.DecompositionClass;
import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.DecompositionClass.Step;
import edu.wpi.cetask.Description.Condition;
import edu.wpi.cetask.TaskClass.Grounding;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Shell;
import edu.wpi.cetask.TaskClass;
import edu.wpi.cetask.TaskEngine;
import edu.wpi.cetask.TaskModel;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

/**
 * New main class for Discolog that extends default Disco agent to add breakdown
 * detection and recovery.
 */
public class Discolog extends Agent {

	/**
	 * Use this main method instead of {@link Disco#main(String[])} to start
	 * system with console.
	 */
	/*public static void main(String[] args) {
		Agent agent = new Discolog("agent");
		// restrict to performing only a single primitive action on each turn
		// so we have more control over example
		agent.setMax(1);
		Interaction interaction = new Interaction(agent, new User("user"),
				args.length > 0 && args[0].length() > 0 ? args[0] : null);
		interaction.start(false);

	}*/

	// TODO add private fields here to hold Prolog engine, etc.

	public Discolog(String name) {
		super(name);
		TaskEngine.DEBUG = true;
	}

	private boolean recover(Interaction interaction) {
		candidates.clear();
		findCandidates(interaction.getDisco().getTops());
		System.out.println(interaction.getDisco().getTops());
		if (candidates.isEmpty()) {
			System.out.println("No recovery candidates!");
			return false;
		} else {
			for (Candidate candidate : candidates) {
				Plan recovery = invokePlanner(candidate.plan,candidate.condition.getScript());
				if (recovery != null) {
					System.out.println("Found recovery plan for " + candidate.plan.getGoal().toString());
					Disco disco = interaction.getDisco();
					// splice in recovery plan
					disco.getFocus().add(recovery);
					recovery.setContributes(true); // so not interruption
					return true;
				}
			}
			System.out.println("No recovery plans found!");
			return false;
		}
	}

	private Plan invokePlanner(Plan candidate, String condition) {
		// this should invoke Prolog planner
		// return new Plan(candidate.getGoal().getType().getEngine().getTaskClass("Open").newInstance());
		TaskEngine d = candidate.getGoal().getType().getEngine();
		ArrayList<String> JavaPlan = new ArrayList<String>();
		//ArrayList<String> Init = new ArrayList<String>();
		//;
		String Goal = condition;
		//String initial = "islocked";
		JavaPlan = CallStripsPlanner(EvalConditions(PlanConstructor.conditions), Goal);
		Plan p = newPlan(d, "recovery");
		for (int i = 0; i < JavaPlan.size() - 1; i++) {
			p.add(newPlan(d, JavaPlan.get(i)));
		}
		if (candidate.isFailed()) {
			for (Plan s : candidate.getSuccessors()) {
				s.requires(p);
				s.unrequires(candidate);
			}
		}

		return p;
	}

	private static Plan newPlan(TaskEngine disco, String name) {
		return new Plan(disco.getTaskClass(name).newInstance());
	}

	private final List<Candidate> candidates = new ArrayList<Candidate>();

	private static class Candidate {
		private final Plan plan;
		private final Condition condition;

		private Candidate(Plan plan, Condition condition) {
			this.plan = plan;
			this.condition = condition;
		}
	}

	/**
	 * @return candidates for recovery planning target.
	 * 
	 *         First cut approach: plans in given tree that are neither done nor
	 *         live nor blocked (which means that their precondition must be
	 *         false)
	 */
	private void findCandidates(List<Plan> children) {
		// check there is a prolog version of the goal condition
		for (Plan plan : children) {
			TaskClass type = plan.getGoal().getType();
			if (type.getPrecondition() != null
					&& !(plan.isDone() || plan.isLive() || plan.isBlocked()||plan.isFailed()))
				candidates.add(new Candidate(plan, type.getPrecondition()));
			else if (type.getPostcondition() != null && plan.isFailed()
					&& type.isSufficient()) // post cond
				candidates.add(new Candidate(plan, type.getPostcondition()));
			if (plan.isLive() && !plan.isPrimitive() && !plan.isDecomposed())
				for (DecompositionClass c : plan.getType().getDecompositions()) {
					if (c.getApplicable() != null)
						candidates.add(new Candidate(plan, c.getApplicable()));
				}
			findCandidates(plan.getChildren());
		}
	}

	@Override
	public Plugin.Item respondIf(Interaction interaction, boolean guess) {
	   Disco disco = interaction.getDisco();
      disco.decomposeAll();
      // do not call super.respondIf() here to avoid automatic retry of other recipes
      Plugin.Item item = generateBest(interaction);
		// if current toplevel goal is not done and we have
		// nothing to do, then we have a breakdown to recover from
		Plan focus = interaction.getFocus();
		if (focus != null && !interaction.getDisco().getTop(focus).isDone()
				&& item == null) {
			if (recover(interaction)){
				//throw new Shell.Quit();
				return super.respondIf(interaction, guess); // new response with
				// new plan
			}
				
			else
				return null;
		} else
			return item;
	}

							// ******************************* Planner Call ********************************************

	private static ArrayList<String> getPlannerOutput(Term plan) {
		ArrayList<String> Output = new ArrayList<String>();
		String init;
		if(plan == null)
			System.out.println("No recovery STRIPS plan found !");
		else{
		Pattern p = Pattern.compile("(do\\()");
		String[] splitString = (p.split(plan.toString()));
		// remove init, parenthisis and init argument to obtain only the name of
		// actions

		for (String element : splitString) {
			String elem = element.replaceAll("(init)(\\)+)", "");
			elem = elem.replaceAll(",", "");
			init = elem.replaceAll("\\(.*\\)", "");
			Output.add(init);

		}
		Collections.reverse(Output);
		}
		return Output;

	}

	public static ArrayList<String> CallStripsPlanner(List<String> Initial_state,
			String Goal) {
		Term Plan = null;
		ArrayList<String> JavaPlan = new ArrayList<String>();
		Prolog engine = new Prolog();
		
		try {
			ClassLoader classloader = Thread.currentThread()
					.getContextClassLoader();
			InputStream planner = Discolog.class
					.getResourceAsStream("/test-2p/test_instance.pl");
			Theory theory = new Theory(planner);
			engine.setTheory(theory);
			Strips_Input(Initial_state, Goal, engine);
			// The request for STRIPS.
			Struct goal = new Struct("test1", new Var("X"));
			SolveInfo info = engine.solve(goal);

			// Results
			if (!info.isSuccess())
				System.out.println("no.");
			else {// main case
				Plan = info.getVarValue("X");
			}
		} catch (InvalidTheoryException | IOException | NoSolutionException ex) {
			throw new RuntimeException(ex);
		}

		JavaPlan = getPlannerOutput(Plan);
		return JavaPlan;
	}

	private static void Strips_Input(List<String> Initial_state, String Goal,
			Prolog engine) {
		// Add the init state and the planner call for the goal
		//Theory init;
		try {
			for(String init : Initial_state){
				//System.out.println(init);
				engine.addTheory(new Theory("strips_holds(" + init + ",init)."));
			}
			Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["
					+ Goal + "],30,Plan).");
			//engine.addTheory(init);
			engine.addTheory(PlannerCall);
		} catch (InvalidTheoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String Goal = "isopen";

	}

	// **********************************************************************************************************
	 private TaskClass newTask (String id, boolean primitive, String precondition, String postcondition, String grounding) {
	      if ( !primitive && grounding != null ) 
	         throw new IllegalArgumentException("Non-primitive cannot have grounding script: "+id);
	      TaskClass task = new TaskClass(model, id,
	            new Precondition(precondition, true, disco), 
	            new Postcondition(postcondition, true, true, disco), 
	            grounding == null ? null : new Grounding(grounding, disco));
	      task.setProperty("@primitive",  primitive);
	      return task;
	   }
/*	public void setLevelOfKnowledg(Tree root,int percentage)  {
	}

	public Plan FromTreeToPlan(Tree node){
		if (node.isLeaf())
			return( newPlan(newTask(node.getHead().getName(), true, node.getHead().getPreconditions(), node.getHead().getPostconditions(),
					node.getHead().getPostconditions()+ "=true;println('"+ node.getHead().getName() + "')")));
		else
			return( newPlan(newTask(node.getHead().getName(), false, node.getHead().getPreconditions(), node.getHead().getPostconditions(),
					node.getHead().getPostconditions()+ "=true;println('"+ node.getHead().getName() + "')")));
	}
	public void GeneratePlan(Tree root, Plan top) {
		Plan child = null;
		if (!root.isLeaf()) {
					for (Tree i : root.getChildren()) {
						child = FromTreeToPlan(i);
						GeneratePlan(i,child);	
			}
		}
	}*/
	public  List<String> EvalConditions(List<String> conditions){
		 List<String> liveCond = new ArrayList<String>();
		 for (int i = 0; i < conditions.size(); i++){
			 if ((Boolean)disco.eval(conditions.get(i).toString(),"breakdown")==true){
				System.out.println("evaluating"+conditions.get(i));
				liveCond.add(conditions.get(i).toString());
			 }
		 }
		return liveCond;
		
	}

	// NB: use instance of Discolog extension instead of Agent below
	   private final Interaction interaction =  new Interaction(new Agent("agent"), new User("user")) {
	      
	      @Override
	      public void run () {
	         // keep running as long as agent has something to do and then stop
	         while (getSystem().respond(interaction, false, false)) {}
	      }
	   };
	   
	   private final Disco disco = interaction.getDisco();
	   private final TaskModel model = new TaskModel("urn:edu.wpi.cetask:models:Test", disco); 
	   
	  
	   
	   private DecompositionClass newRecipe (String id, TaskClass goal, List<Step> steps, String applicable) {
	      return new DecompositionClass(model, id, goal, steps, new Applicability(applicable, true, disco));
	   }
	   
	   private static Plan newPlan (TaskClass task) { return new Plan(task.newInstance()); }
	
}
