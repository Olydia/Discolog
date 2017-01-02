package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.NegotiationMove;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.Statement;
import fr.limsi.negotiate.NegoUtterance;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

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
		
		NegotiationMove prop = new NegotiationMove(p, getExternal(), NegoUtterance.UtType.PROPOSE);
		getNegotiation().getContext().addUtt(prop);
		
		getNegotiation().addProposal(p);
		getNegotiation().addStatement(new Statement<Criterion>((Criterion)p.getValue(),Satisfiable.TRUE), getExternal());



	}
}