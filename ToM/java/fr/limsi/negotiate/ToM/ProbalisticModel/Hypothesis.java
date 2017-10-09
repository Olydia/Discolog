package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Statement.Satisfiable;

public class Hypothesis {
	
	public List<SatCriterion> getModel() {
		return model;
	}


	public void setModel(List<SatCriterion> model) {
		this.model = model;
	}


	List<SatCriterion> model;
	
	public Hypothesis(List<SatCriterion> model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}
	
	
	public SatCriterion getCriterionSat(Class<? extends Criterion> type){
		for(SatCriterion sat: model){
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
