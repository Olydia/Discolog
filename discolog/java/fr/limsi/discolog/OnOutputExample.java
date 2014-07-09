package fr.limsi.discolog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	

	//Struct go  = new Struct("pere", Term.createTerm("theodore"),Term.createTerm("geoffroy"));
	
	Theory theory = new Theory(new FileInputStream("C:/Users/ouldouali/Documents/GitHub/Discolog/discolog/prolog/regression-planner-orig/takeout.pl"));
	//Theory theory = new Theory(":-consult('new.pl').");
	try {
	        engine.setTheory(theory);
	        //engine.addTheory(new Theory(go.toString()+"."));
	    } catch (InvalidTheoryException ex) {

	    }
	//Struct goal  = new Struct("test1",new Var("Plan"));
	SolveInfo info = engine.solve("takeout(X,[1,2,3],L)");
	System.out.println("trying to write the solution");
	
	 if (!info.isSuccess()) 
		 	System.out.println("no." );
	
	 else if (!engine.hasOpenAlternatives()) {
		 	System.out.println(info.getVarValue("Plan"));
	} else {// main case
			System.out.println(info + " ?");
	}
	 
	Theory curTh = engine.getTheory(); // save current theory to file
	//System.out.println(curTh.toString());
	
		}

}
