package fr.limsi.negotiate;
/**
 * 
 * @author Charles rich
 * 	Option proposal
 */

public class OptionProposal extends Proposal {
   
   public final Option option;
   
   protected OptionProposal (boolean isSelf, Option option) {
      super(isSelf);
      this.option = option;
   }
}
