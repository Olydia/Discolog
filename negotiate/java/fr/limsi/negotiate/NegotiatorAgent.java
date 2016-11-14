package fr.limsi.negotiate;

//import java.util.Random;
import edu.wpi.disco.*;
import edu.wpi.disco.plugin.*;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.restaurant.*;

// TODO: Make small movie dialogue example to verify modularity
//import fr.limsi.negotiate.movie.*;

public class NegotiatorAgent extends Agent {

	private Negotiation<? extends Option> model ;


	public static void main (String[] args) {
		InitiaterestauMentalState model = new InitiaterestauMentalState();
		Negotiation<? extends Option> agent1= model.D1();
		Negotiation<? extends Option>  agent2 = model.D_A();
		Dual dual = new Dual( new NegotiatorAgent("Agent1", agent1), 
				new NegotiatorAgent("Agent2", agent2), true);
//		InitiateMovieMentalState model = new InitiateMovieMentalState();
//
//		Dual dual = new Dual( new NegotiatorAgent("Agent1", model.P1()), 
//						new NegotiatorAgent("Agent2", model.P2()), true);
//		
		dual.interaction1.load("models/currentModel/Negotiate.xml");
		dual.interaction1.load("models/currentModel/Negotiation.xml");
		dual.interaction2.load("models/currentModel/Negotiate.xml");
		dual.interaction2.load("models/currentModel/Negotiation.xml");
		dual.interaction1.eval("initiateNegotiation(15,RI.DOMINANT)", "NegotiatorAgent");
		dual.interaction2.eval("initiateNegotiation(15,RI.SUBMISSIVE)", "NegotiatorAgent");
		dual.interaction1.getDisco().addTop("Top");
		dual.start();
	}

	public NegotiatorAgent (String name, Negotiation<? extends Option> model) { 
		super(name); 
		this.model = model;
		// since agent has multiple choices, add DecompositionPlugin in order
		// for agent to "look ahead" to utterance choices (as in user menus) 
		new DecompositionPlugin(agenda, 25, true, true);
	}
	InitiaterestauMentalState mental = new InitiaterestauMentalState();

	/**
	 * @return current negotiation object
	 */
	public Negotiation<? extends Option> getNegotiation() { return model; }


	//private final Negotiation<Movie> movie = InitiateMovieMentalState.Initialise();


}