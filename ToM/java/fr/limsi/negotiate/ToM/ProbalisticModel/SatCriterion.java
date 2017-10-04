package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import fr.limsi.negotiate.Criterion;

public class SatCriterion {
		Class<? extends Criterion> type;
		List<? extends Criterion> satValues;
		
	public SatCriterion(Class<? extends Criterion> type) {
		this.type = type;
		this.satValues = new ArrayList<Criterion>();
		
	}
	public SatCriterion(Class<? extends Criterion> type,
		List<? extends Criterion> satValues){
		
		this.type =type;
		this.satValues = satValues;
	}
	
	public String toString() {
		String s = ("{ Type : " + this.type.getSimpleName());
		s += (" , values : " +  this.satValues +"}  | ");
		return s;
	} 
}
