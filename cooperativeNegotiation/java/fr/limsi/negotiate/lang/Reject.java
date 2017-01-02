package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;

import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

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
		NegotiationMove rej = new NegotiationMove(getProposal(), getExternal(), NegoUtterance.UtType.REJECT);
		getNegotiation().getContext().addUtt(rej);

		if(getProposal() instanceof CriterionProposal){
			Criterion value = (Criterion) getProposal().getValue();
			CriterionProposal p = new CriterionProposal(!getExternal(), value);
			p.setStatus(Status.REJECTED);
			CriterionNegotiation<Criterion>cn =getNegotiation().getValueNegotiation(value.getClass());
			cn.updateProposal((CriterionProposal)getProposal());
			getNegotiation().addStatement(new Statement<Criterion>(value,Satisfiable.FALSE), getExternal());
		}
		if(getProposal() instanceof OptionProposal){
			OptionProposal p = new OptionProposal(!getExternal(), (Option)getProposal().getValue());
			p.setStatus(Status.REJECTED);
			getNegotiation().updateProposal(p);
		}
	}
}
