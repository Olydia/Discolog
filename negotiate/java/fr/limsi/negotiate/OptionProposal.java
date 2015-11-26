package fr.limsi.negotiate;

public class OptionProposal extends Proposal {
   
   public final Option option;
   
   protected OptionProposal (boolean isSelf, Option option) {
      super(isSelf);
      this.option = option;
   }
}
