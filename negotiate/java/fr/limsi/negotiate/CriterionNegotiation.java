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

	private CriterionPrefModel<C> self = new CriterionPrefModel<C>();
	CriterionPrefModel<C> other = new CriterionPrefModel<C>();
	CriterionPrefModel<C> oas = new CriterionPrefModel<C>();      
	public Class<C> criterionType ; 
	private  List<CriterionProposal> proposals = new ArrayList<CriterionProposal>();

	public List<CriterionProposal> getProposals() {
		return proposals;
	}

	public CriterionNegotiation (Class<C> type) {
		criterionType = type;
		self.setType(type);
		other.setType(type);
		oas.setType(type);
	}

	public Class<C> getCriterionType() {
		return criterionType;
	}
	
	// return a value which is in "in" but not in "out"
	public ValuePreference<C> getPreference(CriterionPrefModel<C> in, CriterionPrefModel<C> out){
		for (ValuePreference<C> value: in.getPreferences()){
			if(!out.getPreferences().contains(value))
				return value;
		}
		return null;
	}
	
	public C getTheCurrentMostPreffered(){
		List<C> values = getSelf().getValues();
		ArrayList<Integer> newScores = clearRejected(values, getSelf().getPreferencesValues());
		int mostPref = maxIndex(newScores);
		return( values.get(mostPref));
	}

	public ArrayList<Integer> clearRejected(List<C> values, ArrayList<Integer> cScores) {
		for ( CriterionProposal c: proposals) {
			if(c.status.equals(Proposal.Status.REJECTED)) {
				cScores.set(values.indexOf(c.criterion), Collections.min(cScores));
			}
		}
		return cScores;
	}
	

	private static final int maxIndex(ArrayList<Integer> a) {
		int imax=0;
		for(int i=1;i<a.size();i++)
			if (a.get(i)>a.get(imax))
				imax = i;
		return imax;
	}
	
	private static final int minIndex(ArrayList<Integer> a) {
		int imin = a.get(0);
		for(int i=1;i<a.size();i++)
			if (a.get(i)<=a.get(imin))
				imin = i;
		return imin;
	}
	
	public void propose (CriterionProposal proposal) { 
		if(! proposals.contains(proposal))
			proposals.add(proposal);
		else 
			updateProposal(proposal, Status.OPEN);}

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

	public void addOther(C less, C more) {
		other.add(less,more);
	}
	
	public void addOAS(C less, C more) {
		oas.add(less, more);
	}
	
	public CriterionPrefModel<C> getOas() {
		return oas;
	}

	public boolean isProposed(CriterionProposal proposal, Status status){
		Criterion cr = proposal.getValue() ;
		for (CriterionProposal p: proposals)
			if (p.getValue().equals(cr) && p.getStatus().equals(status))
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
	
	public List<Criterion> getProposals(Proposal.Status status){
		List<Criterion> prop = new ArrayList<Criterion>();
		for (CriterionProposal p: proposals){
			if(p.getStatus().equals(status))
				prop.add(p.getValue());
		}
		return prop;
	}
	
	public void printMentalState() {
		System.out.println(" **** SELF preferences *** \n \n");
		System.out.println(self);
		System.out.println("\n \n*** OTHER preferences *** \n \n");
		System.out.println(other);
		System.out.println("\n \n*** OTHER-ABOUT-SELF preferences *** \n \n");
		System.out.println(oas);
		System.out.println("\n \n*** Dialogue context (proposals) *** \n \n");
		for(CriterionProposal c : proposals)
			System.out.print(c.print() + "|");
	}
	

	public void updateProposal(CriterionProposal proposal, Proposal.Status status){
		
		for(CriterionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				c.setStatus(status);
				//c.setIsSelf(isSelf);
			}
				
		}
	}




}
