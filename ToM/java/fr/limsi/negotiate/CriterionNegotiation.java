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

	
	public void setOther(Other<C> other) {
		this.other = other;
	}

	public void setProposals(ArrayList<CriterionProposal> proposals) {
		this.proposals = proposals;
	}


	public CriterionNegotiation(Self_Ci<C> selfPreferences) {
		this.type = selfPreferences.getType();
		this.self = selfPreferences;
		this.other = new Other<C>(type);
		this.proposals = new ArrayList<CriterionProposal>();
		this.selfStatements = new ArrayList<Statement<C>>();
		// test
	}
	
	public CriterionNegotiation(Self_Ci<C> selfPreferences, Other<C> other, Class<C> type, 
			ArrayList<CriterionProposal> proposals, List<Statement<C>> selfStatements) {
		this.type = type;
		this.self = selfPreferences;
		this.other = other;
		this.proposals = proposals;
		this.selfStatements = selfStatements;
		// test
	}

	public float tolerable(C value, double self){
		return (float) ((self*getSelf().satisfaction(value)) + ((1-self)*getOther().other(value)));
	}

	public Criterion chooseValue(List<C> V, final double self){
		if(V.isEmpty())
			return null;
		V.sort(new Comparator<C>() {
			@Override
			public int compare(C c1, C c2){
				return Float.compare(tolerable(c2, self), tolerable(c1, self));
			}
		});
		return V.get(0);
	}
	
	public List<C> getAcceptableValues(List<C> V, final double self){
		List<C> acc = new ArrayList<C> ();
		for(C value: V){
	
			if(isAcceptable(value, self))
				acc.add(value);	
		}
		
		return acc;
	}
	
	public List<C> acceptableValues(double self){
		List<C> values = getAcceptableValues(this.getElements(), self);
		
		values.sort(new Comparator<C> (){
			public int compare (C c1, C c2){
				return Float.compare(getSelf().satisfaction(c2), getSelf().satisfaction(c1));
			}
		});
		return values;
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

	public ArrayList<CriterionProposal> getProposalsWithStatus(Status status){
		ArrayList<CriterionProposal> props = new ArrayList<CriterionProposal>();
		for(int i=proposals.size()-1; i>=0; i--){
			CriterionProposal p = proposals.get(i);
			if(p.getStatus().equals(status))
				props.add(p);	
		}
		return props;
	}


	public ArrayList<CriterionProposal> getProposalsWithStatus(Status status, boolean isSelf){
		ArrayList<CriterionProposal> props = new ArrayList<CriterionProposal>();
		for(int i=proposals.size()-1; i>=0; i--){
			CriterionProposal p = proposals.get(i);
			if(p.getStatus().equals(status) && p.isSelf == isSelf)
				props.add(p);	
		}
		return props;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<C> getValusProposals(List<CriterionProposal> prop){
		ArrayList<C> values = new ArrayList<C>();
		for(CriterionProposal p: prop){
			values.add((C)p.getValue());
		}
		return values;
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
			if( !isRejected(elem))
				values.add(elem);
		}
		return values;
	}
	public List<C> remainValues(){
		List<C> values = new ArrayList<C>();
		for (C elem: getSelf().sortValues()){
			if(!isStated(elem, true) && !isRejected(elem))
				values.add(elem);
		}
		return values;
	}
	
		

	public boolean isAcceptable(C c, double self){
		 return getSelf().satisfaction(c)>= NegotiationParameters.beta * self;
	}
	
	public Criterion ask(){
		if(this.getOther().getPreferences().isEmpty())
			return null;
		
		
		List<C> otherUnkw = this.getOther().getPreferences(Satisfiable.UNKOWN);

		otherUnkw.sort(new Comparator<C> (){
				public int compare (C c1, C c2){
					return Float.compare(getSelf().satisfaction(c2), getSelf().satisfaction(c1));
				}
			});
			//}
			return otherUnkw.get(0);

	}

	public void clearNegotiation(){
		this.proposals.clear();
		this.selfStatements.clear();
		this.getOther().clearNegotiation();
	}
	
	
	public CriterionNegotiation<C> clone(){
		 Other<C> other = new Other<>(getType());
		 other.setPreferences(this.other.getPreferences());
		 List<Statement<C>> selfStatements = new ArrayList<Statement<C>>(this.getSelfStatements());
		 ArrayList<CriterionProposal> proposals = new ArrayList<CriterionProposal>(this.getProposals());
		 
		 return new CriterionNegotiation<C>(null, other, type, proposals, selfStatements);
		
	}
}	


