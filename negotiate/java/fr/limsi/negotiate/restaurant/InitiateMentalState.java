package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionPrefModel;
import fr.limsi.negotiate.CriterionPreference;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.OptionPrefModel;
import fr.limsi.negotiate.ValuePreference;

public class InitiateMentalState {

	private  static Negotiation<Restaurant> restaurantNegotiation;

	@SuppressWarnings("unchecked")
	public static  Negotiation<Restaurant> Initialise () {
		// 1. Define lydia preference model on each criterion of restaurant
				// 1.1. Preference model on cuisine
				CriterionPrefModel<Cuisine> lydia_cuisine = new CriterionPrefModel<Cuisine>();
				lydia_cuisine.setType(Cuisine.CHINESE);
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.JAPANESE));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));

				// 2.2. Preference model on Cost
				CriterionPrefModel<Cost> lydia_cost = new CriterionPrefModel<Cost>();
				lydia_cost.setType(Cost.CHEAP);
				lydia_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));

				// 2.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> lydia_ambiance = new CriterionPrefModel<Ambiance>();
				lydia_ambiance.setType(Ambiance.CALM);
				lydia_ambiance.add(new ValuePreference<Ambiance>(Ambiance.CALM, Ambiance.NOISY));
				/*1. Define the  preferences on Restaurant criteria */	

				OptionPrefModel<Restaurant> lydia_criteria = new OptionPrefModel<Restaurant>(); 
				lydia_criteria.setType(Restaurant.LE_PARISIEN); // Its is not the idial solution but I have to get the type of an option 
				lydia_criteria.add(new CriterionPreference(Cuisine.class,Cost.class));
				lydia_criteria.add(new CriterionPreference(Cost.class,Ambiance.class));

				//		/*2. Define the agent mental state on each criterion (self pref, user pref, proposals */		
				CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
				cost.setSelfPreferences(lydia_cost);

				CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
				cuisine.setSelfPreferences(lydia_cuisine);

				CriterionNegotiation<Ambiance> ambiance = new CriterionNegotiation<Ambiance>(Ambiance.class);
				ambiance.setSelfPreferences(lydia_ambiance);

				//
				//		/*3. Create a nogotiation on restaurant */
				restaurantNegotiation = new Negotiation<Restaurant>
				(new CriterionNegotiation[] {cost, cuisine, ambiance}, lydia_criteria);
				return (restaurantNegotiation);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Negotiation<Restaurant> restaurant = InitiateMentalState.Initialise(); 
		
	}

}
