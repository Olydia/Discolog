package fr.limsi.negotiate;

import java.util.*;

public class PreferenceModel<C extends Criterion> {
   
   private final Set<Preference<C>> preferences = new HashSet<Preference<C>>();
   
   public void add (Preference<C> preference) {
      preferences.add(preference);
   }

   /**
    * Return Boolean.TRUE if first argument is less preferred, or Boolean.FALSE
    * if first argument is more preferred, or null if no preference.
    */
   public Boolean isPreferred (C less, C more) {
      // TODO implement DFS to look for path from
      // less to more or from more to less
      return null;
   }
}
