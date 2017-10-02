package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.restaurant.Atmosphere;
import fr.limsi.negotiate.restaurant.Cuisine;
/**
 * 
 * @author ouldouali
 *
 * @param <C> the criterion
 * 
 * Computes the values of satisfiability for a given Criterion with the assumption that 
 * the model is defined with total ordered model of preferences  
 */

public class Satifiability {
	
	Criterion domain;

	public Satifiability(Criterion domain) {
		
		this.domain = domain;
	
	}
	
	public List<Float> getSat(){
		List<Float> as = new ArrayList<Float>();
		float sat = (float) 0.0;
		int length = domain.getValues().length -1 ;		
		for( int i = 0; i< domain.getValues().length; i++){
			sat =  (float) i/length;
			as.add(sat);
		}
		
		return as;
	}
	
	public int nbSat (double pow){
		List<Float> sats = getSat();
		int D = domain.getValues().length;
		for(float s : sats){
			if(s>= pow)
				return D - sats.indexOf(s);
		}
		return 0;
	}
	
	public  List<Set<Criterion>> generateHypModels(double pow) {
		int k = nbSat(pow);
		List<Criterion> elements = Arrays.asList(domain.getValues());
	    return getSubsets(elements, k);
	}
	
	/**
	 * 
	 * @param superSet = the set of values
	 * @param k= the number of satisfiable values
	 * @return
	 */
	public static List<Set<Criterion>> getSubsets(List<Criterion> superSet, int k) {
	    List<Set<Criterion>> res = new ArrayList<>();
	    getSubsets(superSet, k, 0, new HashSet<Criterion>(), res);
	    return res;
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


	
	public static void main(String[] args) {
		Satifiability c = new Satifiability(Atmosphere.FAMILY);
		System.out.println(c.getSat());
		System.out.println(c.nbSat(0.6));
		System.out.println(c.generateHypModels(0.6));
	}

	
	

}
