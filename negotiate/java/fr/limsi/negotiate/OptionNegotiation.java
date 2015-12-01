package fr.limsi.negotiate;

import java.util.*;

/**
 * Class to define the mental state of the agent on preferences <b>between criteria</b> for the negotiation 
 * (ie. its preferences and user preferences given by the dialogue).
 * 
 * TODO usage
 * 
 */

public class OptionNegotiation {
   
   public OptionPreferenceModel self,other;       
   
   public OptionNegotiation () {
	   
	   self = new OptionPreferenceModel();
	   other = new OptionPreferenceModel();
   }

private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public void setSelfPreferences(OptionPreferenceModel selfPref) {
	   self = selfPref;
   }
   
}
