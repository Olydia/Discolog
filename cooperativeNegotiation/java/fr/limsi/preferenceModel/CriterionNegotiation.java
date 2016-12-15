package fr.limsi.preferenceModel;

import java.util.ArrayList;

public class CriterionNegotiation<C> {
	ArrayList<CriteriaPreferences<C>> self;
	ArrayList<CriterionProposal> proposals;
	// Other + Oas 

	public CriterionNegotiation(ArrayList<CriteriaPreferences<C>> selfPreferences) {
		this.self = selfPreferences;
		this.proposals = new ArrayList<CriterionProposal>();
	}

}
