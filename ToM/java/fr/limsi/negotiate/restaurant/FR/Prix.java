package fr.limsi.negotiate.restaurant.FR;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Prix implements Criterion {
   
    ABORDABLE, BAS_PRIX, CHIC;

   public Criterion[] getValues() {
	// TODO Auto-generated method stub
	return (Prix.values());
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

	public String afficher(){
		String fr = "";
		switch(this) {
        case CHIC:
        	fr = "chic";
        break;
        case BAS_PRIX:
        	fr = "bas prix";
        break;
        case ABORDABLE:
			fr = "abordable";
			break;
		
        default:
	        System.out.println("tHE VALUE" + this.toString()+ " DOESN'T EXIST");
        break;
     }
		return StringToUTF8.convertToUTF8(fr);
  }
	
	@Override
	public String afficherLikes() {
		String fr= "les restaurants " +afficher();
			if (this.equals(BAS_PRIX))
				return  StringToUTF8.convertToUTF8("les restaurants à " +afficher());
			return StringToUTF8.convertToUTF8(fr + "s");

	}


}
