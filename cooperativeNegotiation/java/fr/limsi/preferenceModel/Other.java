package fr.limsi.preferenceModel;

import java.util.*;

import fr.limsi.preferenceModel.Statement.Satisfiable;

public class Other <C>{

	private ArrayList<Statement<C>> preferences ;
	private Class<C> type;


	public Class<C> getType() {
		return type;
	}

	public void setType(Class<C> type) {
		this.type = type;
	}

	public Other(Class<C> type) {
		this.type = type;
		this.preferences = new  ArrayList<Statement<C>>();
	}


	public void addPreference(C value){
		preferences.add(new Statement<C>(value));
	}

	public void addPreference(C value, Satisfiable status){
		preferences.add(new Statement<C>(value, status));
	}

	public float other(C value){
		if(this.getStatus(value).equals(Satisfiable.FALSE))
			return 0;
		else  if (this.getStatus(value).equals(Satisfiable.TRUE))
			return 1;
		else return 0;
	}


	public Satisfiable getStatus(C value){
		for(Statement<C> elem : preferences){
			if(elem.equals(value))
				return (elem.getStatus());
		}
		return Satisfiable.UNKOWN;
	}



}
