package fr.limsi.negotiate.restaurant.FR;

import fr.limsi.negotiate.Criterion;

public enum Localisation implements Criterion {

	CENTRE_DE_PARIS,GARE_DU_NORD, MONTPARNASSE,  PRES_DE_LA_TOUR_EIFFEL, PERE_LACHAISE ;
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
        	fr += "Dans le centre de Paris";
        break;
        case MONTPARNASSE:
        	fr += "pr\u00e8s de Montparnasse";
        break;
        case GARE_DU_NORD:
        	fr += "pr\u00e8s de Gare du Nord";
        break;
        case PRES_DE_LA_TOUR_EIFFEL:
        	fr += "pr\u00e8s de la tour Eiffel";
        break;
        case PERE_LACHAISE:
        	fr += "pr\u00e8s du P\u00e8re lachaise";
        break;
        default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return fr;// StringToUTF8.convertToUTF8(fr);
  
	}
	
	@Override
	public String afficherLikes() {
				return "les restaurants " +afficher() ;


	}
	
	@Override
	public String toString(){
		String input = this.name();
		input = input.replace("_", " ");
		return input.toLowerCase();
		//return this.afficher();
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
