package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.OptionProposal;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.Statement;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

public abstract class ProposalUtterance extends NegotiationUtterance {

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
   
   public Proposal getValue ( ) { return (Proposal) getSlotValue("proposal"); }
   
   
   //Propose postCondition
   protected void proposeUpdate(Proposal input) {
	   
		Proposal p;
		if(input instanceof CriterionProposal){
			p = new CriterionProposal(!getExternal(), (Criterion)input.getValue());
			getNegotiation().addStatement(new Statement<Criterion>((Criterion)p.getValue(),Satisfiable.TRUE), getExternal());
		}
		else 
			p = new OptionProposal(!getExternal(), (Option)input.getValue());
		
		getNegotiation().addProposal(p);

		//return p;
	}
   
   //Reject postCondition

   protected void rejectUpdate(Proposal rejected) {
	   
		if(rejected instanceof CriterionProposal){
			Criterion value = (Criterion) rejected.getValue();
			
			CriterionProposal p = new CriterionProposal(!getExternal(), value);
			p.setStatus(Status.REJECTED);
			
			CriterionNegotiation<Criterion>cn =getNegotiation().getValueNegotiation(value.getClass());
			cn.updateProposal((CriterionProposal)p);
			
			getNegotiation().addStatement(new Statement<Criterion>(value,Satisfiable.FALSE), getExternal());
			//this.setSlotValue("proposal", p);

		}
		
		else if(rejected instanceof OptionProposal){
			OptionProposal p = new OptionProposal(!getExternal(), (Option)rejected.getValue());
			p.setStatus(Status.REJECTED);
			getNegotiation().updateProposal(p);
			//this.setSlotValue("proposal", p);

		}
	}

}
