package fr.limsi.negotiate;

import java.util.*;

public class CriterionNegotiation<C extends Criterion> {
   
   public PreferenceModel<C>
         self = new PreferenceModel<C>(),
         other = new PreferenceModel<C>();
   
   private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public void setSelfPreferences(PreferenceModel<C> selfPref) {
	   self = selfPref;
   }
}
