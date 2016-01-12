package fr.limsi.negotiate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	Class <C> type; 
	// paired preferences on C values
	private final ArrayList<ValuePreference<C>> preferences = new ArrayList<ValuePreference<C>>();
 	
	
	public void add (ValuePreference<C> preference) {
		preferences.add(preference);
	}
/* definir une methode qui prend en entrée deux criteres et crée */
	public void add (C more, C less){
		add(new ValuePreference<C>(more, less));
	}
	/**
	 * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
	 * if first argument is more preferred, or null if no preference. Can be used to respond to an ask.Preference(Less, More)
	 */
	public Boolean isPreferred (C more, C less) {
		return( getScore(less) < getScore(more)?  true :  false);
	}
	
	@SuppressWarnings("unchecked")
	public List<C> getValues(){
		
		try {
			Method m = type.getDeclaredMethod("getValues");
			Object[] consts = type.getEnumConstants();
		    Object[] v = (Object[])m.invoke(consts[0]);
			return ((List<C>) (Arrays.asList(v)));

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalAccessException e) {
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
	public int getScore(C value){
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
		return (M.getPreferenceOnValue(value));
	}

	
	public C getMostPreferred() {
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
		return (M.getMostPreffered());
	}


	public C getLeastPreferred() {
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
		return (M.getLeastPreffered());
	}
	

	@Override
	public ArrayList<Integer> getPreferences() {
		PreferenceMatrix<C> M = this.generateMatrix(getValues(), preferences);
		return (M.getPreferences());
	}


	public Class<C> getType() {
		return type;
	}


	public void setType(Class<C> type) {
		this.type = type;
	}
}
