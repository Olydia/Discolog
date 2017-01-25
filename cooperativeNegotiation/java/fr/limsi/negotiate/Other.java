package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Statement.Satisfiable;

public class Other <C>{

	private ArrayList<Statement<C>> preferences ;
	private Class<C> type;


	public ArrayList<Statement<C>> getPreferences() {
		return preferences;
	}

	public void setPreferences(ArrayList<Statement<C>> preferences) {
		this.preferences = preferences;
	}
	
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
		else return (float) 0.5;
	}


	public Satisfiable getStatus(C value){
		for(Statement<C> elem : preferences){
			if(elem.getValue().equals(value))
				return (elem.getStatus());
		}
		return Satisfiable.UNKOWN;
	}
	
	public List<C> getPreferences(Satisfiable status){
		List<C> un = new ArrayList<C>();
		List<C> elemenets =  Arrays.asList(this.type.getEnumConstants());
		if(status.equals(Satisfiable.UNKOWN)){
			for(C e: elemenets){
				if(getStatus(e).equals(Satisfiable.UNKOWN))
					un.add(e);
			}
			return un;
		}
		List<C> values = new ArrayList<C>();
		for(Statement<C> elem: preferences){
			if(elem.getStatus().equals(status))
				values.add(elem.getValue());
		}
		return values;
	}
	
//	public boolean isAcceptable(C value){
//		return (this.other(value) >= NegotiationParameters.beta);
//	}


}
