package fr.limsi.negotiate.restaurant.FR;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Ambiance implements Criterion{

	COSY,FAMILIAL,ANIME, MODERNE, ROMANTIQUE, CALME ;

	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Ambiance.values());
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

		return StringToUTF8.convertToUTF8(this.toString());
	}

	@Override
	public String afficherLikes() {
		String fr = "les restaurants ";
		switch(this) {
		case COSY:
			fr += "cosy";
			break;
		case FAMILIAL:
			fr += "familiaux";
			break;
		default:
			fr += afficher() + "s";
			break;
		}
		return StringToUTF8.convertToUTF8(fr);
	}



}


