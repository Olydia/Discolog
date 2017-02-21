package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Arrondissement implements Criterion{
	I, II, IV, IIX, IX, XII ;

	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return Arrondissement.values();
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
