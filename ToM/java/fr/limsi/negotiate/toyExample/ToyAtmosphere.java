package fr.limsi.negotiate.toyExample;

import fr.limsi.negotiate.Criterion;

public enum ToyAtmosphere implements Criterion{

	LIVELY, QUIET, FAMILY;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (ToyAtmosphere.values());
	}

	@Override
	public String toString(){
		return this.name().toLowerCase();
	}
	
	@Override
	public String print(String topic) {
		// TODO Auto-generated method stub
		return this.toString() +" "+ topic;
	}

	public String afficher(){
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

	@Override
	public String afficherLikes() {
		// TODO Auto-generated method stub
		return null;
	}

}
