package fr.limsi.preferenceModel.restaurant;

import fr.limsi.preferenceModel.Criterion;

public enum Atmosphere implements Criterion{

	LIVELY, QUIET, ROMANTIC, FAMILY ;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Atmosphere.values());
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
