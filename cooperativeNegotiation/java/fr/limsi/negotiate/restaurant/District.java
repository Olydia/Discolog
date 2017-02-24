package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum District implements Criterion {
	I, II, IV, IIX, IX, XII ;

	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return District.values();
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(){
		return this.name();
	}

	@Override
	public String print(String topic) {
		return  topic  +" in the "+ this.toString() + " district";
	}



}
