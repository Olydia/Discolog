package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.*;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.toyExample.*;

public class Models<O extends Option> {
	
	public Models() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param elements : list of values needed to generate preferences
	 * @return List<Self_Ci<Criterion>> List of possible preferences generated from elements. factor of elements
	 */
	public List<Self_Ci<Criterion>> createValuesModel(List<Criterion> elements){
		ValuePreferences modelC = new ValuePreferences(elements);
		
		return modelC.createPreferences();
	}
	
	public List<Self_C<O>> createCriterionModel(List<Class<? extends Criterion>> elements, Class<O> type){
		CriterionPreferences<O> model = new CriterionPreferences<O>(elements);
		
		return model.createPreferences(type);
		
	}
	

	/**
	 * 
	 * @param currentIndex: index for the recursive call
	 * @param preferences : List of all possible preferences
	 * @return the combination of all the possible set of preferences for a given topic
	 */
	public List<List<Self_Ci<Criterion>>> getCombination(int currentIndex,  List<List<Self_Ci<Criterion>>> preferences) {
	    
		if (currentIndex == preferences.size()) {
	        // Skip the items for the last container
	        List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	        combinations.add(new ArrayList<Self_Ci<Criterion>>());
	        return combinations;
	    }
		
	    List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	    List<Self_Ci<Criterion>> container = preferences.get(currentIndex);
	    List<Self_Ci<Criterion>> containerItemList = container;
	    
	    // Get combination from next index
	    List<List<Self_Ci<Criterion>>> suffixList = getCombination(currentIndex + 1, preferences);
	    int size = containerItemList.size();
	    
	    for (int ii = 0; ii < size; ii++) {
	    	Self_Ci<Criterion> containerItem = containerItemList.get(ii);
	    	
	        if (suffixList != null) {
	        	
	            for (List<Self_Ci<Criterion>> suffix : suffixList) {
	                List<Self_Ci<Criterion>> nextCombination = new ArrayList<Self_Ci<Criterion>>();
	                nextCombination.add(containerItem);
	                nextCombination.addAll(suffix);
	                combinations.add(nextCombination);
	            }
	        }
	    }
	    return combinations;
	}
	
	
	public List<PrefNegotiation<O>> computeModels(List<Class<? extends Criterion>> criteria, Class<O> class1){
		 List<PrefNegotiation<O>> finalModel = new ArrayList<PrefNegotiation<O>>();
		 List<List<Self_Ci<Criterion>>> models = new ArrayList<List<Self_Ci<Criterion>>>();
			for( Class<? extends Criterion> e: criteria) {
				
				models.add(createValuesModel(Arrays.asList(e.getEnumConstants())));
			}
			
			List<List<Self_Ci<Criterion>>> valuesModel = getCombination(0, models);
			List<Self_C<O>> criterionModel = createCriterionModel(criteria, class1);
			
			// loop the criteria 

			for(Self_C<O> self: criterionModel){
				// loop values
				for(List<Self_Ci<Criterion>> value: valuesModel){
					finalModel.add(new PrefNegotiation<O>(value, self));
				}
			}
		return finalModel;
			
			
	}
	
	public static void main (String[] args) {
		Models<ToyRestaurant> m = new Models<ToyRestaurant>();
		List<PrefNegotiation<ToyRestaurant>> val = m.computeModels(ToyRestaurant.ARRIBA_MEXICO.getCriteria(), ToyRestaurant.class);
		for(PrefNegotiation<ToyRestaurant> v : val){
			System.out.println(v.getValues_prefs() + " "+ v.getCriteria_prefs());
		}
		System.out.println(val.size());
//		ToMNegotiator tom = new ToMNegotiator("TOM", new ToyModel().model1());
//		for(Map.Entry<Double, List<List<Self_Ci<Criterion>>>> entry : tom.otherModel.entrySet()){
//			System.out.println(entry.getKey() + " : " + entry.getValue().size());
//			for(List<Self_Ci<Criterion>> c : entry.getValue()){
//				System.out.println(c);
//			}
//		}
//		
//		List<List<Self_Ci<Criterion>>> containers = new ArrayList<List<Self_Ci<Criterion>>>();
//		
//		for (Class<? extends Criterion> c: ToyRestaurant.ARRIBA_MEXICO.getCriteria()){
//			List<Criterion> elems = Arrays.asList(ToyRestaurant.ARRIBA_MEXICO.getValue(c).getValues());
//			containers.add(m.createModelCriterion(elems));
//		}
//		List<List<Self_Ci<Criterion>>> results = m.getCombination(0, containers);
//		System.out.println(results.size());

	}
	
}
