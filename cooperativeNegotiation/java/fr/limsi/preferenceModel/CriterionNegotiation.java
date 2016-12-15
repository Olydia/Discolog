package fr.limsi.preferenceModel;

import java.util.ArrayList;

import fr.limsi.preferenceModel.NegotiatedPreferences.Satisfiable;
import fr.limsi.preferenceModel.Proposal.Status;

public class CriterionNegotiation<C> {
	private Self<C> self;
	private NegotiatedPreferences<C> other;
	private NegotiatedPreferences<C> oas;
	private Class<C> type; 
	private ArrayList<CriterionProposal> proposals;

	public CriterionNegotiation(Self<C> selfPreferences) {
		this.self = selfPreferences;
		this.other = new NegotiatedPreferences<C>(type);
		this.oas = new NegotiatedPreferences<C>(type);
		this.proposals = new ArrayList<CriterionProposal>();
	}

	public void propose(CriterionProposal p){
		this.proposals.add(p);
	}
	public void addInOther(C value ,Satisfiable s){
		other.addPreference(value, s);
	}
	public void addInOAS(C value ,Satisfiable s){
		oas.addPreference(value, s);
	}

	public Self<C> getSelf() {
		return self;
	}

	public void setSelf(Self<C> self) {
		this.self = self;
	}

	public Class<C> getType() {
		return type;
	}

	public void setType(Class<C> type) {
		this.type = type;
	}

	public NegotiatedPreferences<C> getOther() {
		return other;
	}

	public NegotiatedPreferences<C> getOas() {
		return oas;
	}

	public ArrayList<CriterionProposal> getProposals() {
		return proposals;
	}


	private boolean isStatus(C value, Status status) {
		for(CriterionProposal proposal: proposals){
			if(proposal.getValue().equals(value) && proposal.getStatus().equals(status))
				return true;
		}
		return false;
	}

	public boolean isAccepted(C value){
		return isStatus(value, Status.ACCEPTED);
	}

	public boolean isRejected(C value){
		return isStatus(value, Status.REJECTED);		
	}
}	


