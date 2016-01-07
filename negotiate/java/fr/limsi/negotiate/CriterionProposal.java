package fr.limsi.negotiate;


/**
 * 
 * @author Charles rich
 * 	Criterion proposal
 */ 
public class CriterionProposal extends Proposal {
   
   public final Criterion criterion;
   
   protected CriterionProposal (boolean isSelf, Criterion criterion) {
      super(isSelf);
      this.criterion = criterion;
   }

public Criterion getCriterion() {
	return criterion;
}
   

}
