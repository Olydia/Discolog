package fr.limsi.negotiate.restaurant;

import javax.imageio.metadata.IIOInvalidTreeException;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.movie.Category;
import fr.limsi.negotiate.movie.InitiateMovieMentalState;
import fr.limsi.negotiate.movie.Movie;



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
		user_ambiance.add(new ValuePreference<Ambiance>(Ambiance.QUIET, Ambiance.LIVELY));
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


	public static void printScores(Negotiation<Restaurant> model){

		for(CriterionNegotiation<Criterion> cn : model.criteriaNegotiation){
			System.out.println("**************	"+ cn.criterionType.getSimpleName() + "  **************" );
			for(Criterion pm : cn.getSelf().getValues())
				System.out.println(pm + " : " + cn.getSelf().getScore(pm));	
		}
		System.out.println(" ===============	Options		======================= ");
		for(Option o : Restaurant.values()){
			System.out.println(o.toString() + " : "+ model.optionUtility(o));
		}
	}

	public static void main(String[] args) {
		InitiaterestauMentalState mv = new InitiaterestauMentalState();
		Negotiation<Restaurant> nmv= mv.D1();
		Negotiation<Restaurant> nmv1= mv.V2();
//		Cuisine [] cuisine = {Cuisine.MIXICAN, Cuisine.COREAN};
//		Cost[] cost = Cost.values();//{Cost.AFFRODABLE};
//		Ambiance [] ambiance = Ambiance.values();//{Ambiance.ROMANTIC, Ambiance.FAMILIAL};
//		int cp = 0;
//		for(Cuisine i: cuisine){
//			for(Cost j: cost){
//				for(Ambiance x: ambiance){
//					System.out.println("n"+cp+ "(Cuisine."+i.toString().toUpperCase()+", Cost." 
//							+j.toString().toUpperCase()+", Ambiance." +
//							x.toString().toUpperCase()+"), \n");
//					cp ++;
//				}
//			}
//		}



		

		CriterionPrefModel<Criterion> model =nmv.getCriterionNegotiation(Cuisine.class).getSelf();

		System.out.println(nmv.getCriterionNegotiation(Cuisine.class).acceptableCriteria(2, model));
		System.out.println(nmv.getCriterionNegotiation(Cuisine.class).acceptableCriteria(-2, model));

	}
}
