package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.List;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;
/**
 * 
 * @author ouldouali lydia
 *  hypothesis formulated for a given pow 
 *
 */

public class Hypothesis {

	
	private List<CriterionHypothesis> model;

	
	public List<CriterionHypothesis> getModel() {
		return model;
	}


	public void setModel(List<CriterionHypothesis> model) {
		this.model = model;
	}



	public Hypothesis(List<CriterionHypothesis> model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}


	public CriterionHypothesis getCriterionSat(Class<? extends Criterion> type){
		for(CriterionHypothesis sat: model){
			if(sat.getType().equals(type))
				return sat;
		}
		return null;
	}


	public Satisfiable getSat(Criterion c){

		return getCriterionSat(c.getClass()).
				getSatisfaction(c);
	}
	
	
	

}
