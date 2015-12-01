package fr.limsi.negotiate;

import java.util.*;

/** Stores the preferences about criteria, i.e. a list of OptionPreferences, i.e. a list of couples (less,more) with
 * less and more extends Criteria.
 */

public class OptionPreferenceModel {

	private final Set<OptionPreference> preferences = new HashSet<OptionPreference>();

	public void add (OptionPreference preference) {
		preferences.add(preference);
	}

	public PreferenceMatrix<Class<? extends Criterion>> generateMatrix (List <Class<? extends Criterion>> values){

		PreferenceMatrix<Class<? extends Criterion>> matrix = new PreferenceMatrix <Class<? extends Criterion>>(values);
		for (OptionPreference preference : preferences) {
			matrix.addPreference(preference.getLess(), preference.getMore());
		}
		return matrix;
	}
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference.
	 */
	public Boolean isPreferred (Class<? extends Criterion> less, Class<? extends Criterion> more) {
		// TODO implement DFS to look for path from
		// less to more or from more to less
		List<OptionPreference> VisitedPreferences = new ArrayList<OptionPreference>();
		for (OptionPreference preference : preferences) {
			if(!VisitedPreferences.contains(preference)) {
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
