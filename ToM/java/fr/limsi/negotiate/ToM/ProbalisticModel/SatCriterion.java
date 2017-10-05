package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import fr.limsi.negotiate.Criterion;
/**
 * 
 * @author ouldouali
 * Type: the Criterion
 * satValue: the satisfiable values for the criterion
 */

public class SatCriterion {
	
		private Class<? extends Criterion> type;
		private List<? extends Criterion> satValues;
		
	public Class<? extends Criterion> getType() {
			return type;
		}

		public List<? extends Criterion> getSatValues() {
			return satValues;
		}

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
	
	public boolean isSatifiable(Criterion c){
		
		return satValues.contains(c);
	}
}
