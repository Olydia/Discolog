package fr.limsi.negotiate;

import java.util.*;


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

public class CriterionPrefModel<C extends Criterion> extends PreferenceModel<C> {
	C type; 
	// paired preferences on C values
	private final ArrayList<ValuePreference<C>> preferences = new ArrayList<ValuePreference<C>>();
 	
	
	public void add (ValuePreference<C> preference) {
		preferences.add(preference);
	}

 
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference. Can be used to respond to an ask.Preference(Less, More)
	 */
	public Boolean isPreferred (C more, C less) {
		return( getScore(less) < getScore(more)?  true :  false);
	}
	
	
	public int getScore(C value){
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix((List<C>) Arrays.asList(value.getValues()), preferences);
		return (M.getPreferenceOnValue(value));
	}

	
	@Override
	public C getMostPreffered() {
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix((List<C>) Arrays.asList(type.getValues()), preferences);
		return (M.getMostPreffered());
	}


	@Override
	public C getLeastPreffered() {
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix((List<C>) Arrays.asList(type.getValues()), preferences);
		return (M.getLeastPreffered());
	}


	@Override
	public ArrayList<Integer> getPreferences() {
		@SuppressWarnings("unchecked")
		PreferenceMatrix<C> M = this.generateMatrix((List<C>) Arrays.asList(type.getValues()), preferences);
		return (M.getPreferences());
	}


	public C getType() {
		return type;
	}


	public void setType(C type) {
		this.type = type;
	}
}
