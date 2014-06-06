package fr.limsi.discolog;

import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;
public class OnOutputExample {
static String finalResult = "";

public static void main(String[] args) throws Exception {
Prolog engine = new Prolog();

engine.setTheory(new Theory(":-consult('plan.pl')."));
engine.addTheory(new Theory("action(from_q_achieve_r_undo_q(x),[q(x)],[q(x)],[r(x)])."));
SolveInfo res = engine.solve("execute_plan(Plan).");
//System.out.println(res.getSolution());

Struct	q	=	new Struct	("q",new Var("X"));
Struct	r	=	new Struct	("r",new Var("X")); 
//action(from_q_achieve_r_undo_q(x),[q(x)],[q(x)],[r(x)]).

Struct precond = new Struct(q, new Struct());
Struct add = new Struct(r, new Struct());
System.out.println();

}
}  