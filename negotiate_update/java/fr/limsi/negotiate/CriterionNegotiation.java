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
		List<C> values =(this.getSelf().sortCriteria());
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
		List<C> newScores = getSelf().acceptableCriteria(dom);
		newScores = clearRejected(newScores);
		if(newScores.isEmpty())
			return this.getMostPreffered();
		return(newScores.get(0));
	}

	/**
	 * 
	 * @param dominance value
	 * @return a list of proposals that can be proposed in the dialogue
	 */
	public List<CriterionProposal> computeProposal(int dom, DialogueContext context){
		// dominant only cares about his preferences

		List<C> otherAcceptable = this.getOther().acceptableCriteria(-dom);
		List<CriterionProposal>  proposals = new ArrayList<CriterionProposal>();
		if(dom > 0){
			List<C> selfAcc = getSelf().acceptableCriteria(dom);
			for(C cr: selfAcc){
				CriterionProposal p =new CriterionProposal(true, cr);
				if(!context.isProposed(p))
					proposals.add(p);
			}
			return proposals;
		}
		else {
			for(C pref: otherAcceptable){
				CriterionProposal prop = new CriterionProposal(pref);
				if(!(isProposed(prop, Status.ACCEPTED) || isProposed(prop, Status.REJECTED)) 
						&& getSelf().isAcceptable(pref, dom))
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

	public void addOther(C criterion, Boolean isLikable) {
		this.getOther().addValue(criterion, isLikable);
	}

	public void addOAS(C criterion, Boolean isLikable) {
		getOas().addValue(criterion, isLikable);
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

	public C getnewStatement(){
		for(C c: getSelf().sortCriteria()){
			if(!getOas().getAcceptableValues().contains(c) &&
					!getOas().getNonAcceptableValues().contains(c))
				return c;
		}
		return getSelf().getMostPreferred();
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
