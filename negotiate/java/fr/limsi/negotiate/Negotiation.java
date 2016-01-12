package fr.limsi.negotiate;

import java.util.*;

/**
 * This class defines the negotiation on a defined option (for example Restaurant) 
 * To negotiate we defined the list of propositions on table Proposal
 * 
 * 
 */

public class Negotiation<O extends Option> {
   
   private  List<OptionProposal> proposals;
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

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
	
	public void addProposal(CriterionProposal propose){
		Class<? extends Criterion> type = propose.criterion.getClass();
		CriterionNegotiation<Criterion> criterionNegotiation = getCriterionNegotiation(type);
		// get the index of the criterionNegotiation of type
		int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
		criteriaNegotiation.get(indexList).propose(propose);
		// add the new proposal to the corresponding 
		
		
		
	}

}
