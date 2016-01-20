package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.restaurant.Restaurant;

/**
 * This class defines the negotiation on a defined option (for example Restaurant) 
 * To negotiate we defined the list of propositions on table Proposal
 * 
 * 
 */

public class Negotiation<O extends Option> {

	private  List<OptionProposal> proposals;

	public void propose (OptionProposal proposal) { proposals.add(proposal); }

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

	public void updateProposal(OptionProposal proposal, Status status, Boolean value ){

		for(OptionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				c.setStatus(status);
				c.setIsSelf(value);
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

		Class <? extends Criterion>  type = (more != null? more.getClass() : (less !=null?less.getClass(): null ));
		if(type != null)
			this.getCriterionNegotiation(type).addOther(more, less);
	}

	public void updateOASMentalState(Criterion more, Criterion less){
		Class <? extends Criterion> type =more.getClass();
		this.getCriterionNegotiation(type).addOAS(more, less);
	}

	public void printAllMentalState() {
		for(CriterionNegotiation<Criterion> c: criteriaNegotiation) {
			c.printMentalState();
			System.out.println();
		}
	}

	public boolean isProposed (Proposal proposal){

		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			return(criteriaNegotiation.get(indexList).
					isProposed((CriterionProposal) proposal));
		}

		if(proposal instanceof OptionProposal){
			for (OptionProposal p: proposals) {
				if(p.getValue().equals(proposal.getValue()))
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

	public void updateProposalStatus(Proposal proposal, Status status, boolean isSelf) {
		if (proposal instanceof CriterionProposal) {
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			criteriaNegotiation.get(indexList).
			updateProposal((CriterionProposal) proposal, status, isSelf);		
		}
		if(proposal instanceof OptionProposal){
			updateProposal((OptionProposal)proposal, status, isSelf);
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
	
	public CriterionProposal criterionProposal(Criterion c, boolean isSelf){
		return (new CriterionProposal(isSelf, c));
	}
	
	public OptionProposal optionProposal(Option o, boolean isSelf){
		return (new OptionProposal(isSelf, o));
		
	}
}
