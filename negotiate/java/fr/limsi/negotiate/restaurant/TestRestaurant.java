package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.*;



public class TestRestaurant {

	public static Negotiation<Restaurant> user(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.JAPANESE));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
	
		// 1.2. Preference model on Cost
		CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
		user_cost.setType(Cost.class);
		user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));

		// 1.3 Preference model on Ambiance 
		CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
		user_ambiance.setType(Ambiance.class);
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.CALM, Ambiance.NOISY));
		/*1. Define the  preferences on Restaurant criteria */	

		CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
		user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
		user_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
		user_criteria.add(new CriterionPreference(Ambiance.class,Cost.class));

		//System.out.println(lydia_criteria.getMostPreferred() + "  " + lydia_criteria.getLeastPreferred());
		//		/*2. Define the agent mental state on each criterion (self pref, user pref, proposals */		
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
		cost.setSelfPreferences(user_cost);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
		cuisine.setSelfPreferences(user_cuisine);

		CriterionNegotiation<Ambiance> ambiance = new CriterionNegotiation<Ambiance>(Ambiance.class);
		ambiance.setSelfPreferences(user_ambiance);
		
				/*3. Create a nogotiation on restaurant */
		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> user_restaurants = new Negotiation<Restaurant>
		(new CriterionNegotiation[] {cost, cuisine, ambiance}, user_criteria);
		
		return (user_restaurants);
	}
	
	public static void main(String[] args) {
		// 1. Define lydia preference model on each criterion of restaurant
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
		/*1. Define the  preferences on Restaurant criteria */	

		CriteriaClassPrefModel<Restaurant> lydia_criteria = new CriteriaClassPrefModel<Restaurant>(); 
		lydia_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
		lydia_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
		lydia_criteria.add(new CriterionPreference(Ambiance.class,Cost.class));

		//System.out.println(lydia_criteria.getMostPreferred() + "  " + lydia_criteria.getLeastPreferred());
		//		/*2. Define the agent mental state on each criterion (self pref, user pref, proposals */		
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
		cost.setSelfPreferences(lydia_cost);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
		cuisine.setSelfPreferences(lydia_cuisine);

		CriterionNegotiation<Ambiance> ambiance = new CriterionNegotiation<Ambiance>(Ambiance.class);
		ambiance.setSelfPreferences(lydia_ambiance);
		
				/*3. Create a nogotiation on restaurant */
		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> restaurants = new Negotiation<Restaurant>
		(new CriterionNegotiation[] {cost, cuisine, ambiance}, lydia_criteria);
		
		
		
		restaurants.context.getListStatements().add(new Statement(new ValuePreference<Criterion>
		(Cost.CHEAP, null), true, "State"));
		
		restaurants.context.getListStatements().add(new Statement(new ValuePreference<Criterion>
		(null,Cost.CHEAP), false, "State"));
		
		restaurants.context.getListStatements().add(new Statement(new ValuePreference<Criterion>
		(Cost.CHEAP, null), true, "State"));
		Negotiation<Restaurant> user =  user();
		DistanceNegotiation<Restaurant> euclide = new DistanceNegotiation<Restaurant>(restaurants, user);
		System.out.println(euclide.finalDistance());
	}
}
