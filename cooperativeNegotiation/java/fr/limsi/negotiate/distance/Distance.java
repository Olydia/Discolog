package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Preference;

public class Distance<C> {
	ArrayList<Preference<C>> firstModel;
	ArrayList<Preference<C>> secondModel;
	ArrayList<C> values;

	public Distance (ArrayList<Preference<C>> firstModel, ArrayList<Preference<C>> secondModel, ArrayList<C> value){
		this.firstModel = firstModel;
		this.secondModel = secondModel;
		this.values = value;
	}
	
	public List<ArrayList<Preference<C>>> generateExtension( ArrayList<Preference<C>> model, ArrayList<C> value){
		PreferenceMatrix<C> matrix= new PreferenceMatrix<C>(value, model);
		matrix.builtPreferences();
		Extension<C> ext = new Extension<C>(matrix);
		return ext.extensions();
	}
	
}
