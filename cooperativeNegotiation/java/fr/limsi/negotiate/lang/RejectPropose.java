package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Proposal;

public class RejectPropose extends NegotiationUtterance {


	public RejectPropose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(Propose.class, disco, decomp, name, repeat);
	}

	public RejectPropose (Disco disco, Boolean external, Proposal proposal, Proposal rejected) { 
		super(Propose.class, disco, external);
		
		if(proposal != null) setSlotValue("proposal", proposal);
		if(rejected != null) setSlotValue("rejected", rejected);

	}
	
	public Proposal getRejectedProposal(){ return (Proposal) getSlotValue("rejected");}
	public Proposal getProposal () { return (Proposal) getSlotValue("proposal"); }

	
	@Override
	public void interpret () {
//		new Reject(getDisco(), getExternal(), getRejectedProposal());
//		new Propose(getDisco(), getExternal(), getProposal());
		System.out.println("success");
	}

}
