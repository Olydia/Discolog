package fr.limsi.negotiate;

public class NegotiationMove extends NegoUtterance{
	private	Proposal proposal;
	
	
	public NegotiationMove(Proposal proposal, boolean external, UtType type) {
		super(external, type);
		this.setProposal(proposal);
		if(proposal instanceof CriterionProposal){
			@SuppressWarnings("unchecked")
			Class<? extends Criterion> t = (Class<? extends Criterion>)proposal.getValue().getClass();
			this.setValueType(t);

		}
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return proposal;
	}

}
