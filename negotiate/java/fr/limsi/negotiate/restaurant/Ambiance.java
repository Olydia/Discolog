package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Ambiance implements Criterion{

	 NOISY, CALM, ROMANTIC;

	 public Criterion[] getValues() {
	 	// TODO Auto-generated method stub
	 	return (Ambiance.values());
	 }
}
