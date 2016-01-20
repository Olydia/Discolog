package fr.limsi.negotiate;


/**
 * 
 * @author Charles rich
 * 	Criterion proposal
 */ 
public class CriterionProposal extends Proposal {
   
   @Override
	public String toString() {
		return criterion.toString();
	}

public final Criterion criterion;
   
   public CriterionProposal (boolean isSelf, Criterion criterion) {
      super(isSelf);
      this.criterion = criterion;
   }

public Criterion getCriterion() {
	return criterion;
}

@Override
Criterion getValue() {
	return criterion;
}
   

}
