package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Self_Ci;

public class MHypothesis {

	public MHypothesis() {
		// TODO Auto-generated constructor stub
	}
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

}
