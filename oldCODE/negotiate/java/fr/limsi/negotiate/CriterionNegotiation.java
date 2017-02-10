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

	// return a value which is in "in" but not in "out"
	public List<ValuePreference<C>> getSelectedPreferences(CriterionPrefModel<C> in, CriterionPrefModel<C> out){
		List<ValuePreference<C>> elems = new ArrayList<ValuePreference<C>>();
		List<C> values =sortListOfCriteria(this.getSelf().getValues());
		ValuePreference<C> mostPRef = new ValuePreference<C>(null, values.get(0));
		if(!this.isIn(out, mostPRef))
			elems.add(mostPRef);

		for (ValuePreference<C> value: in.getPreferences()){
			if(!this.isIn(out, value))
				elems.add(value);
		}
		return elems;
	}

	public C getTheCurrentMostPreffered(int dom){
		List<C> newScores = selfAcceptableCriteria(dom);
		newScores = clearRejected(newScores);
		if(newScores.isEmpty())
			return this.getMostPreffered();
		return(sortListOfCriteria(newScores).get(0));
	}

	public List<C> selfAcceptableCriteria(int dom){
		List<C> accepted = new ArrayList<C>();
		for(C criterion: self.getValues())
			if(isSelfAcceptableCriterion(criterion, dom))
				accepted.add(criterion);
		return accepted;
	}

	/** Calculates the acceptable values for the preference model
	 *  which can be either Other or OAS 
	 *  @return List of values that are acceptable**/ 
	public List<C> acceptableCriteria(int dom, CriterionPrefModel<C> model){
		List<C> accepted = new ArrayList<C>();
		for(C criterion: model.getValues())
			if(isAcceptableCriterion(criterion, dom, model))
				accepted.add(criterion);

		return sortListOfCriteria(accepted);
	}

	/**
	 * 
	 * @param dominance value
	 * @return a list of proposals that can be proposed in the dialogue
	 */
	public List<CriterionProposal> computeProposal(int dom, DialogueContext context){
		// dominant only cares about his preferences

		List<C> otherAcceptable = acceptableCriteria(-dom, this.getOther());
		List<CriterionProposal>  proposals = new ArrayList<CriterionProposal>();
		if(dom > 0){
			List<C> selfAcc = selfAcceptableCriteria(dom);
			for(C cr: selfAcc){
				CriterionProposal p =new CriterionProposal(true, cr);
				if(!context.isProposed(p))
					proposals.add(p);
			}
			return proposals;
		}
		else {
			for(C pref: sortListOfCriteria(otherAcceptable)){
				CriterionProposal prop = new CriterionProposal(pref);
				if(!(isProposed(prop, Status.ACCEPTED) || isProposed(prop, Status.REJECTED)) 
						&& isSelfAcceptableCriterion(pref, dom))
					proposals.add(new CriterionProposal(true,pref));
			}
			return proposals;
		}

	}
	public List<C> clearRejected(List<C> values) {
		for ( CriterionProposal c: proposals) {
			if(c.getStatus().equals(Proposal.Status.REJECTED)) {
				values.remove(c);
			}
		}
		return values;
	}

	public List<C> sortListOfCriteria (List<C> criteria){
		criteria.sort(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2){
				return (self.getScore(o2) - self.getScore(o1));
			}
		});
		return criteria;

	}


	//	private static final int maxIndex(ArrayList<Integer> a) {
	//		int imax=0;
	//		for(int i=1;i<a.size();i++)
	//			if (a.get(i)>a.get(imax))
	//				imax = i;
	//		return imax;
	//	}

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
			updateProposal(proposal);}

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
				return prop.getStatus();	
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

	public List<Criterion> getProposalswithoutStatus (Status status){
		List<Criterion> prop = new ArrayList<Criterion>();
		for (CriterionProposal p: proposals){
			if(!p.getStatus().equals(status))
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


	public void updateProposal(CriterionProposal proposal){

		for(CriterionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())){
				int index = proposals.indexOf(c);
				proposals.set(index, proposal);

			}	
		}
	}
	public boolean isIn(CriterionPrefModel<C> model, ValuePreference<C> pref){
		//		pref = (model.isPreferred(pref.getLess(), pref.getMore())? pref:
		//							new ValuePreference<C>(pref.getMore(), pref.getLess()));
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
	public boolean isSelfAcceptableCriterion(C c, int dom){
		if(dom>=0)
			return isAcceptableCriterion(c, dom, getSelf());
		else {
			// if the proposal has already been proposed then accept it in the case of submissive agent
			//if(this.getOas().getValues())
			if(this.proposals.contains(c) && this.getOas().containsPrefabout(c))
				return true;
			return ( getSelf().getScore(c)>= 0);


		}

	}
	public boolean isAcceptableCriterion(C c, int dom, CriterionPrefModel<C> model) {
		//int bestScore = model.getScore(model.getMostPreferred());
		int proposalScore = model.getScore(c);
		List<C> sortedCriteria = (self.sortCriteria());
		// R
		if (dom > 0)
			return (sortedCriteria.indexOf(c)< sortedCriteria.size() *.3);

		else
			return( proposalScore>= 0 );		
	}
	/** take as input a criterion value and returns the agent preference on it
	 **/

	public Optional<ValuePreference<C>> reactToCriterion(C criterion, List<C> negotiatedCriteria){
		if(criterion.equals(this.getSelf().getMostPreferred()))
			if(!isInOAS(null, criterion)|| negotiatedCriteria.contains(criterion))
				return Optional.of(new ValuePreference<C> (null, criterion));
		if(criterion.equals(this.getSelf().getLeastPreferred()))
			if(!isInOAS(criterion, null)|| negotiatedCriteria.contains(criterion))
			{
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
		//		if (getPreference(getSelf(),getOas()) == null){
		//			// Etudier la relation
		//				//return Optional.of(new ValuePreference<C>(null, getMostPreffered()));
		//			return Optional.empty();
		//		}
		return Optional.empty();


	}

	public C getMostPreffered() {
		return this.getSelf().getMostPreferred();
	}

	public C getLeastPreffered() {
		return this.getSelf().getLeastPreferred();
	}

}
