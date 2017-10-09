package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Map.Entry;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.toyExample.*;

public class MHypothesis {

	private double pow;
	private List<Class<? extends Criterion>> criteria; 
	private List<Hypothesis> hypothesis;

	public double getPow() {
		return pow;
	}


	public List<Class<? extends Criterion>> getCriteria() {
		return criteria;
	}


	public List<Hypothesis> getHypothesis() {
		return hypothesis;
	}


	public MHypothesis (double pow, List<Class<? extends Criterion>> criteria) {
		this.pow= pow;
		this.criteria = criteria;
		this.hypothesis = computeHypothesis();
	}


	public List<Hypothesis> computeHypothesis(){

		List<List<SatCriterion>> sat = new ArrayList<List<SatCriterion>> ();
		// for each criterion, compute the set of possible hypotheses 
		for(Class<? extends Criterion> c : criteria){
			Satifiability e = new Satifiability(c);
			sat.add(e.generateHypModels(this.pow));
		}

		// combine the different models obtained for each criterion
		List<List<SatCriterion>> models = getCombination(0, sat);
		List<Hypothesis> hypo = new ArrayList<Hypothesis>();

		models.forEach(model -> hypo.add(new Hypothesis(model)));

		return hypo;
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

	public void revise(Statement<? extends Criterion> s){
		for (Iterator<Hypothesis> it = hypothesis.iterator(); it.hasNext();) {	
			Hypothesis elem = it.next();
			if(!elem.getSat(s.getValue()).equals(s.getStatus()))
				it.remove();
		}


	}
	



	public static void main(String[] args) {

		HModels model = new HModels(ToyRestaurant.A_CITADELLA.getCriteria());
		for(MHypothesis elem : model.getHypotheses()){
			elem.revise(new Statement<ToyCuisine>(ToyCuisine.CHINESE, Satisfiable.FALSE));

//			for(Hypothesis e : elem.getHypothesis())
//				System.out.println(e.getModel());

		}
	}
}
