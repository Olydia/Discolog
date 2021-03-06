package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.restaurant.Cuisine;
/**
 * This class defines the negotiation on a defined option (for example Restaurant) 
 * To negotiate we defined the list of propositions on table Proposal
 * 
 * 
 */

public class Negotiation<O extends Option> {

	private  List<OptionProposal> proposals;

	public void propose (OptionProposal proposal) { 
		if(!proposals.contains(proposal))
			proposals.add(proposal); 
		updateProposal(proposal, Status.OPEN);}

	public List<OptionProposal> getProposals() {
		return proposals;
	}

	public  List<CriterionNegotiation<Criterion>> criteriaNegotiation;

	public CriteriaClassPrefModel<O> criteriaPreferences; 


	public Negotiation (CriterionNegotiation<Criterion>[] criteriaNegotiation, CriteriaClassPrefModel<O> criteriaPreferences) {
		this.criteriaNegotiation = Arrays.asList(criteriaNegotiation);
		this.criteriaPreferences = criteriaPreferences;
		this.proposals = new ArrayList<OptionProposal>();
	}


	public CriterionNegotiation<Criterion> getCriterionNegotiation(Class<? extends Criterion> c){
		for (CriterionNegotiation<Criterion> cn: criteriaNegotiation){
			Class<? extends Criterion> cnType = cn.getCriterionType();
			if (cnType.equals(c))
				return cn;
		}
		return null;
	}

	public CriterionNegotiation<Criterion> getCriterionNegotiation( Criterion c){
		for (CriterionNegotiation<Criterion> cn: criteriaNegotiation){
			Class<? extends Criterion> cnType = cn.getCriterionType();
			if (cnType.equals(c.getClass()))
				return cn;
		}
		return null;
	}
	
	public O getPreferredOption(O firstOption, O secondOption) {

		int UtilityFirst = 0, UtilitySecond = 0;
		for (Class<? extends Criterion> c: firstOption.getCriteria()){
			// get the criterion rank 
			int rank = criteriaPreferences.getRank(c);
			// get the type of the criterion
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(c);
			// Utility = Sum(rank(c) * score(v_c)) 
			UtilityFirst += rank * criterion.getSelf().getScore(firstOption.getValue(c));
			UtilitySecond += rank * criterion.getSelf().getScore(secondOption.getValue(c));
		}
		// 2. en regardant les proposals
		return(UtilityFirst < UtilitySecond? secondOption: firstOption);
	}

	// Methods of the mental state

	public boolean isInOther(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (more, less);
		Class<? extends Criterion> c = (more == null ? less.getClass(): (less == null ? more.getClass(): null));
		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (cn.getOther().getPreferencesValues().contains(p));
	}

	public boolean isInself(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (more, less);
		Class<? extends Criterion> c = (more == null ? less.getClass(): (less == null ? more.getClass(): null));
		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (cn.getSelf().getPreferencesValues().contains(p));
	}

	public boolean isInOAS(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (more, less);
		Class<? extends Criterion> c = (more == null ? less.getClass(): (less == null ? more.getClass(): null));
		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (cn.getOas().getPreferencesValues().contains(p));
	}

	public void updateProposal(OptionProposal proposal, Status status){

		for(OptionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				c.setStatus(status);
				//c.setIsSelf(value);
			}
		}
	}
	public void addCriterionProposal(CriterionProposal propose){
		CriterionNegotiation<Criterion> criterionNegotiation = getCriterionNegotiation(propose.criterion);
		// get the index of the criterionNegotiation of type
		int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
		criteriaNegotiation.get(indexList).propose(propose);
		// add the new proposal to the corresponding 	
	}

	public void updateOtherMentalState(Criterion more, Criterion less){

		Class <? extends Criterion>  type = (more != null? more.getClass() :
				(less !=null?less.getClass(): null ));
		if(type != null)
			this.getCriterionNegotiation(type).addOther(more, less);
	}

	public void updateOASMentalState(Criterion more, Criterion less){
		Class <? extends Criterion>  type = (more != null? more.getClass() :
			(less !=null?less.getClass(): null ));
		if(type != null)
		this.getCriterionNegotiation(type).addOAS(more, less);
	}

	public void printAllMentalState() {
		System.out.println("#### Options proposals ####");
		for(OptionProposal c : proposals)
			System.out.print(c.print() + "|");
			
			System.out.println(" \n \n #### Criteria mental model ####");	
		for(CriterionNegotiation<Criterion> c: criteriaNegotiation) {
			c.printMentalState();
			System.out.println();
		}
	}
	// Proposals methods 
	public boolean isProposed (Proposal proposal, Status status){

		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			return(criteriaNegotiation.get(indexList).
					isProposed((CriterionProposal) proposal, status));
		}

		if(proposal instanceof OptionProposal){
			for (OptionProposal p: proposals) {
				if(p.getValue().equals(proposal.getValue()) && p.getStatus().equals(status))
					return true;
			}
		}
		return false;
	}

	public void addProposal(Proposal proposal) {
		if (proposal instanceof CriterionProposal) 
			addCriterionProposal((CriterionProposal) proposal);

		if(proposal instanceof OptionProposal)
			this.propose((OptionProposal) proposal);
	}

	public void updateProposalStatus(Proposal proposal, Status status) {
		if (proposal instanceof CriterionProposal) {
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			criteriaNegotiation.get(indexList).
			updateProposal((CriterionProposal) proposal, status);		
		}
		if(proposal instanceof OptionProposal){
			updateProposal((OptionProposal)proposal, status);
		}


	}
	public Status checkStatus(OptionProposal p) {
		for(OptionProposal prop : proposals) {
			if(prop.getValue().equals(p.getValue()))
				return prop.status;	
		}
		return null;
	}

	public Status checkProposalStatus (Proposal proposal) {
		Status status = null;
		if(proposal instanceof CriterionProposal){
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			status = criteriaNegotiation.get(indexList).checkStatus((CriterionProposal) proposal);
			//
		}

		if(proposal instanceof OptionProposal){
			status = this.checkStatus((OptionProposal) proposal);
		}

		if(status == null)  throw 
		new NullPointerException(proposal.toString() + " is not proposed yet");
		return status;
	}

	// testing functions 

	public CriterionProposal criterionProposal(Criterion c){
		return (new CriterionProposal(true, c));
	}

	public OptionProposal optionProposal(Option o){
		return (new OptionProposal(true, o));

	}
	
	public Criterion mostPreferredCriterion (Class <? extends Criterion> criterion) {
		
		return (getCriterionNegotiation(criterion).getTheCurrentMostPreffered());
	}

}
