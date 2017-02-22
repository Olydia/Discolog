package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.PreferenceStatement.Acceptable;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ProposalStatement;
import fr.limsi.negotiate.Proposal.Status;

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
		p.setStatus(Status.REJECTED);
		getNegotiation().updateProposal(p);
		getNegotiation().getContext().
		addStatement(new ProposalStatement(p, "Reject"));
		
		if(getProposal() instanceof CriterionProposal){
			if (getExternal()) 
				getNegotiation().updateOtherMentalState((Criterion) getProposal().getValue(), Acceptable.FALSE);
			else 
				getNegotiation().updateOASMentalState((Criterion)getProposal().getValue(), Acceptable.FALSE);
		}
	}
}