package fr.limsi.negotiate;

public class ProposalStatement extends Statement {

	private Proposal prop;
	public ProposalStatement(boolean external, String utteranceType, Proposal prop) {
		super(external, utteranceType);
		this.prop = prop;
		// TODO Auto-generated constructor stub
	}
	public Proposal getProp() {
		return prop;
	}


}
