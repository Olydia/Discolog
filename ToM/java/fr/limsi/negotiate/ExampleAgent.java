package fr.limsi.negotiate;

//import java.util.Random;
import edu.wpi.disco.*;
import edu.wpi.disco.plugin.*;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import fr.limsi.negotiate.restaurant.FR.*;

// TODO: Make small movie dialogue example to verify modularity
//import fr.limsi.negotiate.movie.*;

public class ExampleAgent extends Agent {
   
   // use this instead of Disco.main().  See negotiate/bin/negotiate
   public static void main (String[] args) {
	   
      Interaction interaction = new Interaction(
    		  new ToMNegotiatorProba("Agent1", new ModelDePreferencesTotal().modelBob(), ADAPT.COMPLEMENT), 
    		  new User("user"),
    		  args.length > 0 && args[0].length() > 0 ? args[0] : null);
      // do not guess recipes, since using DecompositionPlugin below
      interaction.setGuess(false); 
   
      interaction.load("models/NegotiateFR.xml");
      ((ToMNegotiatorProba)interaction.getSystem()).setRelation(0.55); 
      interaction.start(true); // give user first turn
   }
 
   public ExampleAgent (String name) { 
      super(name); 
      // since agent has multiple choices, add DecompositionPlugin in order
      // for agent to "look ahead" to utterance choices (as in user menus) 
      new DecompositionPlugin(agenda, 25, true, true);
   }
   
   private final Negotiation<Restaurant> model = new ModelDePreferencesTotal().model1();
   //private final Negotiation<Restaurant> model = new InitiaterestauMentalState().D1();

   /**
    * @return current negotiation object
    */
   public Negotiation<? extends Option> getNegotiation() { return model; }


   //private final Negotiation<Movie> movie = InitiateMovieMentalState.Initialise();


}