package fr.limsi.negotiate.distance;

import java.util.*;
import fr.limsi.negotiate.Preference;

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

	// TODO check if preference(less, more) is not already defined in order to avoid inconcisty and cycles
	public boolean insertPreference(int indexLess, int indexMore) {

		if(preferences[indexLess][indexMore] == 1 || preferences[indexMore][indexLess] == -1){
			//System.out.println("Contradiction: P ("+getValues().get(indexLess)+", " + getValues().get(indexMore) +") exists in the preferences list");
			return false;
		}
		//			try {

		//				throw new Exception("Contradiction: P ("+less+", " + more +") exists in the preferences list");
		//			} catch (Exception e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
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
	// Move to the CriteriaPreferences

	public int getPreferenceOnValue(int i) {
		int somme = 0;
		for(int j=0; j < preferences.length; j++){			
			somme+=preferences[i][j];
		}
		return somme;
	}
	public int getPreferenceOnValue(T c) {
		int i = getValues().indexOf(c);
		int somme = 0;
		for(int j=0; j < preferences.length; j++){			
			somme+=preferences[i][j];
		}
		return somme;
	}
	public int[][] getPreferences(){
		return this.preferences;
	}

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

	

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}
}
