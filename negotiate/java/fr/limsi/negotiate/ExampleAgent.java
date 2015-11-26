package fr.limsi.negotiate;
import fr.limsi.negotiate.restaurant.*;
import fr.limsi.negotiate.movie.*;

public class ExampleAgent {
   
   @SuppressWarnings("unchecked")
   private final Negotiation<Restaurant> restaurant = 
         new Negotiation<Restaurant>(
               new CriterionNegotiation[] {
                  new CriterionNegotiation<Cuisine>(),
                  new CriterionNegotiation<Cost>() });
   
   @SuppressWarnings("unchecked")
   private final Negotiation<Movie> movie = 
         new Negotiation<Movie>(
               new CriterionNegotiation[] {
                  new CriterionNegotiation<Genre>(),
                  new CriterionNegotiation<Time>() });

}
