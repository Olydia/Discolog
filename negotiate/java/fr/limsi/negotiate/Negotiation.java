package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.restaurant.Cuisine;
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
   public List<Class<? extends Criterion>> criteria; 
   
   public Negotiation (CriterionNegotiation<Criterion>[] criteria) {
      this.criteriaNegotiation = Arrays.asList(criteria);
   }
   /* TODO
    * Get the PreferenceMatrix
    *  PreferenceMAtrix => get the criterion note
    *  
    */
    
   public CriterionNegotiation<? extends Criterion> getCriterionNegotiation(Class<? extends Criterion> c){
	   for (CriterionNegotiation<? extends Criterion> cn: criteriaNegotiation){
		   if (cn.getCriterionType().equals(c.getClass()))
			   return cn;
	   }
	   return null;
   }
	public O getPreferredOption(O lessOption, O moreOption) {
		int lessNote = 0, moreNote = 0;
		for (Class<? extends Criterion> c: criteria){
			Criterion less = lessOption.getValue(c);
			Criterion more = moreOption.getValue(c);
			// get the criterionNegotiation which type is c.Class and generates the preference Matrix
			PreferenceMatrix matrix = getCriterionNegotiation(c).self.generateMatrix(Arrays.asList(c.getEnumConstants()));
			int lessnote = matrix.getPreferenceOnValue(less);
			int morenote = matrix.getPreferenceOnValue(more);
			lessNote += lessnote*criteria.indexOf(c);
			moreNote += morenote*criteria.indexOf(c);
			
			
			
		}
		// get the type of criterionNegotiation
		// a partir de criterionNegotation on peut acceder a self pour generer la matrice de preference
		// a partir de la matrice de preference on calcule la note du critere pour les deux objets
		// je multiplie par le poid du critere � savoir l'index de ce dernier dans la liste
		// et je fais cette procedure pour tous les criteres de l'option
		// somme des notes des criters 
		
		// 1. sans regarder dans les proposals, mais seulement dans negociation
		
		/* générer la matrice, pondérer avec les prefs (negociation.criteria), calculer le meilleur */
		// --> il manque l'ordre des critères, qu'il faut rajouter dans les prefs
		
		// 2. en regardant les proposals
		return null;
	}

}
