package fr.limsi.discolog;
import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import alice.tuprolog.*;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;

public class Input {
/*	preconditions(voler(P,D,G),[at(P,D),avion(P),aeroport(D),aeroport(G)]).
	achieves(voler(P,_,G),at(P,G)).
	deletes(voler(P,D,_),at(P,D)).*/
	/**
	 * @param args
	 */
	String Action(String name, ArrayList<String> Precond, ArrayList<String> Add, ArrayList<String> Delete){
		String precond = Precond.toString() ;
		String precondition = "preconditions("+ name+ ",["+ precond +"]).";
		return name;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
