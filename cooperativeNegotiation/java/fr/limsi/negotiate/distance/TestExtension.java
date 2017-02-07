package fr.limsi.negotiate.distance;

import java.util.*;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.*;



public class TestExtension {
	
	public static ArrayList<Preference<Criterion>> inverse (){
		ArrayList<Preference<Criterion>> inverseM1 = new ArrayList<Preference<Criterion>>();
		inverseM1.add(new Preference<Criterion>(Cuisine.CHINESE, Cuisine.KOREAN));
		inverseM1.add(new Preference<Criterion>(Cuisine.CHINESE, Cuisine.JAPANESE));
		inverseM1.add(new Preference<Criterion>(Cuisine.KOREAN, Cuisine.FRENCH));
		inverseM1.add(new Preference<Criterion>(Cuisine.JAPANESE, Cuisine.TURKISH));
		inverseM1.add(new Preference<Criterion>(Cuisine.FRENCH, Cuisine.MEXICAN));
		inverseM1.add(new Preference<Criterion>(Cuisine.FRENCH, Cuisine.ITALIAN));
		inverseM1.add(new Preference<Criterion>(Cuisine.TURKISH, Cuisine.MEXICAN));


	return inverseM1;
	}
	
	public static void main(String[] args) {
		ArrayList<Preference<Criterion>> m1 = new ArrayList<Preference<Criterion>>();
		m1.add(new Preference<Criterion>(Cost.CHEAP, Cost.AFFORDABLE));
		m1.add(new Preference<Criterion>(Cost.CHEAP, Cost.EXPENSIVE));

		ArrayList<Preference<Criterion>> m2 = new ArrayList<Preference<Criterion>>();
		m2.add(new Preference<Criterion>(Cost.CHEAP, Cost.AFFORDABLE));
		m2.add(new Preference<Criterion>(Cost.AFFORDABLE, Cost.EXPENSIVE));
		List<Criterion> values = Arrays.asList(Cost.values());
		totalOrderedModels m = new totalOrderedModels();
		NegotiationDistance e = new NegotiationDistance(m.model1(), m.model4());
		//Distance d = new Distance(m1, m2, values);
		System.out.println(e.distance());

	}
}
