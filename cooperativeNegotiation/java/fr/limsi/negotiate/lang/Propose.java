package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
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
		
		Proposal p;
		if(getProposal() instanceof CriterionProposal){
			p = new CriterionProposal(!getExternal(), (Criterion)getProposal().getValue());
			getNegotiation().addStatement(new Statement<Criterion>((Criterion)p.getValue(),Satisfiable.TRUE), getExternal());
		}
		else 
			p = new OptionProposal(!getExternal(), (Option)getProposal().getValue());
		
		
		NegotiationMove prop = new NegotiationMove(p, getExternal(), NegoUtterance.UtType.PROPOSE);
		getNegotiation().getContext().addUtt(prop);
		
		getNegotiation().addProposal(p);



	}
}