package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class RejectPropose  extends ProposalUtterance {

	public static TaskClass CLASS;

	// for TaskClass.newStep
	public RejectPropose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(RejectPropose.class, disco, decomp, name, repeat);
	}

	public RejectPropose (Disco disco, Boolean external, Proposal proposal, Proposal counter) { 
		super(RejectPropose.class, disco, external, proposal);
		if(counter!= null) setSlotValue("counter", counter);
	}

	public Proposal getCounter () { return (Proposal) getSlotValue("counter"); }

	@Override
	protected void interpret () {
		
		// add the rejected proposal
		super.rejectUpdate();
		
		// add the counter proposal
		Proposal p =super.proposeUpdate(getCounter());
		
		// history update
		ProposalMove prop = new ProposalMove(getProposal(), p, getExternal(), NegoUtterance.UtType.REJECTPROPOSE);
		getNegotiation().getContext().addUtt(prop);
		
		getNegotiation().addProposal(p);

	}
}
