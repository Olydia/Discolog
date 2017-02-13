package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.Criterion;

public enum Country implements Criterion{
	FRANCE, US, CANADA;
	
	   public Criterion[] getValues() {
			// TODO Auto-generated method stub
			return (Country.values());
		   }

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
