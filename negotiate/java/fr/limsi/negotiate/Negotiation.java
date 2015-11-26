package fr.limsi.negotiate;

import java.util.*;

public class Negotiation<O extends Option> {
   
   private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public final List<CriterionNegotiation<Criterion>> criteria;
   
   public Negotiation (CriterionNegotiation<Criterion>[] criteria) {
      this.criteria = Arrays.asList(criteria);
   }
   /* TODO 
    * Ajouter la liste de preferences sur les criteres 
    * **/
    

	public O getPreferredOption() {
		// 1. sans regarder dans les proposals, mais seulement dans negociation
		
		/* générer la matrice, pondérer avec les prefs (negociation.criteria), calculer le meilleur */
		// --> il manque l'ordre des critères, qu'il faut rajouter dans les prefs
		
		// 2. en regardant les proposals
		return null;
	}

}
