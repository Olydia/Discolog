package fr.limsi.negotiate.restaurant;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Preference;
import fr.limsi.negotiate.PreferenceModel;

public class TestRestaurant {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		System.out.println(Restaurant.LE_PARISIEN.getValue(Cuisine.class));
		
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>();
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>();
		Negotiation<Restaurant> restaurants = new Negotiation<Restaurant>(new CriterionNegotiation[] {cost, cuisine});

		PreferenceModel<Cuisine> lydia_cuisine = new PreferenceModel<Cuisine>();
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		// ajouter les preferences sur les criteres 
		PreferenceModel<Cost> lydia_cost = new PreferenceModel<Cost>();
		lydia_cost.add(new Preference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		
		cost.setSelfPreferences(lydia_cost);
		cuisine.setSelfPreferences(lydia_cuisine);
		
		System.out.println(restaurants.getPreferredOption()); // TODO
	}
}

/* TODO-list
 * mettre des commentaires dans le code de Chuck
 * ajouter la liste des prefs sur les critères dans Negotiation
 * tester getPreferred sans proposal
 * ajouter des proposals
 * tester getPreferred avec proposals
 */
