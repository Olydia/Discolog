package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PreferenceMatrix<T> {
	List<T> values; 
	private int [] [] preferences; 
	
	public PreferenceMatrix(List<T> values) {
		// TODO Auto-generated constructor stub
		this.values = values;
		preferences = new int [values.size()][values.size()];
	}


	public void addPreference(T mostPreferred, T lessPreferred) {
		
		int i = values.indexOf(mostPreferred);
		int j = values.indexOf(lessPreferred);
		// add an exception in case where the index = -1
		preferences[i][j]= 1 ;
		preferences[j][i]= -1;
		transitivity(i, j);
		
	}

	public void transitivity (int indexMostPref, int indexLessPref){

		for(int i=0; i< preferences.length; i++){
			if(preferences[indexLessPref][i] == 1 && i != indexMostPref){
				preferences[indexMostPref][i] =1;
				preferences[i][indexMostPref] = -1;
			}
			if(preferences[indexMostPref][i] == -1 && i != indexLessPref){
				preferences[indexLessPref][i] = -1;
				preferences[i][indexLessPref] = 1;
			}
		}
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
		int i = values.indexOf(c);
		int somme = 0;
		for(int j=0; j < preferences.length; j++){			
			somme+=preferences[i][j];
		}
		return somme;
	}
	public ArrayList<Integer> getPreferences(){
		ArrayList<Integer> prefValues = new ArrayList<Integer>();
		for(int i=0; i < preferences.length; i++){
			prefValues.add(getPreferenceOnValue(values.get(i)));
		}
		return prefValues;
		
	}
	public ArrayList<Integer> getRankedPreferences(){
		ArrayList<Integer> prefValues = getPreferences();
		int min = Collections.min(prefValues);
		for(int v : prefValues)
			v= v+ Math.abs(min);
		return prefValues;

	}
	
	private static final int maxIndex(ArrayList<Integer> a) {
		int imax=0;
		for(int i=1;i<a.size();i++)
			if (a.get(i)>a.get(imax))
				imax = i;
		return imax;
	}
	
	public T getMostPreffered() {
		ArrayList<Integer> prefValues = getPreferences();
		int  indexmax = maxIndex(prefValues);
		return (values.get(indexmax));
	}


	public T getLeastPreffered() {
		int  indexmin = Collections.min(getPreferences());
		return (values.get(indexmin));
	}

}
