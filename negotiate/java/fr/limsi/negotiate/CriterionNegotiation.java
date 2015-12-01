package fr.limsi.negotiate;

import java.util.*;
/**
 * Class to define the mental state of the agent on preferences and the dialogue (proposals in dialogue) 
 * (ie. its preferences and user preferences)
 * 
 */

public class CriterionNegotiation<C extends Criterion> {
   
   public PreferenceModel<C> self,other;       
   public Class<C> criterionType ; 
   
   public CriterionNegotiation (Class<C> type) {
	   criterionType = type;
	   self = new PreferenceModel<C>();
	   other = new PreferenceModel<C>();
   }
   

   public Class<C> getCriterionType() {
	return criterionType;
}


private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public void setSelfPreferences(PreferenceModel<C> selfPref) {
	   self = selfPref;
   }
   
}
