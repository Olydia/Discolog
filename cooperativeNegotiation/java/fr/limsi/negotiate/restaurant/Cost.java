package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.Criterion;

public enum Cost implements Criterion {
   
   EXPENSIVE, CHEAP, AFFORDABLE;

   public Criterion[] getValues() {
	// TODO Auto-generated method stub
	return (Cost.values());
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

	public String getFrVersion(){
		String fr = "";
		switch(this) {
        case EXPENSIVE:
        	fr = "CHER";
        break;
        case CHEAP:
        	fr = "BAS";
        break;
        default:
	        System.out.println("tHE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return fr;
  }

}
