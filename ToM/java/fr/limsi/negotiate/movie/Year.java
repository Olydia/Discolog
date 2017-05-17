package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.Criterion;

public enum Year implements Criterion {
	//THE_SEXTIES, THE_SEVENTIES, 
	THE_SEXTIES, THE_SEVENTIES, THE_EIGHTIES, THE_NINETIES, TWENTY;

	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return Year.values();
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
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
	
}
