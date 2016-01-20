package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;


/**
 * Class to define the mental state of the agent on preferences about a <b>criterion</b> 
 * (ie. its preferences and user preferences given by the dialogue).
 * 
 * First, you have to define an Enum that implements Criterion. For example:
 * 
 * Enum Cost implements Criterion {
 * 		EXPENSIVE, CHEAP;
 * }
 * 
 * Then, you can instantiate a CriterionNegotiation on this Criterion:
 * 
 * CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
 * 
 * Note that the type for the generic (Cost in our example) must also be given as parameter (due to Java limitations).
 *
 * This CriterionNegotiation instance will be use as an element of the Negotiation object (@see Negotiation)
 * 
 */

public class CriterionNegotiation<C extends Criterion> {

	private CriterionPrefModel<C> self;
	CriterionPrefModel<C> other;
	CriterionPrefModel<C> oas;       
	public Class<C> criterionType ; 
	private  List<CriterionProposal> proposals = new ArrayList<CriterionProposal>();

	public List<CriterionProposal> getProposals() {
		return proposals;
	}

	public CriterionNegotiation (Class<C> type) {
		criterionType = type;
		setSelf(new CriterionPrefModel<C>());
		other = new CriterionPrefModel<C>();
		oas = new CriterionPrefModel<C>();
		self.setType(type);
		other.setType(type);
		oas.setType(type);
	}

	public Class<C> getCriterionType() {
		return criterionType;
	}
	
	public C getTheCurrentMostPreffered(){
		ArrayList<C> values = (ArrayList<C>) getSelf().getValues();
		ArrayList<Integer> newScores = clearRejected(values, getSelf().getPreferences());
		int mostPref = Collections.max(newScores);
		return( values.get(mostPref));
	}

	public ArrayList<Integer> clearRejected(ArrayList<C> values, ArrayList<Integer> cScores) {
		for ( CriterionProposal c: proposals) {
			if(c.status.equals(Proposal.Status.REJECTED)) {
				cScores.set(values.indexOf(c.criterion), Collections.min(cScores));
			}
		}
		return cScores;
	}
	
	public void propose (CriterionProposal proposal) { proposals.add(proposal); }

	public void setSelfPreferences(CriterionPrefModel<C> selfPref) {
		setSelf(selfPref);
	}

	public CriterionPrefModel<C> getSelf() {
		return self;
	}

	public void setSelf(CriterionPrefModel<C> self) {
		this.self = self;
	}

	public CriterionPrefModel<C> getOther() {
		return other;
	}

	public void addOther(C more, C less) {
		other.add(more,less);
	}
	
	public void addOAS(C more, C less) {
		oas.add(more,less);
	}
	
	public CriterionPrefModel<C> getOas() {
		return oas;
	}

	public boolean isProposed(CriterionProposal proposal){
		Criterion cr = proposal.getValue() ;
		for (CriterionProposal p: proposals)
			if (p.criterion.equals(cr))
				return true;
		
		return false;
	}
	
	public Status checkStatus(CriterionProposal p) {
		for(CriterionProposal prop : proposals) {
			if(prop.getValue().equals(p.getValue()))
				return prop.status;	
		}
		return null;
	}
	
	public void printMentalState() {
		System.out.println("SELF preferences");
		System.out.println(self);
		System.out.println("OTHER preferences");
		System.out.println(other);
		System.out.println("OTHER-ABOUT-SELF preferences");
		System.out.println(oas);
	}
	

	public void updateProposal(CriterionProposal proposal, Proposal.Status status, boolean isSelf){
		
		for(CriterionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				c.setStatus(status);
				c.setIsSelf(isSelf);
			}
				
		}
	}


}
