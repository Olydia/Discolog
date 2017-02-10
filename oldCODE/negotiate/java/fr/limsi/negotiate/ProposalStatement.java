package fr.limsi.negotiate;

public class ProposalStatement extends Statement {

	private Proposal prop;
	public ProposalStatement(Proposal prop, String uttType) {
		
		super(!prop.isSelf, uttType);
		this.prop = prop;
		// TODO Auto-generated constructor stub
	}
	public Proposal getProp() {
		return prop;
	}


}
