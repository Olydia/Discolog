package fr.limsi.negotiate.ToM;

import java.util.*;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.toyExample.*;

public class Models< O extends Option> {
	List<Self_Ci<Criterion>> criteria_models;
	
	public Models() {
		this.criteria_models = new ArrayList<Self_Ci<Criterion>> ();
		// TODO Auto-generated constructor stub
	}
	
	public List<Self_Ci<Criterion>> createModelCriterion(List<Criterion> elements){
		Preferences<Criterion> modelC = new Preferences<Criterion>(elements);
		return modelC.createPreferences();
	}

	public List<List<Self_Ci<Criterion>>> getCombination(int currentIndex,  List<List<Self_Ci<Criterion>>> containers) {
	    
		if (currentIndex == containers.size()) {
	        // Skip the items for the last container
	        List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	        combinations.add(new ArrayList<Self_Ci<Criterion>>());
	        return combinations;
	    }
	    List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	    List<Self_Ci<Criterion>> container = containers.get(currentIndex);
	    List<Self_Ci<Criterion>> containerItemList = container;
	    // Get combination from next index
	    List<List<Self_Ci<Criterion>>> suffixList = getCombination(currentIndex + 1, containers);
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
	
	public static void main (String[] args) {
		ToMNegotiator tom = new ToMNegotiator("TOM", new ToyModel().model1());
		for(Map.Entry<Double, List<List<Self_Ci<Criterion>>>> entry : tom.otherModel.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue().size() );
		}
//		Models<ToyRestaurant> m = new Models<>();
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
