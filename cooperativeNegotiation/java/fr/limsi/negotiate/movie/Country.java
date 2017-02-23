package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.Criterion;

public enum Country implements Criterion{
	FRANCE, US, CANADA, BRITAIN, SPAIN;
	
	   public Criterion[] getValues() {
			// TODO Auto-generated method stub
			return (Country.values());
		   }

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
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
	
}
