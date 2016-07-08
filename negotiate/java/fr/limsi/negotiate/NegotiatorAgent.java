package fr.limsi.negotiate;

//import java.util.Random;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import edu.wpi.disco.*;
import edu.wpi.disco.plugin.*;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.restaurant.*;

// TODO: Make small movie dialogue example to verify modularity
//import fr.limsi.negotiate.movie.*;

public class NegotiatorAgent extends Agent {

	private Negotiation<Restaurant> restaurant ;

	// use this instead of Disco.main().  See negotiate/bin/negotiate
	public static void main (String[] args) {
		InitiaterestauMentalState model = new InitiaterestauMentalState();
		new Dual( new NegotiatorAgent("Agent1", model.D1()), new NegotiatorAgent("Agent2", model.S2()), true).start();

	}

	public NegotiatorAgent (String name, Negotiation<Restaurant> model) { 
		super(name); 
		this.restaurant = model;
		// since agent has multiple choices, add DecompositionPlugin in order
		// for agent to "look ahead" to utterance choices (as in user menus) 
		new DecompositionPlugin(agenda, 25, true, true);
	}
	InitiaterestauMentalState mental = new InitiaterestauMentalState();

	/**
	 * @return current negotiation object
	 */
	public Negotiation<? extends Option> getNegotiation() { return restaurant; }


	//private final Negotiation<Movie> movie = InitiateMovieMentalState.Initialise();


}