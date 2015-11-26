package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.*;

public enum Restaurant implements Option {
   
   LE_PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE),
      
   CHEZ_CHUCK (Cuisine.CHINESE, Cost.CHEAP);
   
   public final Cuisine cuisine;
   public final Cost cost;
   
   Restaurant (Cuisine cuisine, Cost cost) {
      this.cuisine = cuisine;
      this.cost = cost;
   }
   
   @Override
   public Criterion getValue (Class<? extends Criterion> c) {
      return c == Cuisine.class ? cuisine :
         c == Cost.class ? cost : null; // throw error? 
   }
}
