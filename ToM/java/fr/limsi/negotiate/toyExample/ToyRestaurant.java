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
	ELCANTINA_MEXICAINE(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ANGOLO_LITALIA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	AU_SOLEIL_ITALIEN(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	BELITALIE(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CASA_DEL_ZIO(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CASA_ITALIANA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	CIAO_PASTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	COSTA_COSTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	DEL_ARTE(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	DELICES_DITALIE(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ELIOS_RISTORANTE(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	FINDI(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	GIOVANYS_RISTORANTE(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	IL_COCCODRILLO(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	IL_RISTORANTE(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	ITALIA_PIZZA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	TILLEULS(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
//	VOIDOR(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.NORTH_SIDE),
	TRAIT_DUNION(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
//	CERDAGNE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
//	GRANGE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.SOUTH_SIDE),
	MAISON_BLANCHE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
//	MOULIN_DE_PLANCHE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.NORTH_SIDE),
	SAINT_HONORE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
//	CHATEAU_DE_BOUESSE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
//	LOREE_DE_CHAMBORD(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.SOUTH_SIDE),
//	TAJ_MAHAL(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.DOWNTOWN),
//	LOREE_DU_BOIS(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.NORTH_SIDE),
//	BELSAISONS(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.EAST_SIDE),
//	CHATEAU_DE_BAGNOLS(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.WEST_SIDE),
//	COCINA_MUNDI(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.SOUTH_SIDE),
	PETIT_BONNEVAL(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
//	CASINO_DE_ROYAT(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.NORTH_SIDE),
	CHATEAU_DE_VERIE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
//	CHEZ_FELIX(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
//	MONSTRE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.SOUTH_SIDE),
	MEXICO_CITY(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
//	MEXICO_LINDO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.NORTH_SIDE),
	MEXICO_LOCO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
//	MEXICO_MAGICO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
//	MEXICOR(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.SOUTH_SIDE),
	MUCHO_MEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
//	O_MEXICO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.NORTH_SIDE),
//	OTACOS(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
//	PAPPASITOS(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
//	RANCH_RIVER(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.SOUTH_SIDE),
//	SOLOTEX_MEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.DOWNTOWN),
//	TACOMEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.NORTH_SIDE),
//	TEX_MEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.EAST_SIDE),
//	ELPACHUCA(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.WEST_SIDE),
//	ELROSARIO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.ROMANTIC, ToyLocation.SOUTH_SIDE),
	MEX_SOUTH(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
//	TEXAS_CITY(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.NORTH_SIDE),
	THE_WESTERNER_TEX_MEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE);
//	TRADING_POST_TEX_MEX(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
//	VIVA_MEXICO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.SOUTH_SIDE);


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
		return this.capitalize(this.name());
	}

}
