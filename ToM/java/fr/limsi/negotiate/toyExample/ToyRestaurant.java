package fr.limsi.negotiate.toyExample;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;

public enum ToyRestaurant implements Option {


	
	MOULIN2(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	COUTURE(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	LETAPE(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	GLYCINES(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CAILLERE(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	PATISSON(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	ELDORADO(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	PAVILLON_LIMERE(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ARRIBA_MEXICO(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	AY_MEXICO(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CADILLAC(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	CHARLYS(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ELPOPOCA(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ELPEDRO(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	HOLLYWOOD_CAFE(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	ELCANTINA_MEXICAINE(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE);
///	
	public final ToyCuisine cuisine;
	public final ToyCost cost;
	public final ToyAtmosphere ambiance;
	public final ToyLocation district;
	
	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(ToyCost.class); add(ToyCuisine.class); add(ToyAtmosphere.class); add(ToyLocation.class); }};
	
	ToyRestaurant (ToyCuisine cuisine, ToyCost cost, ToyAtmosphere ambiance, ToyLocation district) {
		this.cuisine = cuisine;
		this.cost = cost;
		this.ambiance = ambiance;
		this.district = district;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == ToyCuisine.class ? cuisine :
			c == ToyCost.class ? cost : c == ToyAtmosphere.class? ambiance : c == ToyLocation.class ? district :null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}

	public String capitalize(String input){
		input = input.replace("_", " ");
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;

	}
	
	public String print(){
		  return "It's a "+ this.ambiance+", " +this.cost+" "+ this.cuisine+ " restaurant on the " + this.district;
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return  this.toString();
	}

	@Override
	public String toString(){
		return "Restaurant";
	}

}
