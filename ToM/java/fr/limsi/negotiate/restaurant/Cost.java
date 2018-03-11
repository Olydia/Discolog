package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.StringToUTF8;

public enum Cost implements Criterion {
   
    AFFORDABLE, CHEAP, EXPENSIVE;

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

	public String afficher(){
		String fr = "";
		switch(this) {
        case EXPENSIVE:
        	fr = "gastronomique";
        break;
        case CHEAP:
        	fr = "bas prix";
        break;
        case AFFORDABLE:
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
			if (this.equals(CHEAP))
				return  StringToUTF8.convertToUTF8(fr);
			return StringToUTF8.convertToUTF8(fr + "s");

	}


}
