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
		inverseM1.add(new Preference<Criterion>(Cuisine.FRENCH, Cuisine.MIXICAN));
		inverseM1.add(new Preference<Criterion>(Cuisine.FRENCH, Cuisine.ITALIAN));
		inverseM1.add(new Preference<Criterion>(Cuisine.TURKISH, Cuisine.MIXICAN));


	return inverseM1;
	}
	
	public static void main(String[] args) {
		GenerateModel m = new GenerateModel();
//		Self_Ci<Criterion> e = m.model1().getValueNegotiation(Cuisine.class).getSelf();
//		Self_Ci<Criterion> e2 = m.model2().getValueNegotiation(Cuisine.class).getSelf();
//		List<Criterion> values =e.getElements();
//		Distance d = new Distance(e.getSelfPreferences(), e.getSelfPreferences(), values);
//		System.out.println(d.distance());
//		System.out.println(d.Knn());
		NegotiationDistance distance = new NegotiationDistance(m.model2(), m.model1());
		System.out.println(distance.distance());
	}
}
