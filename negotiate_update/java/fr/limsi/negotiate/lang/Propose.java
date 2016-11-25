package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.PreferenceStatement.Acceptable;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ProposalStatement;
import fr.limsi.negotiate.Proposal.Status;

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
	  p.setStatus(Status.OPEN);
	  getNegotiation().addProposal(p);
	  getNegotiation().getContext().
	  	addStatement(new ProposalStatement(p, "Propose"));
	  
	  if(getProposal() instanceof CriterionProposal){
			if (getExternal()) 
				getNegotiation().updateOtherMentalState((Criterion) getProposal().getValue(), Acceptable.TRUE);
			else 
				getNegotiation().updateOASMentalState((Criterion)getProposal().getValue(), Acceptable.TRUE);
		}
   }
}