package fr.limsi.negotiate.restaurant.FR;

import java.util.ArrayList;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;

public class ModelDePreferencesTotal {

	public  Negotiation<Restaurant>  model1(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Ambiance.class, Prix.class);
		d1_criteria.addPreference(Prix.class, Cuisine.class);
		d1_criteria.addPreference(Localisation.class, Ambiance.class);


		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.CORÉEN);
		d1_cuisine.addPreference(Cuisine.CORÉEN, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.MEXICAIN);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.TURQUE);
		d1_cuisine.addPreference(Cuisine.TURQUE, Cuisine.ITALIEN);
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.FRANÇAIS);
//		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.C);
//		d1_cuisine.addPreference(Cuisine.C, Cuisine.K);
//		d1_cuisine.addPreference(Cuisine.K, Cuisine.M);




		
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d1_atmosphere.addPreference(Ambiance.ANIME, Ambiance.CALME);
		d1_atmosphere.addPreference(Ambiance.ANIME, Ambiance.ROMANTIQUE);
		d1_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.FAMILIAL);
		d1_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.CALME);
		d1_atmosphere.addPreference(Ambiance.CALME, Ambiance.COSY);
		d1_atmosphere.addPreference(Ambiance.COSY, Ambiance.MODERNE);



		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);

		Self_Ci<Localisation> d1_location =  new Self_Ci<>(Localisation.class);
		d1_location.addPreference(Localisation.PRES_DE_LA_TOUR_EIFFEL, Localisation.CENTRE_DE_PARIS);
		d1_location.addPreference(Localisation.CENTRE_DE_PARIS, Localisation.PERE_LACHAISE);
		d1_location.addPreference(Localisation.PERE_LACHAISE, Localisation.GARE_DU_NORD);
		d1_location.addPreference(Localisation.PERE_LACHAISE, Localisation.MONTPARNASSE);
		d1_location.addPreference(Localisation.GARE_DU_NORD, Localisation.MONTPARNASSE);

		CriterionNegotiation<Localisation> location = new CriterionNegotiation<>(d1_location);
		
		Self_Ci<Prix> d1_cost = new Self_Ci<Prix>(Prix.class);
		d1_cost.addPreference(Prix.GASTRONOMIQUE, Prix.ABORDABLE);
		d1_cost.addPreference(Prix.ABORDABLE, Prix.BAS_PRIX);
		
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d1_criteria, Restaurant.class);
		return model1;

	}
	
	public   Negotiation<Restaurant>  model3(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Cuisine.class, Prix.class);
		d1_criteria.addPreference(Prix.class, Ambiance.class);
		d1_criteria.addPreference(Ambiance.class, Localisation.class);


		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.TURQUE, Cuisine.MEXICAIN);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.ITALIEN);
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.CHINOIS);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.CORÉEN);
		d1_cuisine.addPreference(Cuisine.CORÉEN, Cuisine.JAPONAIS);




		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d1_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.ROMANTIQUE);
		d1_atmosphere.addPreference(Ambiance.CALME, Ambiance.ANIME);
		d1_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.ANIME);
		d1_atmosphere.addPreference(Ambiance.CALME, Ambiance.FAMILIAL);
		d1_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.MODERNE);
		d1_atmosphere.addPreference(Ambiance.MODERNE, Ambiance.COSY);



		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);

		Self_Ci<Localisation> d1_location =  new Self_Ci<>(Localisation.class);
		d1_location.addPreference(Localisation.MONTPARNASSE, Localisation.GARE_DU_NORD);
		d1_location.addPreference(Localisation.MONTPARNASSE,Localisation.PERE_LACHAISE);
		d1_location.addPreference(Localisation.GARE_DU_NORD, Localisation.PERE_LACHAISE);
		d1_location.addPreference(Localisation.PERE_LACHAISE, Localisation.PRES_DE_LA_TOUR_EIFFEL);
		d1_location.addPreference(Localisation.PERE_LACHAISE, Localisation.CENTRE_DE_PARIS);
		d1_location.addPreference(Localisation.CENTRE_DE_PARIS, Localisation.PRES_DE_LA_TOUR_EIFFEL);

		CriterionNegotiation<Localisation> location = new CriterionNegotiation<>(d1_location);
		

		Self_Ci<Prix> d1_cost = new Self_Ci<Prix>(Prix.class);
		d1_cost.addPreference(Prix.BAS_PRIX, Prix.ABORDABLE);
		d1_cost.addPreference(Prix.ABORDABLE, Prix.GASTRONOMIQUE);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d1_criteria, Restaurant.class);
		return model1;

	}
	
	
	public   Negotiation<Restaurant>  model2(){


		Self_C<Restaurant>  d2_criteria = new Self_C<Restaurant> (Restaurant.class);
		d2_criteria.addPreference(Localisation.class, Ambiance.class);
		d2_criteria.addPreference(Ambiance.class, Prix.class);
		d2_criteria.addPreference(Prix.class, Cuisine.class);


		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.setType(Cuisine.class);
		
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.CORÉEN);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.CORÉEN);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.ITALIEN);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.MEXICAIN);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.TURQUE);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.ITALIEN);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.TURQUE, Cuisine.CORÉEN);




		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d2_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d2_atmosphere.addPreference(Ambiance.CALME, Ambiance.ANIME);
		d2_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.ANIME);
		d2_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.ROMANTIQUE);
		d2_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.CALME);
		d2_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.CALME);


		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d2_atmosphere);

		
		Self_Ci<Localisation> d2_location =  new Self_Ci<>(Localisation.class);
		d2_location.addPreference(Localisation.MONTPARNASSE, Localisation.GARE_DU_NORD);
		d2_location.addPreference(Localisation.GARE_DU_NORD,Localisation.CENTRE_DE_PARIS);
		d2_location.addPreference(Localisation.PRES_DE_LA_TOUR_EIFFEL, Localisation.CENTRE_DE_PARIS);
		d2_location.addPreference(Localisation.PRES_DE_LA_TOUR_EIFFEL, Localisation.PERE_LACHAISE);
		d2_location.addPreference(Localisation.CENTRE_DE_PARIS, Localisation.PERE_LACHAISE);
		CriterionNegotiation<Localisation> location = new CriterionNegotiation<>(d2_location);
		

		Self_Ci<Prix> d2_cost = new Self_Ci<Prix>(Prix.class);
		d2_cost.addPreference(Prix.BAS_PRIX, Prix.ABORDABLE);
		d2_cost.addPreference(Prix.ABORDABLE, Prix.GASTRONOMIQUE);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d2_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model2 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d2_criteria, Restaurant.class);
		return model2;

	}
	
	
	
	public   Negotiation<Restaurant>  model4(){


		Self_C<Restaurant>  d4_criteria = new Self_C<Restaurant> (Restaurant.class);
		
		d4_criteria.addPreference(Localisation.class, Ambiance.class);		
		d4_criteria.addPreference(Ambiance.class, Prix.class);
		d4_criteria.addPreference(Prix.class, Cuisine.class);

		Self_Ci<Cuisine> d4_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
//		d4_cuisine.addPreference(Cuisine.KOREAN, Cuisine.CHINESE);
//		d4_cuisine.addPreference(Cuisine.CHINESE, Cuisine.ITALIAN);
//		d4_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.TURKISH);
//		d4_cuisine.addPreference(Cuisine.CHINESE, Cuisine.MEXICAN);
//		d4_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.MEXICAN);
//		d4_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.FRENCH);
//		d4_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.JAPANESE);
//		d4_cuisine.addPreference(Cuisine.TURKISH, Cuisine.JAPANESE);
//		d4_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.FRENCH);
		
		d4_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.MEXICAIN);
		d4_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.CORÉEN);
		d4_cuisine.addPreference(Cuisine.CORÉEN, Cuisine.MEXICAIN);
		d4_cuisine.addPreference(Cuisine.CORÉEN, Cuisine.TURQUE);
		d4_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.ITALIEN);
		d4_cuisine.addPreference(Cuisine.TURQUE, Cuisine.ITALIEN);
		d4_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.FRANÇAIS);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d4_cuisine);

		Self_Ci<Ambiance> d4_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d4_atmosphere.addPreference(Ambiance.CALME, Ambiance.ANIME);
		d4_atmosphere.addPreference(Ambiance.ANIME, Ambiance.FAMILIAL);
		d4_atmosphere.addPreference(Ambiance.ANIME, Ambiance.ROMANTIQUE);
		d4_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.ROMANTIQUE);

		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d4_atmosphere);

		Self_Ci<Localisation> d4_location =  new Self_Ci<>(Localisation.class);
		d4_location.addPreference(Localisation.MONTPARNASSE, Localisation.GARE_DU_NORD);
		d4_location.addPreference(Localisation.MONTPARNASSE,Localisation.PERE_LACHAISE);
		d4_location.addPreference(Localisation.GARE_DU_NORD, Localisation.PERE_LACHAISE);
		d4_location.addPreference(Localisation.PERE_LACHAISE, Localisation.PRES_DE_LA_TOUR_EIFFEL);
		d4_location.addPreference(Localisation.PERE_LACHAISE, Localisation.CENTRE_DE_PARIS);
		d4_location.addPreference(Localisation.CENTRE_DE_PARIS, Localisation.PRES_DE_LA_TOUR_EIFFEL);
		CriterionNegotiation<Localisation> location = new CriterionNegotiation<>(d4_location);
		

		Self_Ci<Prix> d4_cost = new Self_Ci<Prix>(Prix.class);
		d4_cost.addPreference(Prix.GASTRONOMIQUE, Prix.ABORDABLE);
		d4_cost.addPreference(Prix.ABORDABLE, Prix.BAS_PRIX);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d4_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d4_criteria, Restaurant.class);
		return model1;

	}
	
	
	public ArrayList<Negotiation<Restaurant>> getModels(){
		ArrayList<Negotiation<Restaurant>> models = new ArrayList<Negotiation<Restaurant>>();
		//models.add(this.model1());
	//	models.add(this.model2());
		models.add(this.model3());
		models.add(this.model4());
	//	models.add(this.model5());
		
		return models;
	}
	
	public static void main (String[] args)  {
		
		ModelDePreferencesTotal m = new ModelDePreferencesTotal();
		Negotiation<Restaurant> m1 = m.model4();
		System.out.println(m1.getValueNegotiation(Cuisine.class).getSelf().getSelfPreferences());
		for(Cuisine e: Cuisine.values()){
			System.out.println( e + " " +m1.getValueNegotiation(e.getClass()).getSelf().satisfaction(e));
		}
		
	}
	
		

}
