package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Ambiance implements Criterion{

	LIVELY, QUIET, ROMANTIC, FAMILIAL ;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Ambiance.values());
	}

	@Override
	public String toString(){
		return this.name().toLowerCase();
	}

	public String getFrVersion(){
		String fr = "";
		switch(this) {
		case LIVELY:
			fr = "ANIME";
			break;
		case QUIET:
			fr = "CALME";
			break;
		default:
			System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
			break;
		}
		return fr;
	}

}
