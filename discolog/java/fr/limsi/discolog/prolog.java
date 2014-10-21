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

public class prolog {
	public static void main(String[] args) throws IOException {
		ArrayList<String> init = new ArrayList<String>();
		ArrayList<String> sol = new ArrayList<String>();

		init.add("p1");
		init.add("p3");
		init.add("p4");
		init.add("p5");

		String Goal = "p2";
		sol=CallStripsPlanner(init,Goal);
		for(String solu:sol)
				System.out.println(solu);
	}
	public static ArrayList<String> CallStripsPlanner(List<String> Initial_state,
			String Goal) {
		Term Plan = null;
		ArrayList<String> JavaPlan = new ArrayList<String>();
		Prolog engine = null;
		engine = new Prolog();
		
		try {
			InputStream planner = Discolog.class
					.getResourceAsStream("/test-2p/Domain_knowledge.pl");
			Theory theory = new Theory(planner);
			engine.setTheory(theory);
			Strips_Input(Initial_state, Goal.toLowerCase(), engine);
			// The request for STRIPS.
			Struct goal = new Struct("test1", new Var("X"));
			SolveInfo info = engine.solve(goal);

			// Results
			if (!info.isSuccess()){
				System.out.println("no.");
				return null;
			}
			else {// main case
				Plan = info.getVarValue("X");
				JavaPlan = getPlannerOutput(Plan);
				return JavaPlan;
			}
		} catch (InvalidTheoryException | IOException | NoSolutionException ex) {
			throw new RuntimeException(ex);
		}

		
	}

	private static void Strips_Input(List<String> Initial_state, String Goal,
			Prolog engine) {
		// Add the init state and the planner call for the goal
		//Theory init;
		try {
			for(String init : Initial_state){
				//System.out.println(init);
				engine.addTheory(new Theory("strips_holds(" + init.toLowerCase() + ",init)."));
			}
			Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["
					+ Goal.toLowerCase() + "],30,Plan).");
			//engine.addTheory(init);
			engine.addTheory(PlannerCall);
		} catch (InvalidTheoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> getPlannerOutput(Term plan) {
		ArrayList<String> Output = new ArrayList<String>();
		String init;
		if(plan == null){
			//System.out.println("No recovery STRIPS plan was found !");
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
		}
		return Output;

	}

	
}
