package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		GenerateModel m = new GenerateModel();
		Self_Ci<Criterion> e = m.model1().getValueNegotiation(Cuisine.class).getSelf();
		//e.add(new Preference<Cost>(Cost.CHEAP, Cost.EXPENSIVE));
		PreferenceMatrix<Criterion> matrix= new PreferenceMatrix<Criterion>(e.getElements(), e.getSelfPreferences());
		matrix.builtPreferences();
		Extension<Criterion> ext = new Extension<Criterion>(matrix);

		
//
		for(ArrayList<Preference<Criterion>> ex : ext.extensions()){
			System.out.println(ex);
		}
		//SSystem.out.println(ext.extension(matrix.values));
	}
}
