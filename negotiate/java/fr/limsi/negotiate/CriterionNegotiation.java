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
			if(!this.isIn(out, value))
				return value;
		}
		return null;
	}
	
	public C getTheCurrentMostPreffered(){
		List<C> values = getSelf().getValues();
		ArrayList<Integer> newScores = new ArrayList<Integer>();
		newScores = clearRejected(values, getSelf().getPreferencesValues());

		int mostPref = maxIndex(newScores);
		return(values.get(mostPref));
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
	
//	private static final int minIndex(ArrayList<Integer> a) {
//		int imin = a.get(0);
//		for(int i=1;i<a.size();i++)
//			if (a.get(i)<=a.get(imin))
//				imin = i;
//		return imin;
//	}
//	
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
	
	public Status checkStatus(C p) {
		for(CriterionProposal prop : proposals) {
			if(prop.getValue().equals(p))
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
	public boolean isIn(CriterionPrefModel<C> model, ValuePreference<C> pref){
		ValuePreference<Criterion>leastPref = new ValuePreference<Criterion> (pref.getLess(), null);
		ValuePreference<Criterion>MostPref = new ValuePreference<Criterion> (null, pref.getMore());
		return (model.getPreferences().contains(pref) 
				||model.getPreferences().contains(MostPref)	
				||model.getPreferences().contains(leastPref));
		
	}
	public boolean isInOther(C less, C more) {
		ValuePreference<C> p = new ValuePreference<C> (less, more);
//		Class<? extends Criterion> c =  p.getType();
//		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (isIn(this.getOther(), p));
	}
	
	public boolean isInself(C less, C more) {
		ValuePreference<C> p = new ValuePreference<C> (less, more);
		return (isIn(this.getSelf(), p));
	}

	public boolean isInOAS(C less, C more) {
		ValuePreference<C> p = new ValuePreference<C> (less, more);
			return (isIn(this.getOas(),p));
		
	}
	public boolean isAcceptableCriterion(C c, int dom) {
		int bestScore = this.getSelf().getScore(this.getSelf().getMostPreferred());
		int proposalScore = this.getSelf().getScore(c);
		// R
		if (dom > 0)
			return (proposalScore>= bestScore *0.7);
		
		if (dom ==0)
			return( proposalScore>= 0);
		
		else {
			// if the proposal has already been proposed then accept it in the case of submissive agent
			//if(this.getOas().getValues())
			if(this.proposals.contains(c) && this.getOas().containsPrefabout(c))
				return true;
			return (proposalScore>= 0);

			
		}
		
	}
	/** take as input a criterion value and returns the agent preference on it
	  **/
	
	public Optional<ValuePreference<C>> reactToCriterion(C criterion){
		if(criterion.equals(this.getSelf().getMostPreferred()) && 
				!isInOAS(null, criterion))
			return Optional.of(new ValuePreference<C> (null, criterion));
		if(criterion.equals(this.getSelf().getLeastPreferred()) && 
				!isInOAS(criterion, null)){
			return Optional.of(new ValuePreference<C> (criterion, null));

		}
		else{
			List<C> criteria = this.getSelf().sortCriteria();
			for(int i = 0; i<criteria.indexOf(criterion); i++){
				if(!isInOAS(criterion, criteria.get(i)))
					return Optional.of(new ValuePreference<C>(criterion, criteria.get(i)));
			}
			for(int i = criteria.size()-1; i<criteria.indexOf(criterion) ; i--){
				if(!isInOAS(criteria.get(i), criterion))
					return  Optional.of(new ValuePreference<C>(criteria.get(i), criterion));
			}
		}	
		// Everything has been said 
		if (getPreference(getSelf(),getOas()) == null){
			// Etudier la relation
				return Optional.of(new ValuePreference<C>(null, getMostPreffered()));
		}
		else return Optional.empty();


	}

	public C getMostPreffered() {
		return this.getSelf().getMostPreferred();
	}
	
	public C getLeastPreffered() {
		return this.getSelf().getLeastPreferred();
	}

}
