package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.restaurant.Cuisine;

/** Stores the preferences about values for a given criterion C, i.e. a list of Preferences<C>, i.e. a list of couples (less,more)
 * with less and more values of an enum C that implements Criterion.
 * 
 * This class is used as follows:
 * 
 * PreferenceModel<Cuisine> lydia_cuisine = new PreferenceModel<Cuisine>();
 * lydia_cuisine.add(new Preference<Cuisine>(Cuisine.CHINESE, Cuisine.FRENCH));
 * 
 * The object lydia_cuisine will be used as parameter to CriterionNegotiation to define the preferences of interlocutors.
 */

public class PreferenceModel<C extends Criterion> {

	private final Set<Preference<C>> preferences = new HashSet<Preference<C>>();

	public void add (Preference<C> preference) {
		preferences.add(preference);
	}

	public PreferenceMatrix<C> generateMatrix (List<? extends Criterion> values){
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> matrix = new PreferenceMatrix (values);
		for (Preference<C> preference : preferences) {
			matrix.addPreference(preference.getLess(), preference.getMore());
		}
		return matrix;
	}
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference.
	 */
	public Boolean isPreferred (C less, C more) {
		// TODO implement DFS to look for path from
		// less to more or from more to less
		//Boolean trouve = false;
		List<Preference<C>> VisitedPreferences = new ArrayList<Preference<C>>();
		for (Preference<C> preference : preferences) {
			if(!VisitedPreferences.contains(preference)/* && trouve == false*/) {
				VisitedPreferences.add(preference);
				if(preference.getLess().equals(less)){
					if(preference.getMore().equals(more)){
						//trouve = true;
						return true;
					}

					Boolean found = isPreferred(preference.getMore(), more);
					if (found) return true;
				}
			}
		}
		return false;
	}
}
