package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class RejectPropose  extends Reject {

	public static TaskClass CLASS;

	// for TaskClass.newStep
	public RejectPropose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(disco, decomp, name, repeat);
	}

	public RejectPropose (Disco disco, Boolean external, Proposal proposal, Proposal counter) { 
		super(disco, external, proposal);
		if(counter!= null) setSlotValue("counter", counter);
	}

	public Proposal getCounter () { return (Proposal) getSlotValue("counter"); }

	@Override
	protected void interpret () {
		super.rejectUpdate();
		// add the new proposal
		Proposal p;
		if(getCounter() instanceof CriterionProposal){
			p = new CriterionProposal(!getExternal(), (Criterion)getCounter().getValue());
			getNegotiation().addStatement(new Statement<Criterion>((Criterion)p.getValue(),Satisfiable.TRUE), 
					getExternal());
		}
		else 
			p = new OptionProposal(!getExternal(), (Option)getCounter().getValue());
		
		
		RejectMove prop = new RejectMove(getProposal(), p, getExternal(), NegoUtterance.UtType.REJECTPROPOSE);
		getNegotiation().getContext().addUtt(prop);
		
		getNegotiation().addProposal(p);

	}
}
