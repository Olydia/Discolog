package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;

public class MHypothesis {

	public MHypothesis() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param currentIndex: index for the recursive call
	 * @param preferences : for all Criteria the list of the different preferences
	 * @return the combination of all the possible set of preferences for a given topic
	 */
	
	
	public List<List<SatCriterion>> getCombination(int currentIndex,  List<List<SatCriterion>> preferences) {

		if (currentIndex == preferences.size()) {
			// Skip the items for the last container
			List<List<SatCriterion>> combinations = new ArrayList<List<SatCriterion>>();
			combinations.add(new ArrayList<SatCriterion>());
			return combinations;
		}

		List<List<SatCriterion>> combinations = new ArrayList<List<SatCriterion>>();
		List<SatCriterion> container = preferences.get(currentIndex);
		List<SatCriterion> containerItemList = container;

		// Get combination from next index
		List<List<SatCriterion>> suffixList = getCombination(currentIndex + 1, preferences);
		int size = containerItemList.size();

		for (int ii = 0; ii < size; ii++) {
			SatCriterion containerItem = containerItemList.get(ii);

			if (suffixList != null) {

				for (List<SatCriterion> suffix : suffixList) {
					List<SatCriterion> nextCombination = new ArrayList<SatCriterion>();
					nextCombination.add(containerItem);
					nextCombination.addAll(suffix);
					combinations.add(nextCombination);
				}
			}
		}
		return combinations;
	}

}
