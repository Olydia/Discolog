package fr.limsi.negotiate;


public class RejectMove extends NegotiationMove{

	Object other;
	public RejectMove(Proposal proposal, Object other, boolean external, UtType type) {
		super(proposal, external, type);
		// TODO Auto-generated constructor stub
		this.other = other;
	}
	
	public Object getOther(){
		return this.other;
	}

}
