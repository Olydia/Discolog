package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.PreferenceStatement.Acceptable;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.ProposalStatement;

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
	   Proposal p= getNegotiation().createProposal(getProposal().getValue(), !getExternal());
	   p.setStatus(Status.ACCEPTED);
	   getNegotiation().updateProposal(p);
	   getNegotiation().getContext().
	  	addStatement(new ProposalStatement(p, "Accept"));
	   
	   if(getProposal() instanceof CriterionProposal){
			if (getExternal()) 
				getNegotiation().updateOtherMentalState((Criterion) getProposal().getValue(), Acceptable.TRUE);
			else 
				getNegotiation().updateOASMentalState((Criterion)getProposal().getValue(), Acceptable.TRUE);
		}
   }
}