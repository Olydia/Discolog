package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;

public class totalOrderedModels {

	public   Negotiation<Restaurant>  model1(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Atmosphere.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.TURKISH);

		
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.QUIET);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.FAMILY);
		d1_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.QUIET);

		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.EXPENSIVE, Cost.AFFORDABLE);
		d1_cost.addPreference(Cost.AFFORDABLE, Cost.CHEAP);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}
	
	public   Negotiation<Restaurant>  model3(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Cuisine.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Atmosphere.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.KOREAN);



		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		d1_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.FAMILY);

		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.CHEAP, Cost.AFFORDABLE);
		d1_cost.addPreference(Cost.AFFORDABLE, Cost.EXPENSIVE);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}
	
	
	public   Negotiation<Restaurant>  model2(){


		Self_C<Restaurant>  d2_criteria = new Self_C<Restaurant> (Restaurant.class);
		d2_criteria.addPreference(Cost.class, Cuisine.class);
		d2_criteria.addPreference(Cuisine.class,Atmosphere.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.setType(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.FRENCH);



		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d2_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d2_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		d2_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.ROMANTIC);
		d2_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.QUIET);


		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d2_atmosphere);


		Self_Ci<Cost> d2_cost = new Self_Ci<Cost>(Cost.class);
		d2_cost.addPreference(Cost.CHEAP, Cost.AFFORDABLE);
		d2_cost.addPreference(Cost.AFFORDABLE, Cost.EXPENSIVE);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d2_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model2 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d2_criteria, Restaurant.class);
		return model2;

	}
	
	// similar to model 1
	public   Negotiation<Restaurant>  model4(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Atmosphere.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.TURKISH);

		
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.FAMILY);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.QUIET);
		d1_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.FAMILY);

		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.EXPENSIVE, Cost.AFFORDABLE);
		d1_cost.addPreference(Cost.AFFORDABLE, Cost.CHEAP);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}

}
