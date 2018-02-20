package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

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
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
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
