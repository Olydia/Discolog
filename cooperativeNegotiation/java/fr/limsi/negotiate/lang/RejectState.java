package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.NegoUtterance.UtType;
import fr.limsi.negotiate.Statement.Satisfiable;



public class RejectState  extends ProposalUtterance {
	public static TaskClass CLASS;

	// for TaskClass.newStep
	public  RejectState(Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(RejectState.class, disco, decomp, name, repeat);
	}
	
	public RejectState(Disco disco, Boolean external, Proposal proposal, Criterion value) {
		super(RejectState.class, disco, external, proposal);
	    if ( value != null ) setSlotValue("value", value);

	}

	public Criterion getValue () { return (Criterion) getSlotValue("value"); }
	
	@Override
	public void interpret () {
		super.rejectUpdate();
		
		Statement<Criterion> statement = new Statement<Criterion>(getValue(),Satisfiable.FALSE);
		ProposalMove st = new ProposalMove(super.getProposal(), statement, getExternal(), UtType.REJECTSTATE);
		
		getNegotiation().getContext().addUtt(st);
		getNegotiation().addStatement(statement, getExternal());
	}
	

	
}
