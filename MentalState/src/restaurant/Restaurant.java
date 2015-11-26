package restaurant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import model.*;
import restaurant.criteria.*;

public class Restaurant extends Option {

	private Cuisine cuisine;
	private Ambiance ambiance;
	private Location location;
	private Price price;
	


	public Restaurant(String name, Cuisine cuisine, Ambiance ambiance, Location location, Price price) {
		super(name);
		this.cuisine = cuisine;
		this.ambiance = ambiance;
		this.location = location;
		this.price = price;

	}
	//@Override
	public  ArrayList<Criterion> getCriteria() {
		this.criteria = new ArrayList() {{ add(getCuisine().getClass()); add(getAmbiance().getClass()); add(getLocation().getClass()); add(getPrice().getClass());}};
		return criteria;

	}

	public Cuisine getCuisine() {
		return cuisine;
	}


	public Ambiance getAmbiance() {
		return ambiance;
	}

	public void setAmbiance(Ambiance ambiance) {
		this.ambiance = ambiance;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	public Criterion getValue(Criterion cr){
		if(cuisine.getClass().equals(cr))
			return cuisine;
		if(price.getClass().equals(cr))
			return price;
		if(location.getClass().equals(cr))
			return location;
		if(ambiance.getClass().equals(cr))
			return ambiance;
					
		return null;
		
	}

	


}
