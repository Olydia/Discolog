package fr.limsi.negotiate.restaurant;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;

public enum Restaurant implements Option {

	PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE, Ambiance.QUIET),
	
	GRAMOPHONE (Cuisine.FRENCH, Cost.EXPENSIVE, Ambiance.LIVELY),
	
	VICTOR(Cuisine.FRENCH, Cost.CHEAP, Ambiance.QUIET),
	
	BEAUREPAIRE(Cuisine.FRENCH, Cost.CHEAP, Ambiance.LIVELY),
	
	MAGOROKO (Cuisine.JAPANESE, Cost.EXPENSIVE, Ambiance.LIVELY),
	
	TOKYO (Cuisine.JAPANESE, Cost.EXPENSIVE, Ambiance.QUIET),
	
	SAMURA (Cuisine.JAPANESE, Cost.CHEAP, Ambiance.LIVELY),
	
	MIZUSHI (Cuisine.JAPANESE, Cost.CHEAP, Ambiance.QUIET),
	
	DRAGON (Cuisine.CHINESE, Cost.CHEAP, Ambiance.LIVELY),
	
	YING (Cuisine.CHINESE, Cost.EXPENSIVE, Ambiance.LIVELY),
	
	YONG (Cuisine.CHINESE, Cost.EXPENSIVE, Ambiance.QUIET),
		
	JILIYA (Cuisine.CHINESE, Cost.CHEAP, Ambiance.QUIET),
	
	PAPELLI (Cuisine.ITALIAN, Cost.CHEAP, Ambiance.LIVELY),
	
	SALENTO (Cuisine.ITALIAN, Cost.EXPENSIVE, Ambiance.LIVELY),
	
	ALBACIO (Cuisine.ITALIAN, Cost.EXPENSIVE, Ambiance.QUIET),
		
	CIASA_MIA (Cuisine.ITALIAN, Cost.CHEAP, Ambiance.QUIET),
	
	CAPPADOCE (Cuisine.TURKISH, Cost.CHEAP, Ambiance.LIVELY),
		
	SIZIN (Cuisine.TURKISH, Cost.EXPENSIVE, Ambiance.QUIET),
		
	BARAK (Cuisine.TURKISH, Cost.CHEAP, Ambiance.QUIET),
	
	LEJANISSAIRE (Cuisine.TURKISH, Cost.EXPENSIVE, Ambiance.LIVELY);
	
	public final Cuisine cuisine;
	public final Cost cost;
	public final Ambiance ambiance;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(Cost.class); add(Cuisine.class); add(Ambiance.class); }};
	
	Restaurant (Cuisine cuisine, Cost cost, Ambiance ambiance) {
		this.cuisine = cuisine;
		this.cost = cost;
		this.ambiance = ambiance;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Cuisine.class ? cuisine :
			c == Cost.class ? cost : c == Ambiance.class? ambiance : null; // throw error? 
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
