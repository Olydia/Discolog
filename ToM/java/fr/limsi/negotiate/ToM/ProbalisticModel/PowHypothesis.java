package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.toyExample.*;

/**
 * 
 * @author ouldouali
 * {@value} satifiability : the initial values of sat for each criterion: needed to compute acc
 *
 */

public class PowHypothesis{


	private double pow;
	private List<Class<? extends Criterion>> criteria; 
	private List<Hypothesis> hypothesis;
	private final int initModels;
	private Map<Class<? extends Criterion>, List<Float>> satisfiability;




	public PowHypothesis (double pow, List<Class<? extends Criterion>> criteria) {
		this.pow= pow;
		this.criteria = criteria;
		this.hypothesis = computeHypothesis();
		this.initModels = hypothesis.size();
	}

	public double getPow() {
		return pow;
	}


	public List<Class<? extends Criterion>> getCriteria() {
		return criteria;
	}


	public List<Hypothesis > getHypothesis() {
		return hypothesis;
	}


	public int getInitModels() {
		return initModels;
	}

	public void setHypothesis(List<Hypothesis> hypothesis) {
		this.hypothesis = hypothesis;
	}

	public List<Hypothesis > computeHypothesis(){
		this.satisfiability = new HashMap<Class<? extends Criterion>, List<Float>>();

		List<List<CriterionHypothesis>> sat = new ArrayList<List<CriterionHypothesis>> ();

		// for each criterion, compute the set of possible hypotheses 
		for(Class<? extends Criterion> c : criteria){
			Satifiability e = new Satifiability(c);

			satisfiability.put(c, e.getSat());

			sat.add(e.generateHypModels(this.pow));
		}

		// combine the different models obtained for each criterion
		List<List<CriterionHypothesis>> models = getCombination(0, sat);
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


	public List<List<CriterionHypothesis>> getCombination(int currentIndex,  
			List<List<CriterionHypothesis>> preferences) {

		if (currentIndex == preferences.size()) {
			// Skip the items for the last container
			List<List<CriterionHypothesis>> combinations = new ArrayList<List<CriterionHypothesis>>();
			combinations.add(new ArrayList<CriterionHypothesis>());
			return combinations;
		}

		List<List<CriterionHypothesis>> combinations = new ArrayList<
				List<CriterionHypothesis>>();
		List<CriterionHypothesis> container = preferences.get(currentIndex);
		List<CriterionHypothesis> containerItemList = container;

		// Get combination from next index
		List<List<CriterionHypothesis>> suffixList = getCombination(currentIndex + 1, preferences);
		int size = containerItemList.size();

		for (int ii = 0; ii < size; ii++) {
			CriterionHypothesis containerItem = containerItemList.get(ii);

			if (suffixList != null) {

				for (List<CriterionHypothesis> suffix : suffixList) {
					List<CriterionHypothesis> nextCombination = 
							new ArrayList<CriterionHypothesis>();
					nextCombination.add(containerItem);
					nextCombination.addAll(suffix);
					combinations.add(nextCombination);
				}
			}
		}
		return combinations;
	}

	/**
	 * 
	 * @param s statement to use in order to revise the knwoledge about other
	 */

	public void revise(Statement<? extends Criterion> s){
		for (Iterator<Hypothesis> it = hypothesis.iterator(); it.hasNext();) {	

			Hypothesis elem = it.next();
			if(!elem.getSat(s.getValue()).equals(s.getStatus()))
				it.remove();
		}


	}

	/**
	 * 
	 * @return the number of acceptable values 
	 */
	public int getAcceptable(Class<? extends Criterion> criterion, double self){

		List<Float> sats = getSatValues(criterion);
		int D = criterion.getEnumConstants().length;

		for(float s : sats){

			if(s >= self)
				return D - sats.indexOf(s);
		}
		return 0;
	}


	public List<Float> getSatValues(Class<? extends Criterion> c){

		return this.satisfiability.get(c);

	}
	// Call from ToMnegotiatorProba

	public float scoreAcc(Criterion criterion, List<CriterionProposal> accepted, double self){

		Class<? extends Criterion> type = criterion.getClass();
		// get the number of acceptable values in the model
		int acc = getAcceptable(type, self);
		int sat = getAcceptable(type, pow);

		int m = acc - sat;
		
		// m = 0 means that Sat = Acc no concessions only sat values are acceptables
		// update models as state
		//System.out.println("Value of power :" + this.pow + "Value of Self "+ self + " m " + m);
		//System.out.println("acceptable values " + acc + "sat values " + sat);

		if(m == 0){
			
			revise(new Statement<Criterion>(criterion, Satisfiable.TRUE));
			float result =  ( (float) hypothesis.size()/ initModels);
			//System.out.println( result + " il reste " + hypothesis.size() + " sur un total de " + initModels) ;
			return result;
		}

		int totalScore = 0;
		int n = type.getEnumConstants().length - sat;

		double perfectScore =  Combination.combination(m, n);

		for(Hypothesis h : this.hypothesis){

			CriterionHypothesis current = h.getCriterionSat(type);
			totalScore += ((float)current.scoreAcc(criterion, m, accepted));

		}
		
		
		// il ne manque que diviser sur la taille init de toutes les valeurs
		float ratioAcc = (float) ((float) totalScore/perfectScore);
	//	System.out.println("-------------------  score "+ totalScore + " perfect "+ perfectScore + " init " + initModels + " ");
		return  (float) (ratioAcc/initModels);
	}


	public static void main(String[] args) {

		HModels model = new HModels(ToyRestaurant.A_CITADELLA.getCriteria());
		for(PowHypothesis elem : model.getHypotheses()){
			elem.revise(new Statement<ToyCuisine>(ToyCuisine.CHINESE, Satisfiable.FALSE));

			//			for(Hypothesis e : elem.getHypothesis())
			//				System.out.println(e.getModel());

		}
	}
}
