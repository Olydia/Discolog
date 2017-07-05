package fr.limsi.negotiate.toyExample;


import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;

public class ToyModel {

	public   Negotiation<ToyRestaurant>  model1(){


		Self_C<ToyRestaurant>  d1_criteria = new Self_C<ToyRestaurant> (ToyRestaurant.class);
		d1_criteria.addPreference(ToyLocation.class, ToyAtmosphere.class);
		d1_criteria.addPreference(ToyCost.class, ToyAtmosphere.class);
		d1_criteria.addPreference(ToyAtmosphere.class, ToyCuisine.class);


		Self_Ci<ToyCuisine> d1_cuisine = new Self_Ci <ToyCuisine>(ToyCuisine.class);
		
		//d1_cuisine.addPreference(ToyCuisine.ITALIAN, ToyCuisine.MEXICAN);
		d1_cuisine.addPreference(ToyCuisine.MEXICAN, ToyCuisine.FRENCH);

		
		CriterionNegotiation<ToyCuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<ToyAtmosphere> d1_ToyAtmosphere = new Self_Ci<ToyAtmosphere>(ToyAtmosphere.class);
		d1_ToyAtmosphere.addPreference(ToyAtmosphere.LIVELY, ToyAtmosphere.QUIET);
		//d1_ToyAtmosphere.addPreference(ToyAtmosphere.QUIET, ToyAtmosphere.FAMILY);


		CriterionNegotiation<ToyAtmosphere> atmospher = new CriterionNegotiation<>(d1_ToyAtmosphere);

		Self_Ci<ToyLocation> d1_ToyLocation =  new Self_Ci<>(ToyLocation.class);
		d1_ToyLocation.addPreference(ToyLocation.DOWNTOWN, ToyLocation.EAST_SIDE);
		//d1_ToyLocation.addPreference(ToyLocation.EAST_SIDE, ToyLocation.WEST_SIDE);

		CriterionNegotiation<ToyLocation> ToyLocation = new CriterionNegotiation<>(d1_ToyLocation);
		
		Self_Ci<ToyCost> d1_ToyCost = new Self_Ci<ToyCost>(ToyCost.class);
//		d1_ToyCost.addPreference(ToyCost.EXPENSIVE, ToyCost.AFFORDABLE);
//		d1_ToyCost.addPreference(ToyCost.AFFORDABLE, ToyCost.CHEAP);
//		
		d1_ToyCost.addPreference(ToyCost.EXPENSIVE, ToyCost.CHEAP);

		CriterionNegotiation<ToyCost> ToyCost = new CriterionNegotiation<>(d1_ToyCost);

		@SuppressWarnings("unchecked")
		Negotiation<ToyRestaurant> model1 = new Negotiation<ToyRestaurant> 
		(new CriterionNegotiation[] {ToyCost, cuisine, atmospher, ToyLocation}, d1_criteria, ToyRestaurant.class);
		return model1;

	}
	
	public   Negotiation<ToyRestaurant>  model2(){


		Self_C<ToyRestaurant>  d2_criteria = new Self_C<ToyRestaurant> (ToyRestaurant.class);
		d2_criteria.addPreference(ToyAtmosphere.class, ToyCost.class);
		d2_criteria.addPreference(ToyCost.class, ToyCuisine.class);
		d2_criteria.addPreference(ToyCuisine.class, ToyLocation.class);


		Self_Ci<ToyCuisine> d2_cuisine = new Self_Ci <ToyCuisine>(ToyCuisine.class);
	
		d2_cuisine.addPreference(ToyCuisine.FRENCH, ToyCuisine.MEXICAN);
	//	d2_cuisine.addPreference(ToyCuisine.MEXICAN, ToyCuisine.ITALIAN);

		
		CriterionNegotiation<ToyCuisine> cuisine = new CriterionNegotiation<>(d2_cuisine);

		Self_Ci<ToyAtmosphere> d2_ToyAtmosphere = new Self_Ci<ToyAtmosphere>(ToyAtmosphere.class);
		d2_ToyAtmosphere.addPreference(ToyAtmosphere.QUIET, ToyAtmosphere.LIVELY);
	//	d2_ToyAtmosphere.addPreference(ToyAtmosphere.LIVELY, ToyAtmosphere.FAMILY);


		CriterionNegotiation<ToyAtmosphere> atmospher = new CriterionNegotiation<>(d2_ToyAtmosphere);

		Self_Ci<ToyLocation> d2_ToyLocation =  new Self_Ci<>(ToyLocation.class);
		d2_ToyLocation.addPreference(ToyLocation.EAST_SIDE, ToyLocation.DOWNTOWN);
	//	d2_ToyLocation.addPreference(ToyLocation.DOWNTOWN, ToyLocation.WEST_SIDE);

		CriterionNegotiation<ToyLocation> ToyLocation = new CriterionNegotiation<>(d2_ToyLocation);
		
		Self_Ci<ToyCost> d2_ToyCost = new Self_Ci<ToyCost>(ToyCost.class);
		d2_ToyCost.addPreference(ToyCost.CHEAP, ToyCost.EXPENSIVE);

//		d2_ToyCost.addPreference(ToyCost.CHEAP, ToyCost.AFFORDABLE);
//		d2_ToyCost.addPreference(ToyCost.AFFORDABLE, ToyCost.EXPENSIVE);
//		
		CriterionNegotiation<ToyCost> ToyCost = new CriterionNegotiation<>(d2_ToyCost);

		@SuppressWarnings("unchecked")
		Negotiation<ToyRestaurant> model2 = new Negotiation<ToyRestaurant> 
		(new CriterionNegotiation[] {ToyCost, cuisine, atmospher, ToyLocation}, d2_criteria, ToyRestaurant.class);
		return model2;

	}
	
	
	
	
		

}
