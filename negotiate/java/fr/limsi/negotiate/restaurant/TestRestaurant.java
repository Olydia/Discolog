package fr.limsi.negotiate.restaurant;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.OptionNegotiation;
import fr.limsi.negotiate.OptionPreference;
import fr.limsi.negotiate.OptionPreferenceModel;
import fr.limsi.negotiate.Preference;
import fr.limsi.negotiate.CriterionPreferenceModel;
import fr.limsi.negotiate.PreferenceMatrix;

public class TestRestaurant {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*1. Define the  preferences on option criteria and model */	
		Restaurant chez_chuck = Restaurant.CHEZ_CHUCK;
		Restaurant parisien = Restaurant.LE_PARISIEN;
		OptionPreferenceModel lydia_criteria = new OptionPreferenceModel(); 
		OptionPreference p = new OptionPreference(Cuisine.class,Cost.class);
		lydia_criteria.add(p);
		OptionNegotiation lydia_optionNegotiation = new OptionNegotiation();
		lydia_optionNegotiation.setSelfPreferences(lydia_criteria);
		/*2. Define the criteria preferences and model */		
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
		// 2.1. Preference model on cuisine
		CriterionPreferenceModel<Cuisine> lydia_cuisine = new CriterionPreferenceModel<Cuisine>();
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.JAPANESE));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
	
		// 2.2. Preference model on Cost
		CriterionPreferenceModel<Cost> lydia_cost = new CriterionPreferenceModel<Cost>();
		lydia_cost.add(new Preference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		// 2.3. Add the agent(lydia) preference models  to their criterionNegotiation.
		cost.setSelfPreferences(lydia_cost);
		cuisine.setSelfPreferences(lydia_cuisine);	
		
		/*3. Create a nogotiation on restaurant with the criterionNegotiation and the OptionNegotiation*/
		Negotiation<Restaurant> restaurants = new Negotiation<Restaurant>
							(new CriterionNegotiation[] {cost, cuisine}, lydia_optionNegotiation);
	
		// Test the DFS preference method (it should return true 
		System.out.println(lydia_cuisine.isPreferred(Cuisine.CHINESE, Cuisine.TURKISH));
		// Test of the decision function and it returns CHEZ_CHUCK because cost is cheap and cost is more preferred to cuisine
		Restaurant res = restaurants.getPreferredOption(Restaurant.CHEZ_CHUCK, Restaurant.LE_PARISIEN);
		System.out.println(res.name());
		PreferenceMatrix<Cost> c = cost.self.generateMatrix(Arrays.asList(Cost.values()));
		ArrayList<Integer> test = c.getPreferenceOrderOfCriteria();
		System.out.println(test.toString());
		int minIndex = test.indexOf(Collections.min(test));
	System.out.println(minIndex);
	}
}

/* TODO-list
 * mettre des commentaires dans le code de Chuck
 * ajouter la liste des prefs sur les criteres dans Negotiation
 * tester getPreferred sans proposal
 * ajouter des proposals
 * tester getPreferred avec proposals
 */
