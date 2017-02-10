package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;

abstract class ProposalUtterance extends NegotiationUtterance {

   // for extensions
   protected ProposalUtterance (Class<? extends ProposalUtterance> cls, Disco disco, Decomposition decomp, 
         String step, boolean repeat) { 
      super(cls, disco, decomp, step, repeat);
   }

   // for extensions
   protected ProposalUtterance (Class<? extends ProposalUtterance> cls, Disco disco, Boolean external, 
         Proposal proposal) { 
      super(cls, disco, external);
      if ( proposal != null ) setSlotValue("proposal", proposal);
   }

   public Proposal getProposal () { return (Proposal) getSlotValue("proposal"); }

}
