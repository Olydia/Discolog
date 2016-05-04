package fr.limsi.negotiate.restaurant;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;

public enum Restaurant implements Option {

	LE_PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE, Ambiance.CALM),
	
	LE_GRAMOPHONE (Cuisine.FRENCH, Cost.EXPENSIVE, Ambiance.NOISY),
	
	VICTOR(Cuisine.FRENCH, Cost.CHEAP, Ambiance.CALM),
	
	BEAUREPAIRE(Cuisine.FRENCH, Cost.CHEAP, Ambiance.NOISY),
	
	MAGOROKO (Cuisine.JAPANESE, Cost.EXPENSIVE, Ambiance.NOISY),
	
	TOKYO (Cuisine.JAPANESE, Cost.EXPENSIVE, Ambiance.CALM),
	
	SAMURA (Cuisine.JAPANESE, Cost.CHEAP, Ambiance.NOISY),
	
	MIZUSHI (Cuisine.JAPANESE, Cost.CHEAP, Ambiance.CALM),
	
	DRAGON (Cuisine.CHINESE, Cost.CHEAP, Ambiance.NOISY),
	
	YING (Cuisine.CHINESE, Cost.EXPENSIVE, Ambiance.NOISY),
	
	YONG (Cuisine.CHINESE, Cost.EXPENSIVE, Ambiance.CALM),
		
	JILIYA (Cuisine.CHINESE, Cost.CHEAP, Ambiance.CALM),
	
	PAPELLI (Cuisine.ITALIAN, Cost.CHEAP, Ambiance.NOISY),
	
	SALENTO (Cuisine.ITALIAN, Cost.EXPENSIVE, Ambiance.NOISY),
	
	AL_BACIO (Cuisine.ITALIAN, Cost.EXPENSIVE, Ambiance.CALM),
		
	CIASA_MIA (Cuisine.ITALIAN, Cost.CHEAP, Ambiance.CALM),
	
	CAPPADOCE (Cuisine.TURKISH, Cost.CHEAP, Ambiance.NOISY),
		
	SIZIN (Cuisine.TURKISH, Cost.EXPENSIVE, Ambiance.CALM),
		
	BARAK (Cuisine.TURKISH, Cost.CHEAP, Ambiance.CALM),
	
	LEJANISSAIRE (Cuisine.TURKISH, Cost.EXPENSIVE, Ambiance.NOISY);
	
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
	
	public Restaurant[] getRestaurants(){
		return Restaurant.values();
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return  this.toString();
	}



}
