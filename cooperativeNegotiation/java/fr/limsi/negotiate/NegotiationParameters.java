package fr.limsi.negotiate;

public class NegotiationParameters {
	// sigma : threshold of the relation of dominance
	 public static final double sigma = 0.5;
	 
	 // minimum of non accepted proposals in the negotiation
	 public static final int tau = 2;
	 
	 // threshold of the satisfiability
	 public static final double beta = 0.65;
	 
	 // parameter of the concession loop
	 public static final double delta = 0.1;
	 
	 // threshold of the acceptability
	 public static final double alfa = 0.6;
	 
	 // maximum number of preferences moves to take back the lead of negotiation
	 public static final int lead = 4;

}
