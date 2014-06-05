package fr.limsi.discolog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import alice.tuprolog.*;

public class InputPlanner {

	/* Input 	= 	domain Knowledge 
	 * 				Domain Knowledge	=	<initial state,	goal,	actions of the world >
	 *  
	 * 1.	From JAVA to PROLOG convert the input to Prolog syntax
	 * 2.	call the Prolog planner
	 * 3.	From PROLOG TO JAVA convert the resulting plan to java syntax 
	 * output	=	the plan 
	 * 
	 **/ 

	public void Action(String Name	,ArrayList Precond	,ArrayList Add	,ArrayList Delete){
	
		
	}
	public static void main(String[] args) throws InvalidTheoryException,
		MalformedGoalException, NoSolutionException, NoMoreSolutionException, FileNotFoundException, IOException {
		
		// import the planner 
		
		Prolog engine = new Prolog();
		SolveInfo info = engine.solve("test1(Plan).");
	
	
		// Init= [on(monkey,floor),on(box,floor),at(monkey,a),at(box,b),at(bananas,c),status(bananas,hanging)]
		Struct	monkey	=	new Struct	("monkey"); 
		Struct 	box		= 	new Struct	("box");
		Struct	floor	=	new Struct	("floor");
		Struct	bananas	=	new Struct	("banana");
		Struct	a		=	new Struct	("a");
		Struct	b		=	new Struct	("b");
		Struct	hanging	=	new Struct	("hanging");
		Struct	on		=	new Struct	("on",	monkey,	floor);
		Struct	on_box	=	new Struct	("on",	box,	floor);
		Struct	at_a	=	new Struct	("at",	monkey,	a);
		Struct	at_b	=	new Struct	("at",	box,	b);
		Struct	at_c	=	new Struct	("at",	bananas,	new Struct	("c"));
		Struct	status	=	new Struct	("status",	bananas,	hanging);
	
		Collection<Struct> v = java.util.Arrays.asList(at_a,at_b,status);

		System.out.println(v);
		//System.out.println(on_box);
		//System.out.println(at_c);


	}

}
