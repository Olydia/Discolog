package fr.limsi.negotiate;

public class NegotiationMove extends NegoUtterance{
	private	Proposal proposal;
	
	public NegotiationMove(Proposal proposal, boolean external, UtType type) {
		super(external, type);
		this.setProposal(proposal);
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
}
