package fr.limsi.negotiate.restaurant;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;

public enum Restaurant implements Option {

	PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE, Atmosphere.QUIET),
	
	GRAMOPHONE (Cuisine.FRENCH, Cost.EXPENSIVE, Atmosphere.LIVELY),
	
	VICTOR(Cuisine.FRENCH, Cost.CHEAP, Atmosphere.QUIET),
	
	BEAUREPAIRE(Cuisine.FRENCH, Cost.CHEAP, Atmosphere.LIVELY),
	
	MAGOROKO (Cuisine.JAPANESE, Cost.EXPENSIVE, Atmosphere.LIVELY),
	
	TOKYO (Cuisine.JAPANESE, Cost.EXPENSIVE, Atmosphere.QUIET),
	
	SAMURA (Cuisine.JAPANESE, Cost.CHEAP, Atmosphere.LIVELY),
	
	MIZUSHI (Cuisine.JAPANESE, Cost.CHEAP, Atmosphere.QUIET),
	
	DRAGON (Cuisine.CHINESE, Cost.CHEAP, Atmosphere.LIVELY),
	
	YING (Cuisine.CHINESE, Cost.EXPENSIVE, Atmosphere.LIVELY),
	
	YONG (Cuisine.CHINESE, Cost.EXPENSIVE, Atmosphere.QUIET),
		
	JILIYA (Cuisine.CHINESE, Cost.CHEAP, Atmosphere.QUIET),
	
	PAPELLI (Cuisine.ITALIAN, Cost.CHEAP, Atmosphere.LIVELY),
	
	SALENTO (Cuisine.ITALIAN, Cost.EXPENSIVE, Atmosphere.LIVELY),
	
	ALBACIO (Cuisine.ITALIAN, Cost.EXPENSIVE, Atmosphere.QUIET),
		
	CIASA_MIA (Cuisine.ITALIAN, Cost.CHEAP, Atmosphere.QUIET),
	
	CAPPADOCE (Cuisine.TURKISH, Cost.CHEAP, Atmosphere.LIVELY),
		
	SIZIN (Cuisine.TURKISH, Cost.EXPENSIVE, Atmosphere.QUIET),
		
	BARAK (Cuisine.TURKISH, Cost.CHEAP, Atmosphere.QUIET),
	
	LEJANISSAIRE (Cuisine.TURKISH, Cost.EXPENSIVE, Atmosphere.LIVELY),
	
	ALBERT (Cuisine.FRENCH, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	GASTON (Cuisine.FRENCH, Cost.AFFRODABLE, Atmosphere.FAMILY), 

	TAOKAN (Cuisine.CHINESE, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	KIGNA (Cuisine.CHINESE, Cost.AFFRODABLE, Atmosphere.FAMILY), 

	TAVOLINO (Cuisine.ITALIAN, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	TRINFO (Cuisine.ITALIAN, Cost.AFFRODABLE, Atmosphere.FAMILY), 

	OTTOMAN(Cuisine.TURKISH, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	KIBELLE(Cuisine.TURKISH, Cost.AFFRODABLE, Atmosphere.FAMILY), 

	YOSHI (Cuisine.JAPANESE, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	SAKURA (Cuisine.JAPANESE, Cost.AFFRODABLE, Atmosphere.FAMILY), 
	 
	 ANAHUACALLI(Cuisine.MIXICAN, Cost.EXPENSIVE, Atmosphere.LIVELY), 

	 AZTECA(Cuisine.MIXICAN, Cost.EXPENSIVE, Atmosphere.QUIET), 

	 BOCAMEXA(Cuisine.MIXICAN, Cost.EXPENSIVE, Atmosphere.ROMANTIC), 

	 PAPPASITOS(Cuisine.MIXICAN, Cost.EXPENSIVE, Atmosphere.FAMILY), 

	 FAJITAS(Cuisine.MIXICAN, Cost.CHEAP, Atmosphere.LIVELY), 

	 CHIPOTLE(Cuisine.MIXICAN, Cost.CHEAP, Atmosphere.QUIET), 

	 OMIXICO(Cuisine.MIXICAN, Cost.CHEAP, Atmosphere.ROMANTIC), 

	 ELRANCHO(Cuisine.MIXICAN, Cost.CHEAP, Atmosphere.FAMILY), 

	 CHIDO(Cuisine.MIXICAN, Cost.AFFRODABLE, Atmosphere.LIVELY), 

	 ELNOPAL(Cuisine.MIXICAN, Cost.AFFRODABLE, Atmosphere.QUIET), 

	 OTACOS(Cuisine.MIXICAN, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	 ELGRINGO(Cuisine.MIXICAN, Cost.AFFRODABLE, Atmosphere.FAMILY), 

	 ITO(Cuisine.KOREAN, Cost.EXPENSIVE, Atmosphere.LIVELY), 

	 SOURA(Cuisine.KOREAN, Cost.EXPENSIVE, Atmosphere.QUIET), 

	 SONGSAN(Cuisine.KOREAN, Cost.EXPENSIVE, Atmosphere.ROMANTIC), 

	 ODORI(Cuisine.KOREAN, Cost.EXPENSIVE, Atmosphere.FAMILY), 

	 JANTCHI(Cuisine.KOREAN, Cost.CHEAP, Atmosphere.LIVELY), 

	 KOHYANG(Cuisine.KOREAN, Cost.CHEAP, Atmosphere.QUIET), 

	 MAROU(Cuisine.KOREAN, Cost.CHEAP, Atmosphere.ROMANTIC), 

	 SODAM(Cuisine.KOREAN, Cost.CHEAP, Atmosphere.FAMILY), 

	 SAMBUJA(Cuisine.KOREAN, Cost.AFFRODABLE, Atmosphere.LIVELY), 

	 SEOUL(Cuisine.KOREAN, Cost.AFFRODABLE, Atmosphere.QUIET), 

	 DOKKEBI(Cuisine.KOREAN, Cost.AFFRODABLE, Atmosphere.ROMANTIC), 

	 SEJONG(Cuisine.KOREAN, Cost.AFFRODABLE, Atmosphere.FAMILY);
	
	public final Cuisine cuisine;
	public final Cost cost;
	public final Atmosphere ambiance;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(Cost.class); add(Cuisine.class); add(Atmosphere.class); }};
	
	Restaurant (Cuisine cuisine, Cost cost, Atmosphere ambiance) {
		this.cuisine = cuisine;
		this.cost = cost;
		this.ambiance = ambiance;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Cuisine.class ? cuisine :
			c == Cost.class ? cost : c == Atmosphere.class? ambiance : null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}

	public String capitalize(String input){
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;

	}
	
	public String print(){
		  return "It's a "+ this.ambiance+", " +this.cost+" "+ this.cuisine+ " restaurant" ;
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return  this.toString();
	}

	@Override
	public String toString(){
		return this.capitalize(this.name());
	}

}
