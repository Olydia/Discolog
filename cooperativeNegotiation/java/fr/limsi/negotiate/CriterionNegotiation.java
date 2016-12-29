package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

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

	public float acceptability(C value, double self){
		return (float) ((self*getSelf().satisfaction(value))+ ((1-self)*getOther().other(value)));
	}
	public void propose(CriterionProposal p){
		this.proposals.add(p);
	}
	public void addStatement(Statement<C> s, boolean external){
		if(external){
			this.addInOther(s.getValue(), s.getStatus());
		}
		else
			updateStatement(s);
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
	@SuppressWarnings("unchecked")
	public ArrayList<C> getProposalsWithStatus(Status status){
		ArrayList<C> props = new ArrayList<C>();
		for(CriterionProposal p: proposals){
			if(p.getStatus().equals(status))
				props.add((C) p.getValue());	
		}
		return props;
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


