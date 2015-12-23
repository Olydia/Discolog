package fr.limsi.negotiate;

//import java.util.Random;
import edu.wpi.disco.*;
import edu.wpi.disco.plugin.*;
import fr.limsi.negotiate.restaurant.*;

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
 
   @SuppressWarnings("unchecked")
   private final Negotiation<Restaurant> restaurant = 
         new Negotiation<Restaurant>(
               new CriterionNegotiation[] {
                  new CriterionNegotiation<Cuisine>(Cuisine.class),
                  new CriterionNegotiation<Cost>(Cost.class) },
               new OptionNegotiation());
   
   /**
    * @return current negotiation object
    */
   public Negotiation<? extends Option> getNegotiation () { return restaurant; }

/*
   @SuppressWarnings("unchecked")
   private final Negotiation<Movie> movie = 
         new Negotiation<Movie>(
               new CriterionNegotiation[] {
                  new CriterionNegotiation<Genre>(),
                  new CriterionNegotiation<Time>() });
*/

}