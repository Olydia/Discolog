package restaurant.criteria;

import java.util.*;

import model.*;

public class RestaurantPreferences {
	List<Preference> restaurantPreference; 
//	Preference CuisinePreference;
//	Preference PricePreference;
//	Preference AmbiancePreference;
//	Preference LocationPreference;
	
	public RestaurantPreferences(List<Criterion> cr) {
		for(Criterion c : cr){
			restaurantPreference.add(new Preference(c.getClass().getSimpleName(), c.values));
		}
//		CuisinePreference = new Preference("Cuisine", Cuisine.values) ;
//		PricePreference = new Preference("Price", Price.values) ;
//		AmbiancePreference = new Preference("Ambiance", Ambiance.values) ;
//		LocationPreference = new Preference("Location", Location.values) ;
	}
	
	public int getIndexPreference(Criterion cr){
		String name = cr.getClass().getSimpleName();
		for (Preference p : restaurantPreference){
			if (p.getCriterionName().equals(name))
				return restaurantPreference.indexOf(p);
		}
		return -1;
	}
	
}
