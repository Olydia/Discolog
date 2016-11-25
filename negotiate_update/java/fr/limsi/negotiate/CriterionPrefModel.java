package fr.limsi.negotiate;


import java.util.*;

import fr.limsi.negotiate.PreferenceStatement.Acceptable;


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

	private Class <C> type; 
	// paired preferences on C values
	private final ArrayList<ValuePreference<C>> preferences = new ArrayList<ValuePreference<C>>();
	private ArrayList<C> acceptableValues ;
	public ArrayList<C> getAcceptableValues() {
		return acceptableValues;
	}
	public ArrayList<C> getNonAcceptableValues() {
		return nonAcceptableValues;
	}


	private ArrayList<C> nonAcceptableValues;


	public CriterionPrefModel(){
		acceptableValues = new ArrayList<C>();
		nonAcceptableValues= new ArrayList<C>();
	}
	public void add (ValuePreference<C> preference) {
		ValuePreference<C> v = new ValuePreference<C>
		(preference.getMore(), preference.getLess());
		if(preferences.contains(v))
			throw new RuntimeException("Cannot add P ("+v.getLess()+", " + v.getMore()+") "
					+ " because P ("+v.getMore()+", " + v.getLess()+") exists in the preferences list");
		if(!preferences.contains(preference))
			preferences.add(preference);
	}

	public void add (C less, C more){
		add(new ValuePreference<C>(less, more));
	}
	
	public void addValue(C value, Acceptable isLikable){
		if(isLikable.equals(Acceptable.FALSE))
			getNonAcceptableValues().add(value);
		else
			getAcceptableValues().add(value);
	}
	/**
	 * Return Boolean.TRUE if there is a preference about criterion, or Boolean.FALSE otherwise
	 */
	public boolean containsPrefabout(C criterion){

		for(ValuePreference<C> pref: this.preferences){
			if(pref.getLess().equals(criterion) || pref.getMore().equals(criterion))
				return true;
		}
		return false;
	}
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference. Can be used to respond to an ask.Preference(Less, More)
	 */
	@Override
	public Boolean isPreferred (C less, C more) {
		if(less == null)
			return true;
		if(more == null)
			return false;
		return( getScore(less) < getScore(more)?  true :  false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<C> getValues(){

		try {
			//Method m = type.getDeclaredMethod("getValues");
			Object[] consts = type.getEnumConstants();
			//Object[] v = (Object[])m.invoke(consts[0]);
			return ((List<C>) (Arrays.asList(consts)));


		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public int getScore(C value){
		if(!preferences.isEmpty()){
			PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
			return (M.getPreferenceOnValue(value));
		}
		else{
			if(acceptableValues.contains(value))
				return 1;
			if(nonAcceptableValues.contains(value))
				return -1;
			return 0;
		}
	}


	@Override
	public ArrayList<Integer> getPreferencesValues() {
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
		return (M.getPreferences());
	}

	public List<C> sortCriteria() {
		List<C> criteria = this.getValues();
		criteria.sort(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2){
				return (getScore(o2) - getScore(o1));
			}
		});
		return criteria;
	}

	public List<C> sortCriteria(List<C> criteria) {
		criteria.sort(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2){
				return (getScore(o2) - getScore(o1));
			}
		});
		return criteria;
	}


	public Class<C> getType() {
		return type;
	}


	public void setType(Class<C> type) {
		this.type = type;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// sb.append("type " + type.getName());
		for(ValuePreference<C> p : preferences)
			sb.append("|"+p);
		return sb.toString();
	}
	@Override
	public ArrayList<ValuePreference<C>> getPreferences() {
		// TODO Auto-generated method stub
		return this.preferences;
	}

	//	@Override
	//	public C getMostPreferred() {
	//		return sortCriteria().get(0);
	//	}

	@Override
	public C getLeastPreferred() {	
		List<C> crit = sortCriteria();
		return crit.get(crit.size()-1);
	}

	@Override
	public String printPreferences() {
		String pref = type.getSimpleName()+ ": \n" ;// TODO Auto-generated method stub
		for (ValuePreference<C> p: preferences){
			pref = pref + p + "\n";
		}
		return pref;
	}
	public boolean isAcceptable(C c, int dom) {
		if(preferences.isEmpty()){
			return (getScore(c) >= 0);
		}
		else
			return super.isAcceptable(c, dom);
	}


	/** Calculates the acceptable values for the preference model
	 *  which can be either Other or OAS 
	 *  @return List of sorted values that are acceptable**/ 
	public List<C> acceptableCriteria(int dom){
		List<C> accepted = new ArrayList<C>();
			for(C criterion:getValues())
				if(isAcceptable(criterion, dom))
					accepted.add(criterion);

			this.sortCriteria(accepted);
		return accepted;
	}


}
