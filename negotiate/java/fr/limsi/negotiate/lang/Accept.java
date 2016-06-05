package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;

public class Accept extends ProposalUtterance {

   public static TaskClass CLASS;

   // for TaskClass.newStep
   public Accept (Disco disco, Decomposition decomp, String name, boolean repeat) { 
      super(Accept.class, disco, decomp, name, repeat);
   }

   public Accept (Disco disco, Boolean external, Proposal proposal) { 
      super(Accept.class, disco, external, proposal);
   }

   @Override
   protected void interpret () {
	   getProposal().setIsSelf(!getExternal());
	   getNegotiation().context.updateProposals(getProposal());
	   getNegotiation().updateProposalStatus(getProposal(), Proposal.Status.ACCEPTED);
   }
}
