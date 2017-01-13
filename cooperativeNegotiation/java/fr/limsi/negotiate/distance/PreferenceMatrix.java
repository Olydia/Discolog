package fr.limsi.negotiate.distance;

import java.util.*;

import fr.limsi.negotiate.Preference;

public class PreferenceMatrix<T> {
	List<T> values; 
	private int [] [] preferences; 

	public PreferenceMatrix(List<T> values) {
		this.values = values;
		preferences = new int [values.size()][values.size()];
	}

	// TODO check if preference(less, more) is not already defined in order to avoid inconcisty and cycles
	public boolean insertPreference(int indexLess, int indexMore) {
		
		if(preferences[indexLess][indexMore] == 1){
			System.out.println("Contradiction: P ("+values.get(indexLess)+", " + values.get(indexMore) +") exists in the preferences list");
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
		int indexMore = values.indexOf(more);
		int indexLess = values.indexOf(less);
		boolean insert = insertPreference(indexLess, indexMore);
		if(insert && transitivity(indexLess, indexMore)) 
			return true;
		else{
			// undo add 
			this.preferences = pref;
			return false;

		}

	}

	public void builtPreferences(ArrayList<Preference<T>> selfPreferences){
		for(Preference<T> elem : selfPreferences)
			addPreference(elem.getLess(), elem.getMore());
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
		int i = values.indexOf(c);
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
					elems.add(new Preference<T> (values.get(i), values.get(j)));
			}
		}
		return elems;

	}
	
	public List<ArrayList<Preference<T>>> allPossibleComination(ArrayList<Preference<T>> nonRelated){
		List<ArrayList<Preference<T>>> extensions = new ArrayList<ArrayList<Preference<T>>>();
		extensions.add(nonRelated);
		for(Preference<T> elm : nonRelated){
			
		}
		return extensions;
	}

}
