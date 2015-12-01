package fr.limsi.negotiate;

import java.util.*;

public class PreferenceModel<C extends Criterion> {

	private final Set<Preference<C>> preferences = new HashSet<Preference<C>>();

	public void add (Preference<C> preference) {
		preferences.add(preference);
	}

	public PreferenceMatrix generateMatrix (List<? extends Criterion> values){
		PreferenceMatrix matrix = new PreferenceMatrix (values);
		for (Preference<C> preference : preferences) {
			matrix.addPreference(preference.less, preference.more);
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
