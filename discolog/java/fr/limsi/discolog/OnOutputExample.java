package fr.limsi.discolog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;
public class OnOutputExample {

	static String finalResult = "";
	
public static void main(String[] args) throws Exception {
	Prolog engine = new Prolog();
	
	Theory theory = new Theory(new FileInputStream("C:/Users/Lydia/Documents/GitHub/Discolog/discolog/prolog/test-2p/moveandpaint.pl"));
	try {
	        engine.setTheory(theory);
	    } catch (InvalidTheoryException ex) {

	    }
	// Add the init state and the planner call for the goal 
	Theory init = new Theory("strips_holds(islocked,init).");
	String Goal = "isopen";
	engine.addTheory(init);	
	
	Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["+Goal+"],7,Plan)." );
	engine.addTheory(PlannerCall);
	
	// The request for STRIPS. 
	Struct goal  = new Struct("test1",new Var("X"));
	SolveInfo info = engine.solve(goal);
	
	// Results
	 if (!info.isSuccess()) 
		 	System.out.println("no." );
	
	 else if (!engine.hasOpenAlternatives()) {
		 	System.out.println(info);
	} else {// main case
			System.out.println(info.getSolution());
	}
	 String Plan = info.getVarValue("X").toString();
	 
	// Split the different clauses of the resulting plan
	System.out.println("Return Value :" );
	ArrayList<String> JavaPlan = new ArrayList<String>();
    for (String retval: Plan.split("do\\(")){
    	JavaPlan.add(retval);
    }
    System.out.println(JavaPlan);
    Collections.reverse(JavaPlan);
    System.out.println(JavaPlan);
   Theory curTh = engine.getTheory(); // save current theory to file
	//System.out.println(curTh.toString());
	
		}

}
