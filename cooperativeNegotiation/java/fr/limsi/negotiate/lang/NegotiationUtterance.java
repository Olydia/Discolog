package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;

public abstract class NegotiationUtterance extends Utterance {

   // for extensions
   protected NegotiationUtterance (Class<? extends NegotiationUtterance> cls, Disco disco, Decomposition decomp, 
         String step, boolean repeat) { 
      super(cls, disco, decomp, step, repeat);
   }

   // for extensions
   protected NegotiationUtterance (Class<? extends NegotiationUtterance> cls, Disco disco, Boolean external) { 
      super(cls, disco, external);

   }

   public Negotiation<?> getNegotiation () {
      return ( (NegotiatorAgent) getDisco().getInteraction().getSystem()).getNegotiation();
      
   }

}
