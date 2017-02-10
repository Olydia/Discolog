package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

abstract public class PreferenceModel<C>{
	/** Generate a preference matrix in order to calculate preferences rate */ 
	public PreferenceMatrix<C> generateMatrix (List<C> values, ArrayList<? extends Preference<C>> preferences){
		PreferenceMatrix<C> matrix = new PreferenceMatrix<C> (values);
		for (Preference<C> preference : preferences) {
			matrix.addPreference(preference.getLess(), preference.getMore());
		}
		return matrix;
	}

	abstract public ArrayList<Integer> getPreferencesValues () ;
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference. Can be used to respond to an ask.Preference(Less, More)
	 */
	abstract public Boolean isPreferred (C less, C more);

	abstract public List<C>getValues();

	public C getMostPreferred() {
		return sortCriteria().get(0);

	}

	public C getLeastPreferred()  {	
		List<C> crit = sortCriteria();
		return crit.get(crit.size()-1);
	}

	public boolean isAcceptable(C c, int dom) {
		List<C> sortedCriteria = sortCriteria();
		if (dom > 0)
			return (sortedCriteria.indexOf(c)< sortedCriteria.size()/4);

		else
			return( sortedCriteria.indexOf(c)< sortedCriteria.size()/2);		
	}

	@SuppressWarnings("rawtypes")
	abstract ArrayList  getPreferences();

	abstract List<C> sortCriteria();

	abstract String printPreferences();

}