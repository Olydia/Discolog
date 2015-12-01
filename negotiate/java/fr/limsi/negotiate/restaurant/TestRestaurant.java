package fr.limsi.negotiate.restaurant;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Preference;
import fr.limsi.negotiate.PreferenceModel;

public class TestRestaurant {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
				
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
		Negotiation<Restaurant> restaurants = new Negotiation<Restaurant>(new CriterionNegotiation[] {cost, cuisine});

		PreferenceModel<Cuisine> lydia_cuisine = new PreferenceModel<Cuisine>();
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.JAPANESE));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.JAPANESE, Cuisine.ITALIAN));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.FRENCH, Cuisine.ITALIAN));
		lydia_cuisine.add(new Preference<Cuisine>(Cuisine.JAPANESE, Cuisine.TURKISH));
		
		// ajouter les preferences sur les criteres 
		PreferenceModel<Cost> lydia_cost = new PreferenceModel<Cost>();
		lydia_cost.add(new Preference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		
		cost.setSelfPreferences(lydia_cost);
		cuisine.setSelfPreferences(lydia_cuisine);
//		System.out.println(lydia_cuisine.isPreferred(Cuisine.CHINESE, Cuisine.TURKISH));
//		//System.out.println(restaurants.getPreferredOption()); // TODO
//		System.out.println(Restaurant.CHEZ_CHUCK.getValue(Cuisine.class));
		System.out.println(cuisine.getCriterionType().getSimpleName());
	}
}

/* TODO-list
 * mettre des commentaires dans le code de Chuck
 * ajouter la liste des prefs sur les criteres dans Negotiation
 * tester getPreferred sans proposal
 * ajouter des proposals
 * tester getPreferred avec proposals
 */
