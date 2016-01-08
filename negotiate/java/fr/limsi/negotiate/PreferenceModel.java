package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

abstract public class PreferenceModel<C>{
	Preference<C> lastUpdate;
	/** Generate a preference matrix in order to calculate preferences rate */ 
	public PreferenceMatrix<C> generateMatrix (List<C> values, ArrayList<? extends Preference<C>> preferences){
		PreferenceMatrix<C> matrix = new PreferenceMatrix<C> (values);
		for (Preference<C> preference : preferences) {
			matrix.addPreference(preference.getMore(), preference.getLess());
		}
		return matrix;
	}
	
	abstract public ArrayList<Integer> getPreferences () ;
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference. Can be used to respond to an ask.Preference(Less, More)
	 */
	abstract public Boolean isPreferred (C more, C less);
	 // A definir ici 
	abstract public C getMostPreffered();
	abstract public C getLeastPreffered();
	 public void updateLastPreference(Preference<C> preference) {
		lastUpdate = preference;
	}
	

}