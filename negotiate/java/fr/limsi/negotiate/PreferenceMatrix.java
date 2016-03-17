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

	// TODO check if preference(less, more) is not already defined in order to avoid inconcisty and cycles
	public void insertPreference(T less, T more) {
		int indexMore = values.indexOf(more);
		int indexLess = values.indexOf(less);
		if(preferences[indexLess][indexMore] == 1)
			try {
				throw new Exception("Contradiction: P ("+less+", " + more +") exists in the preferences list");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// add an exception in case where the index = -1
		preferences[indexMore][indexLess]= 1 ;
		preferences[indexLess][indexMore]= -1;
		transitivity(indexLess, indexMore);
	}
	public void addPreference(T less, T more) {

		if(!(more.equals(null) && less.equals(null))){
			if(more.equals(null)){
				addLeastPreferred(less);

			}
			if(less.equals(null)){
				addMostPreferred(more);
			}
			else{
				insertPreference(less, more);
			}
		}
	}

	public void addMostPreferred(T value){
		int j = values.indexOf(value);
		for(int i=0; i< preferences.length; i++){
			if(i !=j) {
				preferences[j][i] = 1;
				preferences[i][i] = -1;
				transitivity(j, i);
			}
		}
	}

	public void addLeastPreferred(T value){
		int j = values.indexOf(value);
		for(int i=0; i< preferences.length; i++){
			if(i !=j) {
				preferences[j][i] = - 1;
				preferences[i][i] = 1;
				transitivity(i, j);
			}
		}
	}
	public void transitivity (int indexLess , int  indexMore){

		for(int i=0; i< preferences.length; i++){
			if(preferences[indexLess][i] == 1 && i != indexMore){
				preferences[indexMore][i] =1;
				preferences[i][indexMore] = -1;
			}
			if(preferences[indexMore][i] == -1 && i != indexLess){
				preferences[indexLess][i] = -1;
				preferences[i][indexLess] = 1;
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

	private static final int minIndex(ArrayList<Integer> a) {
		int imin = a.get(0);
		for(int i=1;i<a.size();i++)
			if (a.get(i)<=a.get(imin))
				imin = i;
		return imin;
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
