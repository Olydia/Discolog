package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;

public class CriterionNegotiation<C extends Criterion> {
	private Self_Ci<C> self;
	private Other<C> other;
	private Class<C> type; 
	private List<Statement<C>> selfStatements;
	private ArrayList<CriterionProposal> proposals;

	public CriterionNegotiation(Self_Ci<C> selfPreferences) {
		this.self = selfPreferences;
		this.other = new Other<C>(type);
		this.proposals = new ArrayList<CriterionProposal>();
		this.selfStatements = new ArrayList<Statement<C>>();
		this.type = selfPreferences.getType();
	}

	public float acceptability(C value, double self){
		return (float) ((self*getSelf().satisfaction(value)) + ((1-self)*getOther().other(value)));
	}
	
	public Criterion chooseValue(List<C> V, final double self){
		V.sort(new Comparator<C>() {
			@Override
			public int compare(C c1, C c2){
				return Float.compare(acceptability(c2, self), acceptability(c1, self));
			}
		});
		return V.get(0);
	}
	
	public void propose(CriterionProposal p){
		this.proposals.add(p);
	}
	
	public void updateProposal(CriterionProposal proposal){

		for(CriterionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())){
				int index = proposals.indexOf(c);
				proposals.set(index, proposal);

			}	
		}
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
	

	public Self_Ci<C> getSelf() {
		return self;
	}

	public void setSelf(Self_Ci<C> self) {
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
	
	public boolean isStated(C value, boolean isSelf){
		
		if(isSelf && selfStatements.contains(new Statement<C>(value)))
			return true;
		else if(!isSelf && other.getStatus(value)!= Satisfiable.UNKOWN)
			return true;
		return false;
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
	/** returns the set of values that did not occur during the negotiation (not stated, not rejected nor accepted)
	 *  
	 */
	public List<C> remainProposals (){
		List<C> values = new ArrayList<C>();
		for (C elem: getElements()){
			if(!isAccepted(elem) && !isRejected(elem))
				values.add(elem);
		}
		return values;
	}
	public List<C> remainValues(){
		List<C> values = new ArrayList<C>();
		for (C elem: getElements()){
			if(!isAccepted(elem) && !isStated(elem, true))
				values.add(elem);
		}
		return values;
	}

}	


