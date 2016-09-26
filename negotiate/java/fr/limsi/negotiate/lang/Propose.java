package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ProposalStatement;

public class Propose extends ProposalUtterance {
   
   public static TaskClass CLASS;

   // for TaskClass.newStep
   public Propose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
      super(Propose.class, disco, decomp, name, repeat);
   }

   public Propose (Disco disco, Boolean external, Proposal proposal) { 
      super(Propose.class, disco, external, proposal);
   }

   @Override
   protected void interpret () {
	  Proposal p= getNegotiation().createProposal(getProposal().getValue(), !getExternal());
	  getNegotiation().addProposal(p);
	  getNegotiation().getContext().
	  	addStatement(new ProposalStatement(getExternal(),"Propose",p));
   }
}