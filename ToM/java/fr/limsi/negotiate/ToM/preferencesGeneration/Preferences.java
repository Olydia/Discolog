package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.*;
import fr.limsi.negotiate.*;


public abstract class Preferences<E> {

	List<E> elem;
	
	public Preferences(List<E> elem) {
		this.elem = elem;
		// TODO Auto-generated constructor stub
	}
	 public List<List<E>> generatePerm(List<E> or) {
		 List<E> original = new ArrayList<E>(or);
	     if (original.size() == 0) { 
	       List<List<E>> result = new ArrayList<List<E>>();
	       result.add(new ArrayList<E>());
	       return result;
	     }
	     E firstElement = original.remove(0);
	     List<List<E>> returnValue = new ArrayList<List<E>>();
	     List<List<E>> permutations = generatePerm(original);
	     for (List<E> smallerPermutated : permutations) {
	       for (int index=0; index <= smallerPermutated.size(); index++) {
	         List<E> temp = new ArrayList<E>(smallerPermutated);
	         temp.add(index, firstElement);
	         returnValue.add(temp);
	       }
	     }
	     return returnValue;
	   }
	 
			
	 
//		public static void main (String[] args) {
//			List<ToyAtmosphere> c = Arrays.asList(ToyAtmosphere.values());
//			Preferences<ToyAtmosphere> elem= new Preferences<ToyAtmosphere>(c);
//			for(Self_Ci<ToyAtmosphere> p : elem.createPreferences()){
//				System.out.println(p.getSelfPreferences());
//			}
//
//		}
					

}
