package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.Criterion;

public enum Cuisine implements Criterion {

   FRENCH, CHINESE, ITALIAN, TURKISH, JAPANESE;

   
	@Override
	public Criterion[] getValues() {
		// TODO Auto-generated method stub
		return (Cuisine.values());
	}
	public Class <? extends Criterion> getClasse(){
		return Cuisine.class;
	}
	
	public String getFrVersion(){
		String fr = "";
		switch(this) {
        case CHINESE:
        	fr = "CHINOIS";
        break;
        case FRENCH:
        	fr = "FRANÇAIS";
        break;
        case ITALIAN:
        	fr = "ITALIEN";
        break;
        case TURKISH:
        	fr = "TURQUE";
        break;
        case JAPANESE:
        	fr = "JAPONAIS";
        break;
        default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return fr;
  }
}
