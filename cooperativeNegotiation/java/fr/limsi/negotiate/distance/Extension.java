package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;


public class Extension<T> {
	private PreferenceMatrix<T> matrix;
	// extensions is the list of all possible extensions
	private List<List<Preference<T>>>  extensions;


	/**
	 * Compute all the possible extensions of the preferences
	 * @return list of extensions
	 */

	public List<PreferenceMatrix<T>> extensions(){
		List<PreferenceMatrix<T>> totalExtensions = new ArrayList<PreferenceMatrix<T>>();
		// extensions is defined with all the possible extensions
		
		this.getPossibleExtensions(matrix.nonRelatedCriteria(), 0);
		
		for(List<Preference<T>> nonRelated : extensions){
			
			ArrayList<Preference<T>> extension = new ArrayList<Preference<T>>(matrix.getSelfPreferences());
			extension.addAll(nonRelated);
			PreferenceMatrix<T> m = new PreferenceMatrix<T>(matrix.getValues(), extension);
			
			if(isPlausibleExtension(m))
				totalExtensions.add(m);
			
		}
		return totalExtensions;
	} 

	/**
	 * 
	 * @param List of initial preferences
	 * @param cmp
	 * Compute all the possible combinations of extensions. Don't take into account the prevention from cycles
	 */
	public void getPossibleExtensions (List<Preference<T>> input, int cmp){

		if (cmp >= input.size()){
			if (!extensions.contains(input))
				extensions.add(input);
		}
		else {
			Preference<T> current_value= input.get(cmp);

			List<Preference<T>> possible_value = new ArrayList<Preference<T>>();

			possible_value.add(current_value);
			possible_value.add(new Preference<T>(current_value.getMore(), current_value.getLess()));

			for (Preference<T> val : possible_value){
				List<Preference<T>> tmp = new ArrayList<>();
				for(Preference<T> a : input){
					tmp.add(a);
				}
				tmp.set(cmp, val);
				getPossibleExtensions(tmp, cmp+1);

			}
		}

	}

	/**
	 * 
	 * @param input: list of preferences
	 * @param values: the list of values which is defined the preferences
	 * @return the extension compute a total order of preferences without cycles.
	 */
	public boolean isPlausibleExtension(ArrayList<Preference<T>> input, List<T> values){
		PreferenceMatrix<T> m = new PreferenceMatrix<T>(values, input);
		return (m.builtPreferences());
			
	}

	public boolean isPlausibleExtension(PreferenceMatrix<T> m){
		return (m.builtPreferences());
			
	}
	
	public List<List<Preference<T>>> getExtensions() {
		return extensions;
	}

	public PreferenceMatrix<T> getMatrix() {
		return matrix;
	}
	

	public void setMatrix(PreferenceMatrix<T> matrix) {
		this.matrix = matrix;
	}
	// build preferences is already called 
	public Extension (PreferenceMatrix<T> matrix){
		this.matrix = matrix;
		this.extensions = new ArrayList<List<Preference<T>>>();

	}

}
