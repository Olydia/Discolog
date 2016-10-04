package fr.limsi.negotiate;

import java.util.*;

public class PreferenceMatrix<T> {
	List<T> values; 
	private int [] [] preferences; 

	public PreferenceMatrix(List<T> values) {
		this.values = values;
		preferences = new int [values.size()][values.size()];
	}

	// TODO check if preference(less, more) is not already defined in order to avoid inconcisty and cycles
	public void insertPreference(T less, T more) {
		int indexMore = values.indexOf(more);
		int indexLess = values.indexOf(less);
//		if(preferences[indexLess][indexMore] == 1)
//			try {
//				throw new Exception("Contradiction: P ("+less+", " + more +") exists in the preferences list");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		// add an exception in case where the index = -1
		preferences[indexMore][indexLess]= 1 ;
		preferences[indexLess][indexMore]= -1;
		transitivity(indexLess, indexMore);
	}
	public void addPreference(T less, T more) {

		if(!(more == null && less == null)){
			if(more == null){
				addLeastPreferred(less);

			}
			else if(less == null){
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
				preferences[i][j] = -1;
			}
		}
	}

	public void addLeastPreferred(T value){
		int j = values.indexOf(value);
		for(int i=0; i< preferences.length; i++){
			if(i !=j) {
				preferences[j][i] = - 1;
				preferences[i][j] = 1;
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
		Map<Integer, Integer> mapValues = new HashMap<Integer, Integer>();
		ArrayList<Integer> prefValues = getPreferences();
		for(int i=0; i<prefValues.size(); i++)
			mapValues.put(prefValues.get(i), i);

	    ArrayList<Integer> sortedRank = new ArrayList<Integer>(new TreeMap<Integer, Integer>(mapValues).values());
		for(int elem : sortedRank)
			prefValues.set(elem, sortedRank.indexOf(elem));
		
		return (prefValues);
			
	}

	private static final int maxIndex(ArrayList<Integer> a) {
		int imax=0;
		for(int i=1;i<a.size();i++)
			if (a.get(i)>a.get(imax))
				imax = i;
		return imax;
	}

//	private static final int minIndex(ArrayList<Integer> a) {
//		int imin = a.get(0);
//		for(int i=1;i<a.size();i++)
//			if (a.get(i)<=a.get(imin))
//				imin = i;
//		return imin;
//	}

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
