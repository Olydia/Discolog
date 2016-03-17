package JunitTests;


import static org.junit.Assert.*;

import org.junit.Test;

import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.InitiaterestauMentalState;
import fr.limsi.negotiate.restaurant.Restaurant;

public class MentalStateUpdate {


	@Test
	public void testOAS() {
		Negotiation<Restaurant> nego = InitiaterestauMentalState.Initialise();
		nego.updateOASMentalState(Cuisine.JAPANESE, Cuisine.ITALIAN);
		assertTrue(nego.isInOAS(Cuisine.JAPANESE, Cuisine.ITALIAN));
	}
	
	@Test
	public void testOther() {
		Negotiation<Restaurant> nego = InitiaterestauMentalState.Initialise();
		nego.updateOtherMentalState(Cuisine.JAPANESE, Cuisine.ITALIAN);
		assertTrue(nego.isInOther(Cuisine.JAPANESE, Cuisine.ITALIAN));
	}

}
