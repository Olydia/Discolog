package restaurant.criteria;

import java.util.ArrayList;
import java.util.List;

import model.Criterion;

/* This class resume a relation of preference between all the existing binary relations between  values of a criterion. 
 *  * 
 */

public class Preference {

	// private ArrayList<PrefValues<T>> preferences = new ArrayList<PrefValues<T>>();

	List values; 
	private String criterionName;
	private int [] [] preferences; 


	public Preference(String criterionName, List values) {
		this.criterionName = criterionName;
		this.values = values;
		int length = values.size() +1;
		preferences = new int [length][length];
	}

	public String getCriterionName() {
		return criterionName;
	}

	public void addPreference(Criterion mostPreferred, Criterion lessPreferred) {
		if (mostPreferred.getClass().getSimpleName().equals(criterionName) && lessPreferred.getClass().getSimpleName().equals(criterionName)) {
		int i = values.indexOf(mostPreferred);
		int j = values.indexOf(lessPreferred);

		// add an exception in case where the index = -1

		preferences[i][j]= 1;
		preferences[j][i]= -1;
		transitivity(i, j);
		}
		else throw new RuntimeException(mostPreferred+" and "+lessPreferred +" don't belong to the same criteria");
	}

	public void transitivity (int indexMostPref, int indexLessPref){

		for(int i=0; i< preferences.length; i++){
			if(preferences[indexLessPref][i] == 1){
				preferences[indexMostPref][i] =1;
				preferences[i][indexMostPref] = -1;
			}
			if(preferences[indexMostPref][i] == -1){
				preferences[indexLessPref][i] = -1;
				preferences[i][indexLessPref] = 1;
			}
		}
	}
 // ordrer the values by their preference utility
	public ArrayList<Integer> getPreferenceOrderOfCriteria(){
		ArrayList<Integer> preferencesOnCriteria = new ArrayList<>();
		for(int i=0; i < preferences.length; i++)
			preferencesOnCriteria.add(i, getPreferenceOnValue(i));
		return preferencesOnCriteria;
	}

	private int getPreferenceOnValue(int i) {
		int somme = 0;
		for(int j=0; j < preferences.length; j++){			
			somme+=preferences[i][j];
		}
		return somme;
	}
	private int getPreferenceOnValue(Criterion c) {
		int i = values.indexOf(c);
		int somme = 0;
		getPreferenceOnValue(i);
		return somme;
	}

	// not used for now
	//    public void addEquals(T c1, T c2) {
	//    	preferences.add(new PrefValues<T>(c1, c1, PrefValues.EQUALS));
	//    }
}