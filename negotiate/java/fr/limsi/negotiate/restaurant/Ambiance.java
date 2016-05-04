package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.Criterion;

public enum Ambiance implements Criterion{

	 NOISY, CALM;

	 public Criterion[] getValues() {
	 	// TODO Auto-generated method stub
	 	return (Ambiance.values());
	 }
	 
		public String getFrVersion(){
			String fr = "";
			switch(this) {
	        case NOISY:
	        	fr = "BRUYANT";
	        break;
	        case CALM:
	        	fr = "CALME";
	        break;
	        default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
	        break;
	     }
			return fr;
	  }

}
