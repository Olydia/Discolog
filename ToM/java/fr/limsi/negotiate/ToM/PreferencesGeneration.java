package fr.limsi.negotiate.ToM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.toyExample.ToyCuisine;

public class PreferencesGeneration {

	public PreferencesGeneration() {
		
	}
	
	private static void getSubsets(List<Criterion> superSet, int k, int idx, Set<Criterion> current,List<Set<Criterion>> solution) {
	    //successful stop clause
	    if (current.size() == k) {
	        solution.add(new HashSet<>(current));
	        return;
	    }
	    //unseccessful stop clause
	    if (idx == superSet.size()) return;
	    Criterion x = superSet.get(idx);
	    current.add(x);
	    //"guess" x is in the subset
	    getSubsets(superSet, k, idx+1, current, solution);
	    current.remove(x);
	    //"guess" x is not in the subset
	    getSubsets(superSet, k, idx+1, current, solution);
	}

	public static List<Set<Criterion>> getSubsets(List<Criterion> superSet, int k) {
	    List<Set<Criterion>> res = new ArrayList<>();
	    getSubsets(superSet, k, 0, new HashSet<Criterion>(), res);
	    return res;
	}

	public static void main(String[] args) {
		List<Criterion> superSet = Arrays.asList(ToyCuisine.values());
//		 new ArrayList<>();
//		superSet.add(1);
//		superSet.add(2);
//		superSet.add(3);
//		superSet.add(4);
		System.out.println(getSubsets(superSet,3));
	}


}
