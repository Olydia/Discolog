package restaurant.criteria;

import java.util.Arrays;
import java.util.List;

import model.*;

public enum RestaurantCriterion implements CriterionClass {
	
	CUISINE(Cuisine.class),
	AMBIANCE(Ambiance.class),
	LOCATION(Location.class),
	PRICE(Price.class);
	//public final static List<RestaurantCriterion> criteria = Arrays.asList(RestaurantCriterion.class.getEnumConstants());
	
	private Class values;
	
	private RestaurantCriterion(Class c) {
		values = c;
	}
	
	public Criterion[] getValues() {
		return (Criterion[]) values.getEnumConstants();
	}
	
}
