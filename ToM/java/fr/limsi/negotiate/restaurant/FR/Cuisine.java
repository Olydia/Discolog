package fr.limsi.negotiate.restaurant.FR;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Cuisine implements Criterion {

	CHINOIS, FRANÇAIS,  ITALIEN, JAPONAIS, CORÉEN, MEXICAIN, TURQUE;


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
		case CHINOIS:
			fr += "chinois";
			break;
		case FRANÇAIS:
			fr += "fran\u00e7ais";
			break;
		case ITALIEN:
			fr += "italien";
			break;
		case TURQUE:
			fr += "turque";
			break;
		case JAPONAIS:
			fr += "japonais";
			break;
		case MEXICAIN:
			fr += "mexicain";
			break;
		case CORÉEN:
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
		case ITALIEN:
			fr+= "italiens";
			break;
		case TURQUE:
			fr += "turques";
			break;
		case MEXICAIN:
			fr += "mexicains";
			break;
		case CORÉEN:
			fr += "cor\u00e9ens";	
			
			break;
			
		default:
			fr += afficher();
			break;
		}
		return fr;//StringToUTF8.convertToUTF8(fr);
	}
	
	
}
