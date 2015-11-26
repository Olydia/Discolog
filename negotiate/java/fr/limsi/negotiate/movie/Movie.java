package fr.limsi.negotiate.movie;
import fr.limsi.negotiate.*;

public enum Movie implements Option {
   
   MISSION_IMPOSSIBLE (Genre.ACTION, Time.EARLY),
      
   MIDNIGHT_IN_PARIS (Genre.ROMANCE, Time.LATE);
   
   public final Genre genre;
   public final Time time;
   
   Movie (Genre genre, Time time) {
      this.genre = genre;
      this.time = time;
   }
   
   @Override
   public Criterion getValue (Class<? extends Criterion> c) {
      return c == Genre.class ? genre :
         c == Time.class ? time : null; // throw error? 
   }
}
