package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.Criterion;


public enum Category  implements Criterion{
	//ACTION, ROMANTIC, 
	ANIMATION, COMEDY, HORROR; 

	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Category.values());
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
