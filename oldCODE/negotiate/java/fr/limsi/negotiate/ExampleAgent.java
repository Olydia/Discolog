package fr.limsi.negotiate;

import edu.wpi.disco.*;
import edu.wpi.disco.plugin.*;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.movie.*;

// TODO: Make small movie dialogue example to verify modularity
//import fr.limsi.negotiate.movie.*;

public class ExampleAgent extends Agent {
   
   // use this instead of Disco.main().  See negotiate/bin/negotiate
   public static void main (String[] args) {
	   
      Interaction interaction = new Interaction(
            new ExampleAgent("agent"), 
            new User("user"),
            args.length > 0 && args[0].length() > 0 ? args[0] : null);
      // do not guess recipes, since using DecompositionPlugin below
      interaction.setGuess(false); 
      interaction.load("models/Negotiate.xml");
      interaction.load("models/Negotiation.xml");
      interaction.eval("relation = RI.PEER", "ExampleAgent");
      //interaction.getDisco().addTop("Top");
      // TODO: enable random choice among applicable utterances (disabled
      //       for now to make debugging easier
      // Agent.RANDOM = new Random(12345);
      interaction.start(true); // give user first turn
   }
 
   public ExampleAgent (String name) { 
      super(name); 
      // since agent has multiple choices, add DecompositionPlugin in order
      // for agent to "look ahead" to utterance choices (as in user menus) 
      new DecompositionPlugin(agenda, 25, true, true);
   }
   
   private final Negotiation<Movie> model = new InitiateMovieMentalState().P1();
   //private final Negotiation<Restaurant> model = new InitiaterestauMentalState().D1();

   /**
    * @return current negotiation object
    */
   public Negotiation<? extends Option> getNegotiation() { return model; }


   //private final Negotiation<Movie> movie = InitiateMovieMentalState.Initialise();


}