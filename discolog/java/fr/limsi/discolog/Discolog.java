package fr.limsi.discolog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;
import edu.wpi.cetask.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.*;
import edu.wpi.disco.Agent;
import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;

/**
 * New main class for Discolog that extends default Disco agent to add breakdown
 * detection and recovery.
 */
public class Discolog extends Agent {

	/**
	 * Use this main method instead of {@link Disco#main(String[])} to start
	 * system with console.
	 */
	public static void main(String[] args) {
		Agent agent = new Discolog("agent");
		// restrict to performing only a single primitive action on each turn
		// so we have more control over example
		agent.setMax(1);
		Interaction interaction = new Interaction(agent, new User("user"),
				args.length > 0 && args[0].length() > 0 ? args[0] : null);
		interaction.start(true);

	}

	// TODO add private fields here to hold Prolog engine, etc.

	public Discolog(String name) {
		super(name);
		TaskEngine.DEBUG = true;
	}

	private boolean recover(Interaction interaction){
		candidates.clear();
		findCandidates(interaction.getDisco().getTops());
		System.out.println(interaction.getDisco().getTops());
		if (candidates.isEmpty()) {
			System.out.println("No recovery candidates!");
			return false;
		} else {
			for (Plan candidate : candidates) {
				Plan recovery = invokePlanner(candidate);
				if (recovery != null) {
					System.out.println("Found recovery plan for " + candidate);
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

	private Plan invokePlanner(Plan candidate) {
		// this should invoke Prolog planner
		// for now it always returns the answer to make our
		// example work, namely executing "Open"
		// return new
		// Plan(candidate.getGoal().getType().getEngine().getTaskClass("Open").newInstance());
		TaskEngine d = candidate.getGoal().getType().getEngine();
		ArrayList<String> JavaPlan = new ArrayList<String>();
		String Goal = "isopen";
		String initial = "islocked";
		JavaPlan = CallStripsPlanner(initial, Goal);
		Plan p = newPlan(d, "recovery");
		for (int i = 0; i < JavaPlan.size() - 1; i++) {
			p.add(newPlan(d, JavaPlan.get(i)));
		}
		if (candidate.isFailed()){
			for( Plan s: candidate.getSuccessors()) {
				s.requires(p);
				s.unrequires(candidate);
			}
		}
		// p.add(candidate);
		/*
		 * p.add(newPlan(d, "unlock")); p.add(newPlan(d, "open"));
		 */
		return p;
	}

	private static Plan newPlan(TaskEngine disco, String name) {
		return new Plan(disco.getTaskClass(name).newInstance());
	}

	private final List<Plan> candidates = new ArrayList<Plan>();

	/**
	 * @return candidates for recovery planning target.
	 * 
	 *         First cut approach: plans in given tree that are neither done nor
	 *         live nor blocked (which means that their precondition must be
	 *         false)
	 */
	private void findCandidates(List<Plan> children) {
		for (Plan plan : children) {
			if (!(plan.isDone() || plan.isLive() || plan.isBlocked())  // precond
			    || plan.isFailed() // post cond
			    || (plan.isLive() && !plan.isPrimitive() && plan.isDecomposed() && plan.getDecompositions().isEmpty())) // applicabiliy
				candidates.add(plan);
			findCandidates(plan.getChildren());
			
		}
	}

	@Override
	public Plugin.Item respondIf(Interaction interaction, boolean guess) {
		Plugin.Item item = super.respondIf(interaction, guess);
		// if current toplevel goal is not done and we have
		// nothing to do, then we have a breakdown to recover from
		Plan focus = interaction.getFocus();
		if (focus != null && !interaction.getDisco().getTop(focus).isDone()
				&& item == null) {
			if (recover(interaction))
				return super.respondIf(interaction, guess); // new response with
															// new plan
			else
				return null;
		} else
			return item;
	}

	// ******************************* Planner Call
	// ********************************************

	private static ArrayList<String> getPlannerOutput(Term plan) {
		ArrayList<String> Output = new ArrayList<String>();
		String init;

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
		return Output;

	}

	public static ArrayList<String> CallStripsPlanner(String Initial_state,
			String Goal) {
		Term Plan = null ;
		ArrayList<String> JavaPlan = new ArrayList<String>();
		Prolog engine = new Prolog();
		/*
		 * String planner = new String(); File file =new
		 * File("moveandpaint.pl"); planner = file.getCanonicalPath();
		 * System.out.println(planner);
		 */
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		  	InputStream planner = ReplaceDemo.class.getResourceAsStream("/test-2p/moveandpaint.pl");
			Theory theory = new Theory(planner);
			engine.setTheory(theory);
			Strips_Input(Initial_state, Goal, engine );
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
		// Split the different clauses of the resulting plan
		// System.out.println("Return Value :");

		JavaPlan = getPlannerOutput(Plan);
				// System.out.println(JavaPlan);
		return JavaPlan;
	}
	private static void Strips_Input(String Initial_state, String Goal, Prolog engine ){
		// Add the init state and the planner call for the goal
		Theory init;
		try {
			init = new Theory("strips_holds(" + Initial_state
					+ ",init).");
			Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["
					+ Goal + "],7,Plan).");
			engine.addTheory(init);
			engine.addTheory(PlannerCall);
		} catch (InvalidTheoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String Goal = "isopen";
		

	}
	private ArrayList<String>init_primitive(){
		ArrayList<String> Init = new ArrayList<String>();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	  	InputStream planner = Discolog.class.getResourceAsStream("/models/moveandpaint.xml");
	  	return Init;
		
	}
	// *****************************************************************************************
}
