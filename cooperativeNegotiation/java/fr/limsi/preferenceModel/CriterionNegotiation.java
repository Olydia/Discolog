package fr.limsi.preferenceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.limsi.preferenceModel.Statement.Satisfiable;
import fr.limsi.preferenceModel.Proposal.Status;

public class CriterionNegotiation<C extends Criterion> {
	private Self<C> self;
	private Other<C> other;
	private Class<C> type; 
	private List<Statement<C>> selfStatements;
	private ArrayList<CriterionProposal> proposals;

	public CriterionNegotiation(Self<C> selfPreferences) {
		this.self = selfPreferences;
		this.other = new Other<C>(type);
		this.proposals = new ArrayList<CriterionProposal>();
		this.selfStatements = new ArrayList<Statement<C>>();
	}

	public void propose(CriterionProposal p){
		this.proposals.add(p);
	}
	
	public void updateStatement(Statement<C> s){
		this.selfStatements.add(s);
	}
	
	public void updateStatements(C value, Satisfiable s) {
		this.selfStatements.add(new Statement<C>(value, s));
	}
	
	public void addInOther(C value ,Satisfiable s){
		other.addPreference(value, s);
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

	public Other<C> getOther() {
		return other;
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
	public List<C> getElements (){
		return Arrays.asList(this.type.getEnumConstants());
	}

	public List<Statement<C>> getSelfStatements() {
		return selfStatements;
	}

	public void setSelfStatements(List<Statement<C>> selfStatements) {
		this.selfStatements = selfStatements;
	}
	
	

}	


