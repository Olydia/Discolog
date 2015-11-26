package restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurant.criteria.Ambiance;
import restaurant.criteria.Cuisine;
import restaurant.criteria.Location;
import restaurant.criteria.Preference;
import restaurant.criteria.Price;
import restaurant.criteria.RestaurantCriterion;
import restaurant.criteria.RestaurantPreferences;
import model.Criterion;
import model.Negotiation;
/*This class contains all the information relative to the agent mental state : Its own preferences (partially filled) 
 * User preferences initially empty
 * and a knowledge base on the restaurants on paris*/
public class MentalState {
	/* List of restaurants
	 *  Restaurant base
	 **/
	Negotiation nogotiation;
	ArrayList<Restaurant> restaurants;
	RestaurantPreferences AgentPreferences;
	RestaurantPreferences UserPreferences ;
	
	
	public MentalState (List<Criterion> cr){
		AgentPreferences = new RestaurantPreferences(cr);
		UserPreferences = new RestaurantPreferences(cr);
		this.restaurants = new ArrayList<Restaurant>();
	}
	
	
	public void createAgentPreferences() {
		/* Rentrer les preferences de l'agent*/
	//	AgentPrefences.get("Cuisine").addPreference(Cuisine.CHINOIS, Cuisine.AVEYRONNAIS);
	}
	
	public void updatePreference(Criterion mostPref, Criterion lessPref){
		// Add the verification that mostPref and lessPref have the same type
		String criterionName =  mostPref.getClass().getSimpleName();
		}
	
	public void addrestaurant(Restaurant restaurant){
		restaurants.add(restaurant);
	}
	
	public void createRestaurants(){
		addrestaurant(new Restaurant("Josselin",Cuisine.BRETON,Ambiance.CALM,Location.MONTPARNASSE,Price.PASCHER));
		addrestaurant(new Restaurant("CheeseFactory",Cuisine.AVEYRONNAIS,Ambiance.NOISY,Location.DOUXIEME,Price.PASCHER));
		addrestaurant(new Restaurant("Ginza",Cuisine.JAPONAIS,Ambiance.CALM,Location.DOUXIEME,Price.CHER));
	}
//	public int decision(Restaurant restaurant) {
//		//get my list of criteria 
//		// foreach criteria 
//			//get value 
//		}
//	}
	public static final void main(String [] args) {
		
	}
}
