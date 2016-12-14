package fr.limsi.PreferenceModel.restaurant;
import fr.limsi.preferenceModel.Criterion;

public enum Cost implements Criterion {
   
   EXPENSIVE, CHEAP, AFFRODABLE;

   public Criterion[] getValues() {
	// TODO Auto-generated method stub
	return (Cost.values());
   }
   
	@Override
	public String toString(){
		return this.name().toLowerCase();
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
