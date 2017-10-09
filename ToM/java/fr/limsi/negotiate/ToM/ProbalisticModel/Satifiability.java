package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.limsi.negotiate.Criterion;
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
	
	 private Class<? extends Criterion> domain;

	public Class<? extends Criterion> getDomain() {
		return domain;
	}

	public Satifiability(Class<? extends Criterion> domain) {
		
		this.domain = domain;
	
	}
	
	/**
	 * 
	 * @return computes the values of satifiability for the domain, 
	 * with the assumption of total order preferences on the values of the domain.
	 */
	
	public List<Float> getSat(){
		List<Float> as = new ArrayList<Float>();
		float sat = (float) 0.0;
		int length = domain.getEnumConstants().length -1 ;		
		for( int i = 0; i< domain.getEnumConstants().length; i++){
			sat =  (float) i/length;
			as.add(sat);
		}
		
		return as;
	}
	/**
	 * 
	 * @param pow
	 * @return nbSat:  Computes the number of satisfiable values for a given pow
	 */
	public int nbSat (double pow){
		List<Float> sats = getSat();
		int D = domain.getEnumConstants().length;
		for(float s : sats){
			if(s>= pow)
				return D - sats.indexOf(s);
		}
		return 0;
	}
	
	
	/**
	 * 
	 * @param pow
	 * @return Computes the different models of satisfiable for the domain given a value pow
	 */
	public  List<SatCriterion> generateHypModels(double pow) {
		List<SatCriterion> pref = new ArrayList<SatCriterion>();
		int k = nbSat(pow);
		List<Criterion> elements = Arrays.asList(domain.getEnumConstants());
		List<Set<Criterion>>  subsets =  getSubsets(elements, k);
		
		for (Set<Criterion> m : subsets){
			List<Criterion> set = new ArrayList<Criterion> (m);
			pref.add(new SatCriterion(domain, set));
		}
		return pref;
	}
	
	
	
	/**
	 * 
	 * @param superSet = the set of values
	 * @param k= the number of satisfiable values
	 * @return
	 */
	public  List<Set<Criterion>> getSubsets(List<Criterion> superSet, int k) {
	    List<Set<Criterion>> res = new ArrayList<>();
	    getSubsets(superSet, k, 0, new HashSet<Criterion>(), res);
	    return res;
	}
	
	/**
	 * 
	 * @return the subsets of size k 
	 */

	private void getSubsets(List<Criterion> superSet, int k, int idx, Set<Criterion> current,List<Set<Criterion>> solution) {
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


}
