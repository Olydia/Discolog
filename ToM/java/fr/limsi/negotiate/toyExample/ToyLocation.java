package fr.limsi.negotiate.toyExample;

import fr.limsi.negotiate.Criterion;

public enum ToyLocation implements Criterion {
	DOWNTOWN, EAST_SIDE/*, WEST_SIDE*/;
	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return ToyLocation.values();
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
