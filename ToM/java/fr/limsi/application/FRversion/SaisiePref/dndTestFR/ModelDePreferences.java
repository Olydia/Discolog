package fr.limsi.application.FRversion.SaisiePref.dndTestFR;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.restaurant.FR.*;
public class ModelDePreferences {

	public Self_Ci<Cuisine> d1_cuisine;
	public Self_Ci<Ambiance> d1_atmosphere;
	public Self_Ci<Prix> d1_cost;
	public Self_Ci<Localisation> d1_location;
	
	public ModelDePreferences() {
		d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d1_cost = new Self_Ci<Prix>(Prix.class);
		d1_location =  new Self_Ci<Localisation>(Localisation.class);

	}
	
	public Negotiation<Restaurant>  createModel(){

		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);

		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);
		
//		d1_location.addPreference(Location.NORTH_SIDE, Location.DOWNTOWN);

		CriterionNegotiation<Localisation> location = new CriterionNegotiation<>(d1_location);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher, location}, d1_criteria, Restaurant.class);
		return model1;

	}
	

}
