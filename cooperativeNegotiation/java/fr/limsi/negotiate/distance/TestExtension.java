package fr.limsi.negotiate.distance;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.GenerateModel;
import fr.limsi.negotiate.restaurant.Restaurant;

public class TestExtension {
	public static void main(String[] args) {
		GenerateModel m = new GenerateModel();
		Negotiation<Restaurant> m1 = m.model1();
		Self_Ci<Criterion> self= m1.getValueNegotiation(Cuisine.class).getSelf();
		PreferenceMatrix<Criterion> matrix= new PreferenceMatrix<Criterion>(self.getElements());
		Extension<Criterion> ext = new Extension<>(matrix);
		
		System.out.println(ext.extension(self.getSelfPreferences()));
	}
}
