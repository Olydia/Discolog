package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Location implements Criterion{
	;

	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return Location.values();
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(){
		return this.name().toLowerCase();
	}

}
