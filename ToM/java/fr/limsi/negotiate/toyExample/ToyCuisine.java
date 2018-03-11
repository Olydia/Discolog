package fr.limsi.negotiate.toyExample;
import fr.limsi.negotiate.Criterion;

public enum ToyCuisine implements Criterion {

   FRENCH,
   ITALIAN
  ,JAPANESE
  , CHINESE
   ;

   
	@Override
	public Criterion[] getValues() {
		
		// TODO Auto-generated method stub
		return (ToyCuisine.values());
	}
	public Class <? extends Criterion> getClasse(){
		return ToyCuisine.class;
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
	
	public String afficher(){
		String fr = "";
//		switch(this) {
//        case MEXICAN:
//        	fr = "MEXICAIN";
//        break;
//        case FRENCH:
//        	fr = "FRANÇAIS";
//        break;
        
        
 //       default:
	        System.out.println("THE VALUE" + this.toString()+ " DOESN'T EXIST");
 //       break;
 //    }
		return fr;
  }
	
	@Override
	public String afficherLikes() {
		// TODO Auto-generated method stub
		return null;
	}
}
