package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.NegoUtterance;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ProposalMove;
import fr.limsi.negotiate.Statement;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

public class AcceptPropose  extends ProposalUtterance {

	public static TaskClass CLASS;

	// for TaskClass.newStep
	public AcceptPropose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(AcceptPropose.class, disco, decomp, name, repeat);
	}

	public AcceptPropose (Disco disco, Boolean external, CriterionProposal accepted, Proposal proposal) { 
		super(AcceptPropose.class, disco, external, proposal);
		if(accepted!= null) setSlotValue("accepted", accepted);
	}

	public CriterionProposal getAccepted () { return (CriterionProposal) getSlotValue("accepted"); }

	@Override
	protected void interpret () {
		
		// add the proposal 
		super.proposeUpdate(getProposal());
		
		// add the accepted proposal
		Criterion value = (Criterion) getAccepted().getValue();
		CriterionProposal acc = new CriterionProposal(!getExternal(), value);
		acc.setStatus(Status.ACCEPTED);
		this.setSlotValue("accepted", acc);

		
		CriterionNegotiation<Criterion>cn =getNegotiation().getValueNegotiation(value.getClass());
		cn.updateProposal(acc);
		getNegotiation().addStatement(new Statement<Criterion>(value,Satisfiable.TRUE), getExternal());
		
		
		// history update

		// -----------------
		getNegotiation().getContext().addUtt(this);
		// -----------------

	}

	@Override
	public NegotiationUtterance mirrorCopy() {
		// TODO Auto-generated method stub
		return new AcceptPropose(getDisco(), !getExternal(), getAccepted(), getProposal());
	}
}