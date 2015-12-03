package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


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
	public Hashtable<T, Integer> getNormalizedPreferenceOrder(){
		Hashtable<T, Integer> preferencesOnCriteria = this.getPreferenceOrderOfCriteria();
		this.sortValue(preferencesOnCriteria);
		int k =0;
		for(T  elem: preferencesOnCriteria.keySet())
		{
			preferencesOnCriteria.put(elem, k);
			k++;
		}
		return preferencesOnCriteria;
	}
	
	public  void sortValue(Hashtable<?, Integer> t){

	       //Transfer as List and sort it
	       ArrayList<Map.Entry<?, Integer>> l = new ArrayList<Entry<?, Integer>>(t.entrySet());
	       Collections.sort(l, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o1.getValue().compareTo(o2.getValue());
	        }});

	       System.out.println(l);
	    }
	
 // ordrer the values by their preference utility
	public Hashtable<T, Integer> getPreferenceOrderOfCriteria(){
		Hashtable<T, Integer> preferencesOnCriteria = new Hashtable<T, Integer>();
		for(int i=0; i < preferences.length; i++)
			preferencesOnCriteria.put(values.get(i), getPreferenceOnValue(i));
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
