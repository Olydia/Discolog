package fr.limsi.negotiate.ToM;
import java.util.*;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.*;


public class Preferences< E extends Criterion> {

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
	 
	 public List<Self_Ci<E>> createPreferences (){
		 List<Self_Ci<E>> pref = new ArrayList<Self_Ci<E>>();
		 List<List<E>> original = generatePerm(elem);
		 for (List<E> o : original){
			 pref.add(generateModel(o));
		 }
		 return pref;
	 }
	 
	 public Self_Ci<E> generateModel(List<E> list){
		 @SuppressWarnings("unchecked")
		Class<E> type = (Class<E>) list.get(0).getClass();
		 Self_Ci<E> model = new Self_Ci<E>(type);
		 for(int i =0; i<list.size()-1; i++){
			 model.addPreference(list.get(i), list.get(i+1));
		 }
		 return model;
	 }
		public static void main (String[] args) {
			List<Atmosphere> c = Arrays.asList(Atmosphere.values());
			Preferences<Atmosphere> elem= new Preferences<>(c);
			System.out.println(elem.generatePerm(c));
			for(Self_Ci<Atmosphere> p : elem.createPreferences()){
				System.out.println(p.getSelfPreferences());
			}

		}
					

}
