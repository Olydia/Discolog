package fr.limsi.negotiate.restaurant;

import java.util.Arrays;
import java.util.List;

import fr.limsi.negotiate.*;

public class InitiaterestauMentalState {

	@SuppressWarnings("unchecked")
	public  Negotiation<Restaurant> D1 () {
		Negotiation<Restaurant> restaurant;
		// 1. Define d1 preference model on each criterion of restaurant
				// 1.1. Preference model on cuisine
				CriterionPrefModel<Cuisine> d1_cuisine = new CriterionPrefModel<Cuisine>();
				d1_cuisine.setType(Cuisine.class);
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.KOREAN, Cuisine.CHINESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.CHINESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.KOREAN));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.JAPANESE));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.MIXICAN, Cuisine.TURKISH));
				d1_cuisine.add(new ValuePreference<Cuisine>(Cuisine.MIXICAN, Cuisine.FRENCH));


				// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> d1_cost = new CriterionPrefModel<Cost>();
				d1_cost.setType(Cost.class);
				d1_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.AFFRODABLE));
				d1_cost.add(new ValuePreference<Cost>(Cost.AFFRODABLE, Cost.CHEAP));


				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> d1_ambiance = new CriterionPrefModel<Ambiance>();
				d1_ambiance.setType(Ambiance.class);
				d1_ambiance.add(new ValuePreference<Ambiance>(Ambiance.LIVELY, Ambiance.QUIET));
				d1_ambiance.add(new ValuePreference<Ambiance>(Ambiance.LIVELY, Ambiance.ROMANTIC));
				d1_ambiance.add(new ValuePreference<Ambiance>(Ambiance.ROMANTIC, Ambiance.FAMILIAL));


				//1.4. Define the  preferences on Restaurant criteria 
				CriteriaClassPrefModel<Restaurant> d1_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				d1_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				d1_criteria.add(new CriterionPreference(Ambiance.class, Cost.class));
				d1_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));

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
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.MIXICAN, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.KOREAN, Cuisine.MIXICAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.KOREAN, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.MIXICAN));

	
		// 1.2. Preference model on Cost
		CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
		user_cost.setType(Cost.class);
		user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.AFFRODABLE));


		// 1.3 Preference model on Ambiance 
		CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
		user_ambiance.setType(Ambiance.class);
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.LIVELY, Ambiance.QUIET));
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.ROMANTIC, Ambiance.QUIET));
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.ROMANTIC, Ambiance.FAMILIAL));
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.FAMILIAL, Ambiance.LIVELY));


		/*1. Define the  preferences on Restaurant criteria */	

		CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
		user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
		user_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
		user_criteria.add(new CriterionPreference(Cuisine.class,Ambiance.class));

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
				user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.LIVELY, Ambiance.QUIET));
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
	//-----------------------------------------------------------------------------------------

	public  Negotiation<Restaurant> D_A(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
		//user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.CHINESE));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		//user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.ITALIAN));



	
		// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
				user_cost.setType(Cost.class);
				user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
				user_ambiance.setType(Ambiance.class);
				user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.QUIET, Ambiance.LIVELY));
				/*1. Define the  preferences on Restaurant criteria */	

				CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				user_criteria.add(new CriterionPreference(Cost.class, Cuisine.class));
				user_criteria.add(new CriterionPreference(Cuisine.class,Ambiance.class));
				

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

//-----------------------------------------------------------------------------------------
	
	public  Negotiation<Restaurant> D4(){
		CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
		user_cuisine.setType(Cuisine.class);
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.TURKISH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.CHINESE));
	
		// 1.2. Preference model on Cost
				CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
				user_cost.setType(Cost.class);
				user_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

				// 1.3 Preference model on Ambiance 
				CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
				user_ambiance.setType(Ambiance.class);
				user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.QUIET, Ambiance.LIVELY));
				/*1. Define the  preferences on Restaurant criteria */	

				CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
				user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
				user_criteria.add(new CriterionPreference(Ambiance.class, Cuisine.class));
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
	//-----------------------------------------------------------------------------------------
	
		public  Negotiation<Restaurant> V2(){
			CriterionPrefModel<Cuisine> user_cuisine = new CriterionPrefModel<Cuisine>();
			user_cuisine.setType(Cuisine.class);
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.FRENCH));
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.CHINESE));
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.CHINESE, Cuisine.ITALIAN));
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.JAPANESE));
			user_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.FRENCH));



		
			// 1.2. Preference model on Cost
					CriterionPrefModel<Cost> user_cost = new CriterionPrefModel<Cost>();
					user_cost.setType(Cost.class);
					user_cost.add(new ValuePreference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));

					// 1.3 Preference model on Ambiance 
					CriterionPrefModel<Ambiance> user_ambiance = new CriterionPrefModel<Ambiance>();
					user_ambiance.setType(Ambiance.class);
					user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.LIVELY, Ambiance.QUIET));
					/*1. Define the  preferences on Restaurant criteria */	

					CriteriaClassPrefModel<Restaurant> user_criteria = new CriteriaClassPrefModel<Restaurant>(); 
					user_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
					user_criteria.add(new CriterionPreference(Ambiance.class, Cost.class));
					user_criteria.add(new CriterionPreference(Cost.class,Cuisine.class));
					

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
	
	public static void printOptions(Negotiation<Restaurant> n, int relation){
		List<Option> options =	(Arrays.asList(n.getOptions()));
		for(Option o: options)
			System.out.println(n.isAcceptableOption(o));
	}
//	public static void main (String[] args) {
//		InitiaterestauMentalState model = new InitiaterestauMentalState();
//		Negotiation<Restaurant> s2 = model.D_A();
//		printOptions(s2, 2);
//		//System.out.println("Cuisine");
//		//Negotiation<Restaurant> d2 = model.D2();
////		for(Cuisine c: Cuisine.values()){
////			System.out.println(c.name() + " :" +
////		s2.getCriterionNegotiation(Cuisine.class).getSelf().getScore(c));
////		}
////		for(Ambiance c: Ambiance.values()){
////			System.out.println(c.name() + " :" +
////		s2.getCriterionNegotiation(Ambiance.class).getSelf().getScore(c));
////		}
////		for(Cost c: Cost.values()){
////			System.out.println(c.name() + " :" +
////		s2.getCriterionNegotiation(Cost.class).getSelf().getScore(c));
////		}
//	}

}
