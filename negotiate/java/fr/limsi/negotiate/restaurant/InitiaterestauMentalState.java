package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionPrefModel;
import fr.limsi.negotiate.CriterionPreference;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.CriteriaClassPrefModel;
import fr.limsi.negotiate.ValuePreference;

public class InitiaterestauMentalState {

	private  static Negotiation<Restaurant> restaurantNegotiation;

	@SuppressWarnings("unchecked")
	public static  Negotiation<Restaurant> Initialise () {
		// 1. Define lydia preference model on each criterion of restaurant
				// 1.1. Preference model on cuisine
				CriterionPrefModel<Cuisine> lydia_cuisine = new CriterionPrefModel<Cuisine>();
				lydia_cuisine.setType(Cuisine.class);
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.CHINESE));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.CHINESE));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.JAPANESE));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
				lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.JAPANESE));

				// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> lydia_cost = new CriterionPrefModel<Cost>();
				lydia_cost.setType(Cost.class);
				lydia_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> lydia_ambiance = new CriterionPrefModel<Ambiance>();
				lydia_ambiance.setType(Ambiance.class);
				lydia_ambiance.add(new ValuePreference<Ambiance>(Ambiance.NOISY, Ambiance.CALM));
				
				//1.4. Define the  preferences on Restaurant criteria 
				CriteriaClassPrefModel<Restaurant> lydia_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				lydia_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				lydia_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
				lydia_criteria.add(new CriterionPreference(Ambiance.class, Cost.class));

				///2. Define the agent mental state on each criterion (self pref, user pref, proposals) 		
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
		Negotiation<Restaurant> restaurant = InitiaterestauMentalState.Initialise(); 
//		System.out.println(restaurant.getCriterionNegotiation(Cuisine.class).getSelf().getMostPreferred());
		restaurant.printAllMentalState();
//		System.out.println();
	}

}
