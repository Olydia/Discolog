package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;

public class RejectPropose  extends ProposalUtterance {

	public static TaskClass CLASS;

	// for TaskClass.newStep
	public RejectPropose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(RejectPropose.class, disco, decomp, name, repeat);
	}

	public RejectPropose (Disco disco, Boolean external, Proposal rejected, Proposal proposal) { 
		super(RejectPropose.class, disco, external, proposal);
		if(rejected!= null) setSlotValue("rejected", rejected);
	}

	public Proposal getReject () { return (Proposal) getSlotValue("rejected"); }

	@Override
	protected void interpret () {
		
		// add the rejected proposal
		super.rejectUpdate(getReject());
		
		// add the counter proposal
		Proposal p =super.proposeUpdate(getProposal());
		
		// history update
		ProposalMove prop = new ProposalMove(p, getReject(), getExternal(), NegoUtterance.UtType.REJECTPROPOSE);
		// -----------------
		getNegotiation().getContext_bis().addUtt(this);
		// -----------------
	}
}
