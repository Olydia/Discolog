package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Location implements Criterion {
	//DOWNTOWN, NORTH_SIDE, EAST_SIDE, WEST_SIDE, SOUTH_SIDE;

	CENTER_OF_PARIS,GARE_DU_NORD, MONTPARNASSE,  NEAR_EIFFEL_TOWER, PERE_LACHAISE ;
	//CENTER_OF_PARIS,NEAR_EIFFEL_TOWER, PERE_LACHAISE, GARE_DU_NORD , MONTPARNASSE;
	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return Location.values();
	}

	@Override
	public String afficher() {

		String fr = "";
		switch(this) {
        case CENTER_OF_PARIS:
        	fr += "près du centre de paris";
        break;
        case MONTPARNASSE:
        	fr += "à Montparnasse";
        break;
        case GARE_DU_NORD:
        	fr += "à gare du Nord";
        break;
        case NEAR_EIFFEL_TOWER:
        	fr += "près de la tour Eiffel";
        break;
        case PERE_LACHAISE:
        	fr += "à Père lachaise";
        break;
        default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return StringToUTF8.convertToUTF8(fr);
  
	}
	
	@Override
	public String afficherLikes() {
	
			String fr = "les restaurants " +afficher() + "s";
			return StringToUTF8.convertToUTF8(fr);


	}
	
	@Override
	public String toString(){
		String input = this.name();
		input = input.replace("_", " ");
		return input.toLowerCase();
	}

	@Override
	public String print(String topic) {
		String text = " at ";
		if(this.equals(CENTER_OF_PARIS))
		 text =  "in the ";
		if(this.equals(NEAR_EIFFEL_TOWER))
			text = " ";
		return  topic  + text + this.toString();
	}



}
