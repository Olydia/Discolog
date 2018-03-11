package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Cuisine implements Criterion {

	CHINESE, FRENCH,  ITALIAN, JAPANESE, KOREAN, MEXICAN, TURKISH;


	@Override
	public Criterion[] getValues() {

		// TODO Auto-generated method stub
		return (Cuisine.values());
	}
	public Class <? extends Criterion> getClasse(){
		return Cuisine.class;
	}

	@Override
	public String toString(){
		return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
	}

	@Override
	public String print(String topic) {
		// TODO Auto-generated method stub
		return this.toString() +" "+ topic;
	}

	public String afficher(){
		String fr = "";
		switch(this) {
		case CHINESE:
			fr += "chinois";
			break;
		case FRENCH:
			fr += "fran\u00e7ais";
			break;
		case ITALIAN:
			fr += "italien";
			break;
		case TURKISH:
			fr += "turque";
			break;
		case JAPANESE:
			fr += "japonais";
			break;
		case MEXICAN:
			fr += "mexicain";
			break;
		case KOREAN:
			fr += "cor\u00e9en";
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
		case ITALIAN:
			fr+= "italiens";
			break;
		case TURKISH:
			fr += "turques";
			break;
		case MEXICAN:
			fr += "mexicains";
			break;
		case KOREAN:
			fr += "cor\u00e9ens";	
			
			break;
			
		default:
			fr += afficher();
			break;
		}
		return fr;//StringToUTF8.convertToUTF8(fr);
	}
	
	
}
