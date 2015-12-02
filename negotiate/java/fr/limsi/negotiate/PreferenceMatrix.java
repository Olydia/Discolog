package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

public class PreferenceMatrix<T> {
	List<T> values; 
	private int [] [] preferences; 
	
	public PreferenceMatrix(List<T> values) {
		// TODO Auto-generated constructor stub
		this.values = values;
		preferences = new int [values.size()][values.size()];
	}


	public void addPreference(T lessPreferred, T mostPreferred) {
		
		int i = values.indexOf(lessPreferred);
		int j = values.indexOf(mostPreferred);
		// add an exception in case where the index = -1
		preferences[i][j]= -1 ;
		preferences[j][i]= 1;
		transitivity(i, j);
		
	}

	public void transitivity (int indexLessPref, int indexMostPref){

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
	public ArrayList<Integer> getNormalizedPreferenceOrder(){
		ArrayList<Integer> preferencesOnCriteria = new ArrayList<>();
		
		return preferencesOnCriteria;
	}
 // ordrer the values by their preference utility
	public ArrayList<Integer> getPreferenceOrderOfCriteria(){
		ArrayList<Integer> preferencesOnCriteria = new ArrayList<>();
		for(int i=0; i < preferences.length; i++)
			preferencesOnCriteria.add(i, getPreferenceOnValue(i));
		return preferencesOnCriteria;
	}

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

}
