package fr.limsi.negotiate.distance;

import fr.limsi.negotiate.restaurant.GenerateModel;

public class MovieDistance {


	public static void main(String[] args) {
		GenerateModel m = new GenerateModel();
		NegotiationDistance distance = new NegotiationDistance(m.model1(), m.model2());
		System.out.println(distance.distance());
	}

		

}
