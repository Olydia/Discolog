package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Self_Ci;

public class ValuePreferences extends Preferences<Criterion> {

	public ValuePreferences(List<Criterion> elements) {
		super(elements);
	}

	public List<Self_Ci<Criterion>> createPreferences (){
		 List<Self_Ci<Criterion>> pref = new ArrayList<Self_Ci<Criterion>>();
		 List<List<Criterion>> original = generatePerm(elem);
		 Class<Criterion> type = (Class<Criterion>) elem.get(0).getClass();
		 for (List<Criterion> o : original){
			 pref.add(generateModel(o, type));
		 }
		 return pref;
	 }
	 
	 public Self_Ci<Criterion> generateModel(List<Criterion> list, Class<Criterion> type){
		 Self_Ci<Criterion> model = new Self_Ci<Criterion>(type);
		 for(int i =0; i<list.size()-1; i++){
			 model.addPreference(list.get(i), list.get(i+1));
		 }
		 return model;
	 }



}
