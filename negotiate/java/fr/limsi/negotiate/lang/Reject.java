package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;

public class Reject extends ProposalUtterance {

   public static TaskClass CLASS;

   // for TaskClass.newStep
   public Reject (Disco disco, Decomposition decomp, String name, boolean repeat) { 
      super(Reject.class, disco, decomp, name, repeat);
   }

   public Reject (Disco disco, Boolean external, Proposal proposal) { 
      super(Reject.class, disco, external, proposal);
   }

   @Override
   protected void interpret () {
	   getProposal().setIsSelf(!getExternal());
	  getNegotiation().getContext().updateProposals(getProposal());
      getNegotiation().updateProposalStatus(getProposal(), Proposal.Status.REJECTED);
   }
}
