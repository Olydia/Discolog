package fr.limsi.negotiate;


public class ProposalMove extends NegotiationMove{

	Object other;
	public ProposalMove(Proposal proposal, Object other, boolean external, UtType type) {
		super(proposal, external, type);
		// TODO Auto-generated constructor stub
		this.other = other;
	}
	
	public Object getOther(){
		return this.other;
	}

}
