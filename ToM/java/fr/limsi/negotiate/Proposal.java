package fr.limsi.negotiate;
/**
 * 
 * define  propositions made  during dialogue. For each proposition, we define its status for the begining open, counter represent the
 * counter proposition that the other interlocutor may do and finally isSelf is true if the proposition is made by the agent
 *
 */
public abstract class Proposal {

	
	
	protected Status status = Status.OPEN;
	// the flag is about who proposed the utterance 
	public boolean isSelf;

	protected Proposal (boolean isSelf) {
		this.isSelf = isSelf;
	}
	
	protected Proposal () {
		this.isSelf = false;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean value){
		this.isSelf = value;

	}
	
	public void setStatus (Status status) {
		this.status = status;
	}

	public Status getStatus () { return status; }
	
	// Needed in the model of ToM to generate the inverse of proposals
	public abstract Proposal mirrorProposal();

		
	public abstract Object getValue();
	
	public abstract String printValue();
	public abstract String printProposal(String topic) ;
	
	public static enum Status { OPEN, REJECTED, ACCEPTED }

}
