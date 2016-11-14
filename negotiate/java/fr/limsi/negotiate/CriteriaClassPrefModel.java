package fr.limsi.negotiate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import fr.limsi.negotiate.restaurant.Ambiance;
import fr.limsi.negotiate.restaurant.Cost;
import fr.limsi.negotiate.restaurant.Cuisine;

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
		return sortCriteria().get(0);
	}

//	@Override
//	public Class<? extends Criterion> getLeastPreferred() {
//		for(int i : getPreferencesValues())
//			if(i == (getPreferencesValues().size()-1))
//				return (getValues().get(i));
//		return null;		
//	}
	@Override
	public ArrayList<Class<? extends Criterion> > sortCriteria() {
		ArrayList<Class<? extends Criterion> > criteria = (ArrayList<Class<? extends Criterion>>) this.getValues();
		criteria.sort(new Comparator<Class<? extends Criterion> >() {
			@Override
			public int compare(Class<? extends Criterion>  o1, Class<? extends Criterion>  o2){
				return (getRank(o2) - getRank(o1));
			}
		});
		return criteria;
	}

	@Override
	public String printPreferences() {
		String pref = type.getSimpleName()+ " :\n ";
		for(CriterionPreference cr : preferences)
			pref = pref + cr.toString()+ "\n";
		return pref;
	}
	
	/** IsImportantCriterion (criterion, dom) calculates in function of the relation of dominance whether a criterion 
	 * is important to choose an option
	 * The calcul depends on the rank of the criterion
	 * nbTurns in function of the number of turns spoken in the negotiation
	 * 
	 */
	public boolean IsImportantCriterion (Class<? extends Criterion> criterion, int dom, int spokenTurns){
		List <Class<? extends Criterion>>criteria = sortCriteria();
		if (dom > 0 || spokenTurns < 2)
			return true;
		else
			return (criteria.indexOf(criterion)<= (criteria.size())-(spokenTurns/2));
		
			
	}
	
	public List<Class<? extends Criterion>> importantCriteria(int dom, int spokenTurns){
		ArrayList<Class<? extends Criterion>> importantC= new ArrayList<Class<? extends Criterion>>();
		@SuppressWarnings("unchecked")
		ArrayList<Class<? extends Criterion>> criteria = (ArrayList<Class<? extends Criterion>>) sortCriteria().clone();
        Iterator<Class<? extends Criterion>> it = criteria.iterator();
		while(it.hasNext()){
			Class<? extends Criterion> elm = it.next();
			if(this.IsImportantCriterion(elm, dom, spokenTurns))
				importantC.add(elm);
		}
		return importantC;
	}

}
