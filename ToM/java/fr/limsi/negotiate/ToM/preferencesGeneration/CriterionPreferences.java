package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;

public class CriterionPreferences<O extends Option> extends Preferences<Class<? extends Criterion>> {

	public CriterionPreferences(List<Class<? extends Criterion>> elems) {
	
		super(elems);
	}

	
	public List<Self_C<O>> createPreferences(Class<O> type) {
		
		List<Self_C<O>> pref = new ArrayList<Self_C<O>>();
		 List<List<Class<? extends Criterion>>> original = generatePerm(elem);
		 for (List<Class<? extends Criterion>> o : original){
			 pref.add(generateModel(o, type));
		 }
		 return   pref;
	}

	public Self_C<O> generateModel(List<Class<? extends Criterion>> list, Class<O> type) {
		Self_C<O> model = new Self_C<O> (type);
		 for(int i =0; i<list.size()-1; i++){
			 model.addPreference(list.get(i), list.get(i+1));
		 }
		 return model;
	 }


}
