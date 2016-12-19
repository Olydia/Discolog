package fr.limsi.preferenceModel;

import java.util.*;

public class NegotiatedPreferences <C>{

	private ArrayList<NegotiatedPreference<C>> preferences ;
	private Class<C> type;


	public Class<C> getType() {
		return type;
	}


	public void setType(Class<C> type) {
		this.type = type;
	}


	public NegotiatedPreferences(Class<C> type) {
		this.type = type;
		this.preferences = new  ArrayList<NegotiatedPreference<C>>();
	}


	public void addPreference(C value){
		preferences.add(new NegotiatedPreference<C>(value));
	}

	public void addPreference(C value, Satisfiable status){
		preferences.add(new NegotiatedPreference<C>(value, status));
	}

	public float satisfaction(C value){
		if(this.getStatus(value).equals(Satisfiable.FALSE))
			return 0;
		else return 1;
	}


	public Satisfiable getStatus(C value){
		for(NegotiatedPreference<C> elem : preferences){
			if(elem.equals(value))
				return (elem.getStatus());
		}
		return Satisfiable.UNKOWN;
	}


	public class NegotiatedPreference <L> {
		private L value;
		private Satisfiable status;
		public L getValue() {
			return value;
		}

		public void setValue(L value) {
			this.value = value;
		}

		public Satisfiable getStatus() {
			return status;
		}

		public NegotiatedPreference(L value){
			this.value = value;
			this.status = Satisfiable.UNKOWN;
		}

		public NegotiatedPreference(L value, Satisfiable status){
			this.value = value;
			this.status = status;
		}


	}
	public static enum Satisfiable { TRUE, FALSE, UNKOWN }

}
