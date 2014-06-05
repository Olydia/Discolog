package fr.limsi.discolog;

import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;
public class OnOutputExample {
static String finalResult = "";

public static void main(String[] args) throws Exception {
Prolog engine = new Prolog();

engine.setTheory(new Theory(":-consult('plan.pl')."));

SolveInfo res = engine.solve("execute_plan(Plan).");
System.out.println(res.getSolution());



}
}  