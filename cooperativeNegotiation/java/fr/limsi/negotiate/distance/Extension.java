package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;


public class Extension<T> {
	private PreferenceMatrix<T> matrix;
	// extensions is the list of all possible extensions
	private List<List<Preference<T>>>  extensions;


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

	public List<ArrayList<Preference<T>>> extensions(){
		List<ArrayList<Preference<T>>> totalExtensions = new ArrayList<ArrayList<Preference<T>>>();
		// extensions is defined with all the possible extensions
		this.getPossibleExtensions(matrix.nonRelatedCriteria(), 0);
		for(List<Preference<T>> nonRelated : extensions){
			ArrayList<Preference<T>> extension = new ArrayList<Preference<T>>(matrix.getSelfPreferences());
				//List<Preference<T>> nonRelated = matrix.nonRelatedCriteria();
			extension.addAll(nonRelated);
			if(isPlausibleExtension(extension, matrix.getValues()))
				totalExtensions.add(extension);
			
		}
		return totalExtensions;
	} 

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

	public boolean isPlausibleExtension(ArrayList<Preference<T>> input, List<T> values){
		PreferenceMatrix<T> m = new PreferenceMatrix<T>(values, input);
		return (m.builtPreferences());
			
	}


}
