package fr.limsi.negotiate.toyExample;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;



public enum ToyRestaurant implements Option {

	A_CITADELLA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ANDIAMO_PIZZERIA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	ANGOLO_ITALIA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	ANTEPRIMA(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	ARCADIUS_RISTORANTE(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	ARLECCHINO(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	AU_SOLEIL_ITALIEN(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	BAROCCO(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	BEITALIE(ToyCuisine.ITALIAN, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	BELLA_ITALIA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	BELLA_NAPOLI(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	BLUE_PASTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	BUONA_PASTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	CAFFE_DITALIA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CAFFE_DI_PASTA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	CAPRI_PIZZA(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CASA_28(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	CASA_DA_VINCI(ToyCuisine.ITALIAN, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	CASA_DEL_ZIO(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	CASA_ITALIA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CASA_ITALIANA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	CASA_PASTAS(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	CHALET_DU_GRAND_ARC(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CHEZ_GIOVANNI_TRATTORIA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	CIAO_PASTA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CORNER_PASTA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	COSTA_COSTA(ToyCuisine.ITALIAN, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	AKI(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	EBIS(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	EDOKKO(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	FOUJITA(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	SUSHI_PLACE(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	HIGUMA(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	KILALA(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	KINUGAWA(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	KOESTU(ToyCuisine.JAPANESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	MATSUDA(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	SAKURA(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	MIYOSHI(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	MIDORY(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	NARITAKE(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	NODAIWA(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	SANUKIA(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	SAPPORO(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	TAKARA(ToyCuisine.JAPANESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	YASUBE(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	YAKINIKU(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	AI(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	HOKKAIDO(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	TOKYO(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	FUKISHIMA(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	AKITA(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	BIZAN(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	JUJI(ToyCuisine.JAPANESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	ASIA_PARFUM(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ANCIAN_DRAGON(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	ASIA_PLACE(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	ASIA_STAR(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	BEIJING(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	CHANGAIS(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	CHIENG_MAI(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	CHIM(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	CHINA_CLUB(ToyCuisine.CHINESE, ToyCost.EXPENSIVE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	CHINA_EXPRESS(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	CHINA_STAR(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	CHINATOWN(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	CHINESE_WALL_(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	CHO_GAO(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	DAI_DUONG(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	DONG(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	DRAGON_(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	DRAGON_DOR(ToyCuisine.CHINESE, ToyCost.CHEAP, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE),
	ESCALE_DE_CHINE(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.DOWNTOWN),
	ETOILE_DE_CHINE(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.EAST_SIDE),
	GOLD_DRAGON(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.LIVELY, ToyLocation.WEST_SIDE),
	HAO_HAO(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.DOWNTOWN),
	HENG_LAY(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.EAST_SIDE),
	INDOCHINE(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.QUIET, ToyLocation.WEST_SIDE),
	JADE(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.DOWNTOWN),
	JILIYA(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.EAST_SIDE),
	JUJUBE(ToyCuisine.CHINESE, ToyCost.AFFORDABLE, ToyAtmosphere.FAMILY, ToyLocation.WEST_SIDE)

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

	@Override
	public String afficher() {
		// TODO Auto-generated method stub
		return null;
	}

}
