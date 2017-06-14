package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

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
	public void interpret () {


		if(getProposal() instanceof CriterionProposal){
			Criterion value = (Criterion) getProposal().getValue();
			CriterionProposal p = new CriterionProposal(!getExternal(), value);
			p.setStatus(Status.ACCEPTED);
			
			this.setSlotValue("proposal", p);
			
			CriterionNegotiation<Criterion>cn =getNegotiation().getValueNegotiation(value.getClass());
			cn.updateProposal((CriterionProposal)getProposal());
			getNegotiation().addStatement(new Statement<Criterion>(value,Satisfiable.TRUE), getExternal());
		}

		else if(getProposal() instanceof OptionProposal){
			OptionProposal p = new OptionProposal(!getExternal(), (Option)getProposal().getValue());
			p.setStatus(Status.ACCEPTED);
			getNegotiation().updateProposal(p);
			
			this.setSlotValue("proposal", p);

		}
		
		// -----------------
		getNegotiation().getContext().addUtt(this);
		// -----------------
	}
}
