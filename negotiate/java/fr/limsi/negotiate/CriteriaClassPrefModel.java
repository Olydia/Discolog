package fr.limsi.negotiate;

import java.util.*;

/** Stores the preferences about criteria, i.e. a list of OptionPreferences, i.e. a list of couples (less,more) with
 * less and more extends Criteria.
 */

public class CriteriaClassPrefModel<O extends Option> extends PreferenceModel<Class<? extends Criterion>>{
	public  O type; 
	private final ArrayList<CriterionPreference> preferences = new ArrayList<CriterionPreference>();

	public void add (CriterionPreference preference) {
		preferences.add(preference);
	}

	public  void setType(O option) {
		 type = option;
	}

	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference.
	 */
	public Boolean isPreferred (Class<? extends Criterion> more, Class<? extends Criterion> less) {
	
		return( getRank(less) < getRank(more)?  true :  false);	}


	public int getRank(Class<? extends Criterion> criterion){
		int index = type.getCriteria().indexOf(criterion);
		PreferenceMatrix<Class<? extends Criterion>> M = this.generateMatrix(type.getCriteria(), preferences);
		 		
		return (M.getRankedPreferences().get(index));
	}

	@Override
	public Class<? extends Criterion> getMostPreffered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Criterion> getLeastPreffered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getPreferences() {
		// TODO Auto-generated method stub
		PreferenceMatrix<Class<? extends Criterion>> M = this.generateMatrix(type.getCriteria(), preferences);
		return (M.getPreferences());
	}
}
