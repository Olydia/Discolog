package fr.limsi.negotiate.distance;

import java.util.*;
import fr.limsi.negotiate.Preference;

/**
 * 
 * @author ouldouali
 *
 * @param <T> type of the criterion
 * This class calculate an Adjacency matrix for a set of preferences (selfPreferences) 
 */
public class PreferenceMatrix<T> {
	private List<T> values; 
	private int [] [] preferences; 
	private ArrayList<Preference<T>> selfPreferences;

	public ArrayList<Preference<T>> getSelfPreferences() {
		return selfPreferences;
	}

	public void setSelfPreferences(ArrayList<Preference<T>> selfPreferences) {
		this.selfPreferences = selfPreferences;
	}

	public PreferenceMatrix(List<T> values, ArrayList<Preference<T>> selfPreferences) {
		this.setValues(values);
		this.preferences = new int [values.size()][values.size()];
		this.selfPreferences = selfPreferences;
	}

	/**
	 * 
	 * @param indexLess
	 * @param indexMore
	 * @return true : if the insertion doesn't generate a cycle.
	 */
	public boolean insertPreference(int indexLess, int indexMore) {

		if(preferences[indexLess][indexMore] == 1 || preferences[indexMore][indexLess] == -1){
			return false;
		}

		// add an exception in case where the index = -1
		preferences[indexMore][indexLess]= 1 ;
		preferences[indexLess][indexMore]= -1;
		return true;
	}
	
	
	public boolean addPreference(T less, T more) {
		int [] [] pref = this.preferences.clone();
		int indexMore = getValues().indexOf(more);
		int indexLess = getValues().indexOf(less);
		boolean insert = insertPreference(indexLess, indexMore);
		if(insert && transitivity(indexLess, indexMore)) 
			return true;
		else{
			// undo add 
			this.preferences = pref;
			return false;

		}

	}

	/**
	 * 
	 * construct the adjancy matrix form a list of binary preferences
	 */
	public boolean builtPreferences(){
		boolean insertionSuccess = true;
		for(Preference<T> elem : this.selfPreferences){
			insertionSuccess=addPreference(elem.getLess(), elem.getMore());
			if(insertionSuccess == false)
				return insertionSuccess;
		}
		return true;
	}

	
	public boolean transitivity (int indexLess , int  indexMore){
		boolean more = false, less = false;
		for(int i=0; i< preferences.length; i++){
			if(preferences[indexLess][i] == 1 && i != indexMore){
				more = insertPreference(i, indexMore);
				if(more == false){
					System.out.println("erreur");
					return false;

				}
			}
			if(preferences[indexMore][i] == -1 && i != indexLess){
				less = insertPreference(indexLess, i);
				if(less == false){
					System.out.println("erreur");

					return false;

				}
			}
		}
		return true;
	}

	/**
	 * computes the pair of values that are not related by a preference 
	 * there is not path from u to v (not Preference(u,v) or Preference(v,u))
	 * @return list of pairs (u,v).
	 */
	public List<Preference<T>> nonRelatedCriteria(){
		List<Preference<T>> elems = new ArrayList<Preference<T>>();
		for(int i=0; i< preferences.length; i++){
			for(int j=i+1; j< preferences.length; j++){
				if(preferences[i][j] == 0)
					elems.add(new Preference<T> (getValues().get(i), getValues().get(j)));
			}
		}
		return elems;

	}
	
	public int[][] getPreferences(){
		return this.preferences;
	}


	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}
}
