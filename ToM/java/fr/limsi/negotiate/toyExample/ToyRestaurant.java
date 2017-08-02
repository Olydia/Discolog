package fr.limsi.negotiate.toyExample;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;



public enum ToyRestaurant implements Option {


	A_CITADELLA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ANDIAMO_PIZZERIA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	ANGOLO_ITALIA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	ANTEPRIMA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ARCADIUS_RISTORANTE(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	ARLECCHINO(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	AU_SOLEIL_ITALIEN(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	BAROCCO(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	BEITALIE(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	BELLA_ITALIA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	BELLA_NAPOLI(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	BLUE_PASTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	BUONA_PASTA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	CAFFE_DITALIA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CAFFE_DI_PASTA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	CAPRI_PIZZA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CASA_28(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CASA_DA_VINCI(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	MOULIN(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ORANGERIE_DU_CHATEAU(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	COUTURE(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	PAPILLON(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	MARINE(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	GLYCINES(ToyCuisine.FRENCH, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	ETAPE(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	COURONNE(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	AIR_DU_TEMPS(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	ADELITA(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	TRIPOT(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	VIEUX_PRESSOIR(ToyCuisine.FRENCH, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	PASHA(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	GENTILLIERE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	ROSERAIE(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	BOUDOR(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	PLATANES(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	ANNEES_FOLLES(ToyCuisine.FRENCH, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	ARRIBA_MEXICO(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ATLANTIC_OAK(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	AY_MEXICO(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	AZTECA(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	BOCAMEXA(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CACTUS_TEX_MEX(ToyCuisine.MEXICAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	CADILLAC(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	CANDELARIA(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CHARLY_TEX(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	TEX_MEX(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CHIPOTLE(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CHIDO(ToyCuisine.MEXICAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	KRISTINA(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	MARTI(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CHICANOS(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	Viva_Mexico(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CHIDOCA(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CHIPO(ToyCuisine.MEXICAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE)

;


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
