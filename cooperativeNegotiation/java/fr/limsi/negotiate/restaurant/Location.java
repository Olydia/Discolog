package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Location implements Criterion {
	DOWNTOWN, NORTH_SIDE, EAST_SIDE, WEST_SIDE, SOUTH_SIDE;
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
		String text = (this.equals(DOWNTOWN)? " " : " on the ");
		return  topic  + text + this.toString();
	}



}
