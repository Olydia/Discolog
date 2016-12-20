package fr.limsi.PreferenceModel.restaurant;

import java.util.*;
import fr.limsi.preferenceModel.*;

public class GenerateModel {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OptionNegotiation<Restaurant>  model1(){
		OptionNegotiation<Restaurant> model1; 
		
		Self<Restaurant> d1_criteria = new Self<Restaurant>(Restaurant.class);
		d1_criteria.addPreference(new Preference(Atmosphere.class, Cost.class));
		d1_criteria.addPreference(new Preference(Cost.class, Cuisine.class));
		
		Self<Cuisine> d1_cuisine = new Self <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.FRENCH);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);
		
		Self<Atmosphere> d1_atmosphere = new Self<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.QUIET);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.FAMILY);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);

	
		Self<Cost> d1_cost = new Self<Cost>(Cost.class);
		d1_cost.addPreference(Cost.EXPENSIVE, Cost.AFFRODABLE);
		d1_cost.addPreference(Cost.AFFRODABLE, Cost.CHEAP);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);
		@SuppressWarnings("serial")
		ArrayList<CriterionNegotiation<? extends Criterion>> values = new ArrayList<CriterionNegotiation<? extends Criterion>> (){{
			add(cuisine); add(cost); add(atmospher);
		}};

		return null;

	}
	public static void main (String[] args)  {
		
	}

}
