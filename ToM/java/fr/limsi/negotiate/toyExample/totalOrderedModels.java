package fr.limsi.negotiate.toyExample;


import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;

public class totalOrderedModels {

	public   Negotiation<ToyRestaurant>  model1(){


		Self_C<ToyRestaurant>  d1_criteria = new Self_C<ToyRestaurant> (ToyRestaurant.class);
		d1_criteria.addPreference(ToyAtmosphere.class, ToyCost.class);
		d1_criteria.addPreference(ToyCost.class, ToyCuisine.class);
		d1_criteria.addPreference(ToyLocation.class, ToyAtmosphere.class);


		Self_Ci<ToyCuisine> d1_cuisine = new Self_Ci <ToyCuisine>(ToyCuisine.class);
	
		d1_cuisine.addPreference(ToyCuisine.MEXICAN, ToyCuisine.FRENCH);

		
		CriterionNegotiation<ToyCuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<ToyAtmosphere> d1_ToyAtmosphere = new Self_Ci<ToyAtmosphere>(ToyAtmosphere.class);
		d1_ToyAtmosphere.addPreference(ToyAtmosphere.LIVELY, ToyAtmosphere.QUIET);


		CriterionNegotiation<ToyAtmosphere> atmospher = new CriterionNegotiation<>(d1_ToyAtmosphere);

		Self_Ci<ToyLocation> d1_ToyLocation =  new Self_Ci<>(ToyLocation.class);
		d1_ToyLocation.addPreference(ToyLocation.DOWNTOWN, ToyLocation.EAST_SIDE);

		CriterionNegotiation<ToyLocation> ToyLocation = new CriterionNegotiation<>(d1_ToyLocation);
		
		Self_Ci<ToyCost> d1_ToyCost = new Self_Ci<ToyCost>(ToyCost.class);
		d1_ToyCost.addPreference(ToyCost.EXPENSIVE, ToyCost.AFFORDABLE);
		d1_ToyCost.addPreference(ToyCost.AFFORDABLE, ToyCost.CHEAP);
		
		CriterionNegotiation<ToyCost> ToyCost = new CriterionNegotiation<>(d1_ToyCost);

		@SuppressWarnings("unchecked")
		Negotiation<ToyRestaurant> model1 = new Negotiation<ToyRestaurant> 
		(new CriterionNegotiation[] {ToyCost, cuisine, atmospher, ToyLocation}, d1_criteria, ToyRestaurant.class);
		return model1;

	}
	
	
	
	
	
		

}
