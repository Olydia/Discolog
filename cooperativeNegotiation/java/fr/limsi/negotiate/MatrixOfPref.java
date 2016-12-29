package fr.limsi.negotiate;

import java.util.List;

public class MatrixOfPref<C> {

	private List<C> values; 
	private int [] [] preferences;  


	public MatrixOfPref(List<C> values) {
		this.values = values;
		preferences = new int [values.size()][values.size()];
	}

	public int[][] getPreferences() {
		return preferences;
	}
	public List<C> getValues(){
		return values;
	}

	public void addPreference(C less, C more){
		if(less == null){
			int indexMore = values.indexOf(more);
			for(int i=0; i< preferences.length; i++)
				insertPreference(i, indexMore);

		}
		else if(more == null){
			int indexLess = values.indexOf(less);
			for(int i=0; i< preferences.length; i++)
				insertPreference(indexLess, i);

		}
		else {
			int indexMore = values.indexOf(more);
			int indexLess = values.indexOf(less);
			insertPreference(indexLess, indexMore);
		}
	}

	public void insertPreference(int  indexLess, int indexMore) {

		preferences[indexLess][indexMore]= 1; // less < more
		preferences[indexMore][indexLess]= 0; // more > less
		transitivity(indexLess, indexMore);
	}

	public void transitivity(int indexLess, int indexMore) {
		for(int i=0; i< preferences.length; i++){
			//indexMore < i
			if(preferences[indexMore][i] == 1 && i != indexMore){
				preferences[indexLess][i] =1; // indexLess< i
			}		
		}
	}
	
	public float getScoreOf(C value){
		int index = values.indexOf(value);
		int score =0;
		for(int i=0; i< preferences.length; i++){
			score = score + preferences[index][i];
		}
		return(float) score/(values.size()-1);
		

	}

}
