package fr.limsi.negotiate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/** Stores the preferences about criteria, i.e. a list of OptionPreferences, i.e. a list of couples (less,more) with
 * less and more extends Criteria.
 */

public class CriteriaClassPrefModel<O extends Option> extends PreferenceModel<Class<? extends Criterion>>{
	public  Class <O> type; 
	private final ArrayList<CriterionPreference> preferences = new ArrayList<CriterionPreference>();

	public void add (CriterionPreference preference) {
		preferences.add(preference);
	}

	public  void setType(Class <O> option) {
		 type = option;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends Criterion>> getValues(){
		try {
			Method m = type.getDeclaredMethod("getCriteria");
			Object[] v = type.getEnumConstants();
			m.setAccessible(true);
			List<Class<? extends Criterion>> value = (List<Class<? extends Criterion>>)m.invoke(v[0]);
			return (value);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference.
	 */
	@Override
	public Boolean isPreferred (Class<? extends Criterion> less, Class<? extends Criterion> more) {
	
		return( getRank(less) < getRank(more)?  true :  false);	}


	public int getRank(Class<? extends Criterion> criterion){
		int index = getValues().indexOf(criterion);
		PreferenceMatrix<Class<? extends Criterion>> M = this.generateMatrix(getValues(), preferences);
		 		
		return (M.getRankedPreferences().get(index));
	}

	@Override
	public ArrayList<Integer> getPreferencesValues() {
		 ArrayList<Integer> ranks = new ArrayList<Integer>();
		 for(Class<? extends Criterion> c : getValues())
			 ranks.add(getRank(c));
		 return ranks;
	}

	@Override
	ArrayList<CriterionPreference> getPreferences() {
		// TODO Auto-generated method stub
		return preferences;
	}

	@Override
	public Class<? extends Criterion> getMostPreferred() {
		for(int i : getPreferencesValues())
			if(i == 0)
				return (getValues().get(i));
		return null;
	}

	@Override
	public Class<? extends Criterion> getLeastPreferred() {
		for(int i : getPreferencesValues())
			if(i == (getPreferencesValues().size()-1))
				return (getValues().get(i));
		return null;		
	}

}
