package fr.limsi.negotiate.restaurant.FR;

import fr.limsi.negotiate.Criterion;

public enum Localisation implements Criterion {
	//DOWNTOWN, NORTH_SIDE, EAST_SIDE, WEST_SIDE, SOUTH_SIDE;

	CENTRE_DE_PARIS,GARE_DU_NORD, MONTPARNASSE,  PRES_DE_LA_TOUR_EIFFEL, PERE_LACHAISE ;
	//CENTER_OF_PARIS,NEAR_EIFFEL_TOWER, PERE_LACHAISE, GARE_DU_NORD , MONTPARNASSE;
	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return Localisation.values();
	}

	@Override
	public String afficher() {

		String fr = "";
		switch(this) {
        case CENTRE_DE_PARIS:
        	fr += "pr\u00e9s du centre de paris";
        break;
        case MONTPARNASSE:
        	fr += "\u00e0 Montparnasse";
        break;
        case GARE_DU_NORD:
        	fr += "\u00e0 gare du Nord";
        break;
        case PRES_DE_LA_TOUR_EIFFEL:
        	fr += "pr\u00e9s de la tour Eiffel";
        break;
        case PERE_LACHAISE:
        	fr += "\u00e0 P\u00e8re lachaise";
        break;
        default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return fr;// StringToUTF8.convertToUTF8(fr);
  
	}
	
	@Override
	public String afficherLikes() {
	
			String fr = "les restaurants " +afficher() + "s";
			return fr;//StringToUTF8.convertToUTF8(fr);


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
		if(this.equals(CENTRE_DE_PARIS))
		 text =  "in the ";
		if(this.equals(PRES_DE_LA_TOUR_EIFFEL))
			text = " ";
		return  topic  + text + this.toString();
	}



}
