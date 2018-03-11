package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Atmosphere implements Criterion{

	COZY,FAMILY,LIVELY, MODERN, ROMANTIC, QUIET ;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Atmosphere.values());
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
			fr = "anim\u00e9";
			break;
		case QUIET:
			fr = "calme";
			break;
		case COZY:
			fr = "cosy";
			break;
		case FAMILY:
			fr = "familial";
			break;
		case MODERN:
			fr = "moderne";
			break;
		case ROMANTIC:
			fr = "romantique";
			break;
		default:
			System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
			break;
		}
		return StringToUTF8.convertToUTF8(fr);
	}

	@Override
	public String afficherLikes() {
		String fr = "les restaurants ";
		switch(this) {
		case COZY:
			fr += "cosy";
			break;
		case FAMILY:
			fr += "familiaux";
			break;
		default:
			fr += afficher() + "s";
			break;
		}
		return StringToUTF8.convertToUTF8(fr);
	}



}


