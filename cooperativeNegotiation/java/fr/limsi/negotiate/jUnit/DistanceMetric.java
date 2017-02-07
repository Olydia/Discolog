package fr.limsi.negotiate.jUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Preference;
import fr.limsi.negotiate.distance.Distance;
import fr.limsi.negotiate.restaurant.Atmosphere;
import fr.limsi.negotiate.restaurant.GenerateModel;

public class DistanceMetric {

	@Test
	public void test() {
		GenerateModel m = new GenerateModel();
		ArrayList<Preference<Criterion>> m1 = m.model1().getValueNegotiation(Atmosphere.class).getSelf().getSelfPreferences();
		ArrayList<Preference<Criterion>> m2 = m.model3().getValueNegotiation(Atmosphere.class).getSelf().getSelfPreferences();

		Distance d = new Distance(m1, m2, m.model1().getValueNegotiation(Atmosphere.class).getSelf().getElements());
		double distance = d.distance();
		System.out.println(distance);
		assertTrue(distance==0);
	}

}
