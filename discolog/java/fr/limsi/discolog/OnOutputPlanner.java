package fr.limsi.discolog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;

public class OnOutputPlanner {

	@SuppressWarnings("null")
	private static ArrayList<String> getPlannerOutput(String plan) {
		ArrayList<String> Output = new ArrayList<String>();
		String init;

		Pattern p = Pattern.compile("(do\\()");
		String[] splitString = (p.split(plan));
		// remove init, parenthisis and init argument to obtain only the name of
		// actions

		for (String element : splitString) {
			String elem = element.replaceAll("(init)(\\)+)", "");
			elem = elem.replaceAll(",", "");
			init = elem.replaceAll("\\(.*\\)", "");
			Output.add(init);

		}
		return Output;

	}

	public static  ArrayList<String> CallStripsPlanner(String Initial_state, String Goal)
			throws InvalidTheoryException, FileNotFoundException, Exception {
		String Plan = " ";
		ArrayList<String> JavaPlan = new ArrayList<String>();
		Prolog engine = new Prolog();
		/*
		 * String planner = new String(); File file =new
		 * File("moveandpaint.pl"); planner = file.getCanonicalPath();
		 * System.out.println(planner);
		 */
		Theory theory = new Theory(
				new FileInputStream(
						"C:/Users/Lydia/Documents/GitHub/Discolog/discolog/prolog/test-2p/moveandpaint.pl"));
		try {
			engine.setTheory(theory);
		} catch (InvalidTheoryException ex) {

		}
		// Add the init state and the planner call for the goal
		Theory init = new Theory("strips_holds(" + Initial_state + ",init).");
		// String Goal = "isopen";
		engine.addTheory(init);

		Theory PlannerCall = new Theory("test1(Plan):- strips_solve([" + Goal
										+ "],7,Plan).");
		engine.addTheory(PlannerCall);

		// The request for STRIPS.
		Struct goal = new Struct("test1", new Var("X"));
		SolveInfo info = engine.solve(goal);

		// Results
		if (!info.isSuccess())
			System.out.println("no.");
		else {// main case
			 Plan = info.getVarValue("X").toString();
		}
		

		// Split the different clauses of the resulting plan
		//System.out.println("Return Value :");
		
		JavaPlan = getPlannerOutput(Plan);
		Collections.reverse(JavaPlan);
		//System.out.println(JavaPlan);
		return JavaPlan;
	}

	public static void main(String[] args) throws Exception {
		ArrayList<String> JavaPlan = new ArrayList<String>();
		 String Goal = "isopen";
		 String initial = "islocked";
		 JavaPlan = CallStripsPlanner(initial,Goal);
		// Theory init = new Theory("strips_holds(islocked,init).");
		// Theory curTh = engine.getTheory(); // save current theory to file
		System.out.println(JavaPlan);

	}

}
