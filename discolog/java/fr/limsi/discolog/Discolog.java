package fr.limsi.discolog;

import java.io.IOException;
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
import edu.wpi.cetask.Description.Condition;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskClass;
import edu.wpi.cetask.TaskEngine;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Interaction;

/**
 * New main class for Discolog that extends default Disco agent to add breakdown
 * detection and recovery.
 */
public class Discolog extends Agent {
	// TODO add private fields here to hold Prolog engine, etc.

	public Discolog(String name) {
		super(name);
		TaskEngine.DEBUG = true;
	}

	private boolean recover(Interaction interaction) {
		long begin_recover = System.currentTimeMillis();

		TestClass.NbBreakdown ++;
		//interaction.getDisco().history(System.out);
		candidates.clear();
		long find_candidates = System.currentTimeMillis();

		findCandidates(interaction.getDisco().getTops());

		long end_candidates = System.currentTimeMillis();
		try {
			TestClass.time_execution.write(" Finding candidates for the broken task : "+ 
					interaction.getDisco().getFocus().getType().getId()+ "    :"+
					(end_candidates - find_candidates)+"");
			TestClass.time_execution.flush();
			TestClass.time_execution.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long startplanRepair = System.currentTimeMillis();
		System.out.println("Breakdown detected at " +interaction.getDisco().getFocus());
		if (candidates.isEmpty()) {
			System.out.println("No recovery candidates!");
		} else {
			Solution STRIPS = invokePlanner(candidates);

			long endplanRepair = System.currentTimeMillis();
			try {
				TestClass.time_execution.write(" the plan repair procedure for the candidate : "+ 
						interaction.getDisco().getFocus().getType().getId()+ "    :"+
						(endplanRepair - startplanRepair)+"");
				TestClass.time_execution.flush();
				TestClass.time_execution.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long end_recover = System.currentTimeMillis();
			
			try {
				TestClass.time_execution.write(" recover procedure for the broken task : "+ 
						interaction.getDisco().getFocus().getType().getId()+ "    :"+
						(end_recover - begin_recover)+"");
				TestClass.time_execution.flush();
				TestClass.time_execution.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(STRIPS != null){
				//Plan recovery = new Plan(PlanConstructor.RECOVERY.newInstance());
				//TaskEngine TE = PlanConstructor.RECOVERY.getEngine();
				/*for(String plan: STRIPS.Strips){
					if ((Boolean)TE.eval(plan,"evalsolution")== false){
						System.out.println("Solution inapplicable");
						TestClass.NbRecoveredCandidates -= recovred;
						recovred = 0;
						return false;
					}
					else {

						System.out.println("Solution applicable , exec "+plan + (Boolean)TE.eval("exec"+plan,"evalsolution"));
					}

				}*/
				/*for (int i = 0; i < STRIPS.getStrips().size(); i++) {
					recovery.add(newPlan(TE, STRIPS.getStrips().get(i)));
				}
				if (STRIPS.getCandidate().plan.isFailed()) {
					for (Plan s : STRIPS.getCandidate().plan.getSuccessors()) {
						s.requires(recovery);
						s.unrequires(STRIPS.getCandidate().plan);
					}
				}*/
				//if (recovery!= null) {
				System.out.println("Found recovery plan"+ STRIPS.Strips.toString()+" for " + STRIPS.getCandidate().plan.getGoal().getType().getId());
				TestClass.NbRecover ++;
				/*Disco disco = interaction.getDisco();
				// splice in recovery plan
				disco.getFocus().add(recovery);
				recovery.setContributes(true); // so not interruption*/
				return true;
				//}
			}
			else {
				System.out.println("No recovery plans found!");

			}
		}
		return false;
	}

	private Solution invokePlanner(List <Candidate> conditions) {
		// this should invoke Prolog planner
		ArrayList<String> JavaPlan = new ArrayList<String>();
		ArrayList<Solution> planrepair = new ArrayList<Solution>();
		TestClass.NbCandidates += candidates.size();

		for(Candidate candidate: candidates){
			long startCallStripsPlanner = System.currentTimeMillis();
			TaskEngine d = candidate.plan.getGoal().getType().getEngine();
			JavaPlan = CallStripsPlanner(localtheory, EvalConditions(TestClass.conditions,d) ,candidate.condition.getScript(), d);
			long endCallStripsPlanner = System.currentTimeMillis();
			
			try {
				TestClass.time_execution.write(" ################################### "+ 
						candidate.plan.getType().getId()+ "    :"+						" ################################### ");
				TestClass.time_execution.flush();

				TestClass.time_execution.newLine();
				TestClass.time_execution.write(" Call Strips Planner  for  "+ 
						candidate.plan.getType().getId()+ "    :"+
						(endCallStripsPlanner - startCallStripsPlanner)+"");
				TestClass.time_execution.flush();
				TestClass.time_execution.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if((JavaPlan != null )){
				planrepair.add(new Solution(JavaPlan, candidate));
			}

		}	
		Collections.sort(planrepair);
		if (planrepair.isEmpty())
			return null;
		else{		
			TestClass.NbRecoveredCandidates += planrepair.size();
			return(planrepair.get(0));
		}

	}
	//***************************************************************************************************************************************

	private final List<Candidate> candidates = new ArrayList<Candidate>();
	private final Prolog localtheory = new Prolog();

	private  class Candidate {
		private final Plan plan;
		private final Condition condition;

		private Candidate(Plan plan, Condition condition) {
			this.plan = plan;
			this.condition = condition;
		}

		@Override
		public String toString() {
			return "Candidate [plan=" + plan + ", condition=" + condition.getScript() + "]";
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
		//check there is a prolog version of the goal condition
		for (Plan plan : children) {
			TaskClass type = plan.getGoal().getType();
			if (type.getPrecondition() != null
					&& !(plan.isDone() || plan.isLive() || plan.isBlocked()||plan.isFailed()) &&
					TestClass.STRIPSconditions.contains(type.getPrecondition().getScript()))
				candidates.add(new Candidate(plan, type.getPrecondition()));
			else if (type.getPostcondition() != null && plan.isFailed()
					&& !type.isSufficient() 
					&& TestClass.STRIPSconditions.contains(type.getPostcondition().getScript())) // post cond
				candidates.add(new Candidate(plan, type.getPostcondition()));
			if (plan.isLive() && !plan.isPrimitive() && !plan.isDecomposed())
				for (DecompositionClass c : plan.getType().getDecompositions()) {
					if (c.getApplicable() != null && TestClass.STRIPSconditions.contains(c.getApplicable().getScript()))
						candidates.add(new Candidate(plan, c.getApplicable()));
				}
			findCandidates(plan.getChildren());
		}
	}

	@Override
	public Plugin.Item respondIf(Interaction interaction, boolean guess, boolean retry) {
		Plugin.Item item = super.respondIf(interaction, guess, retry);
		// if current toplevel goal is not done and we have
		// nothing to do, then we have a breakdown to recover from
		Plan focus = interaction.getFocus();
		if (focus != null && !interaction.getDisco().getTop(focus).isDone()
				&& item == null) {
			if (recover(interaction)){
				//throw new Shell.Quit();
				return super.respondIf(interaction, guess, retry); // new response with
				// new plan
			}

			else
				return null;
		} else
			return item;
	}

	// ******************************* Planner Call ********************************************

	private  ArrayList<String> getPlannerOutput(Term plan, TaskEngine d) {
		ArrayList<String> Output = new ArrayList<String>();
		String init;
		if(plan == null){		
			return null;
		}
		else{
			Pattern p = Pattern.compile("(do\\()");
			String[] splitString = (p.split(plan.toString()));

			for (String element : splitString) {
				String elem = element.replaceAll("(init)(\\)+)", "");
				elem = elem.replaceAll(",", "");
				init = elem.replaceAll("\\(.*\\)", "");
				if (init.equals("init"))
					return null;
				else 
					if (!init.equals(""))
						Output.add(init);


			}
			Collections.reverse(Output);
			if(Output.get(0).equals(d.getFocus().getType().getId()))
				return null;
		}
		return Output;

	}

	public  ArrayList<String> CallStripsPlanner(Prolog engine ,List<String> Initial_state,
			String Goal, TaskEngine d) {
		Term Plan = null;

		ArrayList<String> JavaPlan = new ArrayList<String>();

		long startINITStripsPlanner = System.currentTimeMillis();

		localtheory.clearTheory();
		try {

			localtheory.setTheory(TestClass.engine.getTheory());
			//System.out.println(localtheory.getTheory());
		} catch (InvalidTheoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Strips_Input(Initial_state, Goal.toLowerCase(), engine);

			long endINITStripsPlanner = System.currentTimeMillis();
			try {
				TestClass.time_execution.write(" theory Update :  "+ 

						(endINITStripsPlanner - startINITStripsPlanner)+"");
				TestClass.time_execution.flush();
				TestClass.time_execution.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Struct goal = new Struct("test1", new Var("X"));

			long startFindPlan = System.currentTimeMillis();

			SolveInfo info = engine.solve(goal);

			long endFindPlan = System.currentTimeMillis();
			try {
				TestClass.time_execution.write(" fIND A PLAN FOR  "+  Goal + "  :" +

						(endFindPlan - startFindPlan)+"");
				TestClass.time_execution.flush();
				TestClass.time_execution.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Results
			if (!info.isSuccess()){
				return null;
			}
			else {// main case
				long startoutputplanner = System.currentTimeMillis();

				Plan = info.getVarValue("X");
				JavaPlan = getPlannerOutput(Plan, d);
				
				long endoutputplanner = System.currentTimeMillis();
				try {
					TestClass.time_execution.write(" get Strips Planner  output "+ 

							(endoutputplanner - startoutputplanner+""));
					TestClass.time_execution.flush();
					TestClass.time_execution.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return JavaPlan;
			}
		} catch (NoSolutionException ex) {
			throw new RuntimeException(ex);
		}
		
		
	}

	private  void Strips_Input(List<String> Initial_state, String Goal,
			Prolog engine) {
		try {
			for(String init : Initial_state){
				engine.addTheory(new Theory("strips_holds(" + init.toLowerCase() + ",init)."));
			}
			Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["
					+ Goal.toLowerCase() + "],"
					+(TestClass.partialroot.getLeaves().size()*2)+",Plan).");
			engine.addTheory(PlannerCall);

		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}

	}


	public  List<String> EvalConditions(List<String> conditions, TaskEngine engine){
		long eval_cond = System.currentTimeMillis();

		List<String> liveCond = new ArrayList<String>();
		for (int i = 0; i < conditions.size(); i++){
			if ((Boolean)engine.eval(conditions.get(i).toString(),"breakdown")!= null &&
					(Boolean)engine.eval(conditions.get(i).toString(),"breakdown")!= false){
				liveCond.add(conditions.get(i).toString());
			}
		}
		long end_eval_cond = System.currentTimeMillis();
		try {
			TestClass.time_execution.write("conditions evaluation fot the prolog init "+ 

					(end_eval_cond - eval_cond+""));
			TestClass.time_execution.flush();
			TestClass.time_execution.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liveCond;
	}

	public class Solution implements Comparable<Solution>{
		private final ArrayList<String> Strips;
		private final Candidate candidate;

		private Solution(ArrayList<String>  Strips, Candidate condition) {
			this.Strips=Strips;
			this.candidate = condition;
		}
		public ArrayList<String> getStrips() {
			return Strips;
		}
		public Candidate getCandidate() {
			return candidate;
		}
		public int getSolutionSize(){
			return this.getStrips().size();
		}

		@Override
		public int compareTo(Solution sol2) {
			int sol2Size=sol2.getSolutionSize();
			/* For Ascending order*/
			return this.getSolutionSize()-sol2Size;
		}
	}
}