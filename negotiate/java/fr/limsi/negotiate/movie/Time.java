package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.Criterion;


public enum Time implements Criterion {

	   
	  H2MIN15, H1MIN30;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Time.values());
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
