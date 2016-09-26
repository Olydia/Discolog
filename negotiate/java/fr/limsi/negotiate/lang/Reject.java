package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ProposalStatement;

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
	  Proposal p= getNegotiation().createProposal(getProposal().getValue(), !getExternal());
      getNegotiation().updateProposalStatus(p, Proposal.Status.REJECTED);
      getNegotiation().getContext().
	  	addStatement(new ProposalStatement(getExternal(),"Reject",p));
   }
}
