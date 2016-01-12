package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;

/**
 * Class to define the mental state of the agent on preferences about a <b>criterion</b> 
 * (ie. its preferences and user preferences given by the dialogue).
 * 
 * First, you have to define an Enum that implements Criterion. For example:
 * 
 * Enum Cost implements Criterion {
 * 		EXPENSIVE, CHEAP;
 * }
 * 
 * Then, you can instantiate a CriterionNegotiation on this Criterion:
 * 
 * CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
 * 
 * Note that the type for the generic (Cost in our example) must also be given as parameter (due to Java limitations).
 *
 * This CriterionNegotiation instance will be use as an element of the Negotiation object (@see Negotiation)
 * 
 */

public class CriterionNegotiation<C extends Criterion> {

	CriterionPrefModel<C> self,other,oas;       
	public Class<C> criterionType ; 
	private  List<CriterionProposal> proposals = new ArrayList<CriterionProposal>();

	public CriterionNegotiation (Class<C> type) {
		criterionType = type;
		self = new CriterionPrefModel<C>();
		other = new CriterionPrefModel<C>();
		oas = new CriterionPrefModel<C>();
	}

	public Class<C> getCriterionType() {
		return criterionType;
	}
	public C getTheCurrentMostPreffered(){
		@SuppressWarnings("unchecked")
		ArrayList<C> values = (ArrayList<C>) self.getValues();
		ArrayList<Integer> newScores = clearRejected(values, self.getPreferences());
		int mostPref = Collections.max(newScores);
		return( values.get(mostPref));
	}


	public ArrayList<Integer> clearRejected(ArrayList<C> values, ArrayList<Integer> cScores) {
		for ( CriterionProposal c: proposals) {
			if(c.status.equals(Proposal.Status.REJECTED)) {
				cScores.set(values.indexOf(c.criterion), Collections.min(cScores));
			}
		}
		return cScores;
	}
	public void propose (CriterionProposal proposal) { proposals.add(proposal); }

	public void setSelfPreferences(CriterionPrefModel<C> selfPref) {
		self = selfPref;
	}

}
