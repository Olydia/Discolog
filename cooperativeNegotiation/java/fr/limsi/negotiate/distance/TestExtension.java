package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.Arrays;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Preference;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.restaurant.Cost;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.GenerateModel;
import fr.limsi.negotiate.restaurant.Restaurant;

public class TestExtension {
	public static void main(String[] args) {
//		GenerateModel m = new GenerateModel();
//		Negotiation<Restaurant> m1 = m.model1();
//		Self_Ci<Criterion> self= m1.getValueNegotiation(Cuisine.class).getSelf();
//		PreferenceMatrix<Criterion> matrix= new PreferenceMatrix<Criterion>(self.getElements());
		//		System.out.println(ext.extension(self.getSelfPreferences()));

//		Extension<Criterion> ext = new Extension<>(matrix);
		ArrayList<Preference<Cost>> e = new ArrayList<Preference<Cost>>();
		e.add(new Preference<Cost> (Cost.EXPENSIVE, Cost.AFFRODABLE));
		e.add(new Preference<Cost>(Cost.AFFRODABLE, Cost.CHEAP));
		e.add(new Preference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		PreferenceMatrix<Cost> matrix= new PreferenceMatrix<Cost>(Arrays.asList(Cost.values()));
		matrix.builtPreferences(e);
	}
}
