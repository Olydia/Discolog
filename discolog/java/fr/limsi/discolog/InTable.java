package fr.limsi.discolog;
import jekpro.tools.api.*;
import jekpro.platform.headless.ToolkitLibrary;

import java.io.IOException;
import java.io.Writer;
public class InTable {

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws InterpreterException,
    InterpreterMessage, IOException {
Knowledgebase know = new Knowledgebase(ToolkitLibrary.DEFAULT);
Interpreter inter = new Interpreter(know);
inter.initKnowledgebase();
//inter.addClassPath("file://C:/Users/ouldouali/workspace/discolog/prolog");

Term consultGoal = inter.parseTerm("consult('intable.p')");
inter.unfoldChecked(consultGoal);
TermVar[] vars = TermVar.createVars(1);
TermAtom employeeAtom = new TermAtom("employee");
TermCompound employeeGoal = inter.createCompound(employeeAtom, vars);
CallIn CallIn = new CallIn();
boolean found = inter.unfoldFirst(employeeGoal, CallIn);
while (found) {
    Writer wr = (Writer)((TermRef)inter.getProperty(ToolkitLibrary.PROP_SYS_CUR_OUTPUT)).getValue();
    wr.write(vars[0].deref().toString());
   
    wr.write('\n');
    wr.flush();
    found = inter.unfoldNext(CallIn);
}
		// TODO Auto-generated method stub
		

	}

}
