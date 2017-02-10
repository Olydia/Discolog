package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.*;

public class MovieTest {

	public static void main(String[] args) {
		InitiateMovieMentalState mv = new InitiateMovieMentalState();
		Negotiation<Movie> nmv= mv.P1();
		for(Option m: nmv.getAcceptableOptions()){
			System.out.println(m);
		}
	}
}
