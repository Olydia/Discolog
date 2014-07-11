package fr.limsi.discolog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;
public class OnOutputExample {

	static String finalResult = "";
	
public static void main(String[] args) throws Exception {
	Prolog engine = new Prolog();

	engine.addOutputListener(new OutputListener() {
		@Override
		public void onOutput(OutputEvent e) {
			finalResult += e.getMsg();
			}
		});
	

	Theory theory = new Theory(new FileInputStream("C:/Users/Lydia/Documents/GitHub/Discolog/discolog/prolog/test-2p/moveandpaint.pl"));
	//Theory theory = new Theory(":-consult('new.pl').");
	try {
	        engine.setTheory(theory);
	    } catch (InvalidTheoryException ex) {

	    }
	Struct goal  = new Struct("test1",new Var("X"));
	SolveInfo info = engine.solve(goal);
	
	 if (!info.isSuccess()) 
		 	System.out.println("no." );
	
	 else if (!engine.hasOpenAlternatives()) {
		 	System.out.println(info);
	} else {// main case
			System.out.println(info.getSolution());
	}
	 /*String Plan = info.getVarValue("X").toString();
	//System.out.println(Plan);
	System.out.println("Return Value :" );
	ArrayList<String> JavaPlan = new ArrayList<String>();
    for (String retval: Plan.split(",")){
    	JavaPlan.add(retval);
    	
    }
    //System.out.println(JavaPlan);
    for (String retval: Plan.split("do")){
    	//JavaPlan.add(retval);
    	//System.out.println(retval);
    }*/
	Theory curTh = engine.getTheory(); // save current theory to file
	//System.out.println(curTh.toString());
	
		}

}
