package fr.limsi.negotiate;

import java.util.*;

/**
 * This class defines the negotiation on a defined option (for example Restaurant) 
 * To negotiate we defined the list of propositions on table Proposal
 * 
 * 
 */

public class Negotiation<O extends Option> {
   
   private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public final List<CriterionNegotiation<Criterion>> criteriaNegotiation;
   public final OptionNegotiation optionCriteria;

   //public List<Class<? extends Criterion>> criteria; 
   
   public Negotiation (CriterionNegotiation<Criterion>[] criteriaNegotiation, OptionNegotiation optionCriteria) {
      this.criteriaNegotiation = Arrays.asList(criteriaNegotiation);
      this.optionCriteria = optionCriteria;
   }
   /* TODO
    * Get the PreferenceMatrix
    *  PreferenceMAtrix => get the criterion note
    *  
    */
    
   public CriterionNegotiation<Criterion> getCriterionNegotiation(Class<? extends Criterion> c){
	   for (CriterionNegotiation<Criterion> cn: criteriaNegotiation){
		   Class<? extends Criterion> cnType = cn.getCriterionType();
		   String cnname = cnType.getSimpleName();
		   String cname = c.getSimpleName();
		   if (cnType.equals(c))
			   return cn;
	   }
	   return null;
   }
	public O getPreferredOption(O lessOption, O moreOption) {
		/**1. get the list of criteria from O
		 * 1 bis. prefCriteria = Calculer la liste des preferences pour les criteres
		 * 2. Parcourir PrefCriteria pour calculer la note de la valeur de ce critere pour les deux options
		 * 3. multiplier la note de la valeur du critere par le poid du critere
		 * 4. sommer
		 * 5. comparer
		 */
		int lessNote = 0, moreNote = 0;
		// generates here an ordered list of preferences 
		for (Class<? extends Criterion> c: lessOption.getCriteria()){
			PreferenceMatrix<Class<? extends Criterion>> optioncriterion = optionCriteria.self.generateMatrix(lessOption.getCriteria());
			int criterionNote = optioncriterion.getPreferenceOnValue(c);
			// get the criterionNegotiation which type is c.Class and generates the preference Matrix
			PreferenceMatrix<Criterion> matrix = getCriterionNegotiation(c).self.generateMatrix(Arrays.asList(c.getEnumConstants()));
			int lessnote = matrix.getPreferenceOnValue(lessOption.getValue(c));
			int morenote = matrix.getPreferenceOnValue(moreOption.getValue(c));
			lessNote += lessnote+(criterionNote);
			moreNote += morenote+(criterionNote);
			
		}
		// 2. en regardant les proposals
		return(lessNote<moreNote? moreOption: lessOption);
	}

}
