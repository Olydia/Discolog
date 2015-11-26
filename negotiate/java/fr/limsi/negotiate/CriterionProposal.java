package fr.limsi.negotiate;

public class CriterionProposal extends Proposal {
   
   public final Criterion criterion;
   
   protected CriterionProposal (boolean isSelf, Criterion criterion) {
      super(isSelf);
      this.criterion = criterion;
   }
}
