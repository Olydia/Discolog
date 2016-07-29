package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.*;

public class InitiaterestauMentalState {

	//private  Negotiation<Restaurant> restaurantNegotiation;

	@SuppressWarnings("unchecked")
	public  Negotiation<Restaurant> D1 () {
		Negotiation<Restaurant> restaurant;
		// 1. Define d1 preference model on each criterion of restaurant
				// 1.1. Preference model on cuisine
				CriterionPrefModel<Cuisine> d1_cuisine = new CriterionPrefModel<Cuisine>();
				d1_cuisine.setType(Cuisine.class);
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.CHINESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.CHINESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.JAPANESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.JAPANESE));

				// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> d1_cost = new CriterionPrefModel<Cost>();
				d1_cost.setType(Cost.class);
				d1_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> d1_ambiance = new CriterionPrefModel<Ambiance>();
				d1_ambiance.setType(Ambiance.class);
				d1_ambiance.add(new ValuePreference<Ambiance>(Ambiance.NOISY, Ambiance.CALM));
				
				//1.4. Define the  preferences on Restaurant criteria 
				CriteriaClassPrefModel<Restaurant> d1_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				d1_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				d1_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
				d1_criteria.add(new CriterionPreference(Ambiance.class, Cost.class));

				///2. Define the agent mental state on each criterion (self pref, user pref, proposals) 		
				CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
				cost.setSelfPreferences(d1_cost);

				CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
				cuisine.setSelfPreferences(d1_cuisine);

				CriterionNegotiation<Ambiance> ambiance = new CriterionNegotiation<Ambiance>(Ambiance.class);
				ambiance.setSelfPreferences(d1_ambiance);

				//
				//		/*3. Create a nogotiation on restaurant */
				restaurant= new Negotiation<Restaurant>
				(new CriterionNegotiation[] {cost, cuisine, ambiance}, d1_criteria);
				return (restaurant);
	}
	
	public  Negotiation<Restaurant> D2(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.FRENCH));
	
		// 1.2. Preference model on Cost
		CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
		user_cost.setType(Cost.class);
		user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));

		// 1.3 Preference model on Ambiance 
		CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
		user_ambiance.setType(Ambiance.class);
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.NOISY, Ambiance.CALM));
		/*1. Define the  preferences on Restaurant criteria */	

		CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
		user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
		user_criteria.add(new CriterionPreference(Cuisine.class, Cost.class));
		user_criteria.add(new CriterionPreference(Cost.class,Ambiance.class));

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
	
	public  Negotiation<Restaurant> S2(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.JAPANESE));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.JAPANESE));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.CHINESE));
	
		// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
				user_cost.setType(Cost.class);
				user_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
				user_ambiance.setType(Ambiance.class);
				user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.NOISY, Ambiance.CALM));
				/*1. Define the  preferences on Restaurant criteria */	

				CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				user_criteria.add(new CriterionPreference(Cuisine.class, Cost.class));
				user_criteria.add(new CriterionPreference(Cost.class,Ambiance.class));

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

	
	public  Negotiation<Restaurant> D3(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.JAPANESE));
	
		// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
				user_cost.setType(Cost.class);
				user_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
				user_ambiance.setType(Ambiance.class);
				user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.CALM, Ambiance.NOISY));
				/*1. Define the  preferences on Restaurant criteria */	

				CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				user_criteria.add(new CriterionPreference(Cuisine.class, Cost.class));
				user_criteria.add(new CriterionPreference(Cost.class,Ambiance.class));

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

	public InitiaterestauMentalState() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
