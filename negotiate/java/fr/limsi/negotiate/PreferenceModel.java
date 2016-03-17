package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

abstract public class PreferenceModel<C>{
	Preference<C> lastUpdate;
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
	 // A definir ici 
	
	public void updateLastPreference(Preference<C> preference) {
		lastUpdate = preference;
	}
	
	public C getMostPreferred() {
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), getPreferences());
		return (M.getMostPreffered());
	}


	public C getLeastPreferred() {
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), getPreferences());
		return (M.getLeastPreffered());
	}
	
	@SuppressWarnings("rawtypes")
	abstract ArrayList  getPreferences();

}