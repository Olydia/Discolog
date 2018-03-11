package fr.limsi.application.FRversion.SaisiePref.dndTestFR;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.restaurant.Atmosphere;
import fr.limsi.negotiate.restaurant.Cost;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.Location;
import fr.limsi.negotiate.restaurant.Restaurant;

public class PreferencesModel {

	public Self_Ci<Cuisine> d1_cuisine;
	public Self_Ci<Atmosphere> d1_atmosphere;
	public Self_Ci<Cost> d1_cost;
	public Self_Ci<Location> d1_location;
	
	public PreferencesModel() {
		d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_location =  new Self_Ci<>(Location.class);

	}
	
	public Negotiation<Restaurant>  createModel(){

		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);

		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);
		
//		d1_location.addPreference(Location.NORTH_SIDE, Location.DOWNTOWN);

		CriterionNegotiation<Location> location = new CriterionNegotiation<>(d1_location);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d1_criteria, Restaurant.class);
		return model1;

	}
	

}
