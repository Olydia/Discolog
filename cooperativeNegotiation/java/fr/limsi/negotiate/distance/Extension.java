package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;


public class Extension<T> {
	private PreferenceMatrix<T> matrix;
	private List<T> extension;

	public PreferenceMatrix<T> getMatrix() {
		return matrix;
	}

	public void setMatrix(PreferenceMatrix<T> matrix) {
		this.matrix = matrix;
	}

	public Extension (PreferenceMatrix<T> matrix){
		this.matrix = matrix;
	}
	
	public  List<ArrayList<Preference<T>>> extension(List<Preference<T>> poset){
		List<ArrayList<Preference<T>>> extensions = new ArrayList<ArrayList<Preference<T>>>();
		ArrayList<Preference<T>> extension = new ArrayList<Preference<T>>(poset);
		matrix.builtPreferences(extension);
		List<Preference<T>> nonRelated = matrix.nonRelatedCriteria();
		
		for(Preference<T> elem: nonRelated){
			int i = matrix.values.indexOf(elem.getMore());
			int j = matrix.values.indexOf(elem.getLess());

			if(matrix.getPreferences()[i][j] == 0){
				matrix.addPreference(elem.getLess(), elem.getMore());
				extension.add(elem);
			}
			if(matrix.nonRelatedCriteria().isEmpty())
				 extensions.add(extension);
		}
		return extensions;

	} 



}
