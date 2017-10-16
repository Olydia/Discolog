package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;
import fr.limsi.negotiate.*;
/**
 * 
 * @author ouldouali
 * Type: the Criterion
 * satValue: the satisfiable values for the criterion
 */
import fr.limsi.negotiate.Statement.Satisfiable;

public class CriterionHypothesis{

	private Class<? extends Criterion> type;
	private List<Criterion> satValues;

	
	public Class<? extends Criterion> getType() {
		return type;
	}

	
	public List<Criterion> getSatValues() {
		return satValues;
	}

	public CriterionHypothesis(Class<? extends Criterion> type) {
		this.type = type;
		this.satValues = new ArrayList<Criterion>();

	}

	public CriterionHypothesis(Class<? extends Criterion> type,
			List<Criterion> satValues){

		this.type =type;
		this.satValues = satValues;
	}

	public String toString() {
		String s = ("{ Type : " + this.type.getSimpleName());
		s += (" , values : " +  this.satValues.toString() +"}  | ");
		return s;
	} 

	public boolean isSatifiable(Criterion c){

		return satValues.contains(c);
	}

	public Satisfiable getSatisfaction(Criterion c){

		return(isSatifiable(c)? Satisfiable.TRUE : Satisfiable.FALSE);
	}

	public int getDomainSize(){

		return this.type.getEnumConstants().length;
	}
	
	public int sat(Criterion c){
		return (isSatifiable(c) ? 1 : 0);
	}

	/**
	 *  
	 * @param k: Nb of accepted criteria during the negotiation
	 * @param m: card(M): M is the set of acceptable values which are not Sat.
	 * @param accepted: list of accepted values during the negotiation
	 * 
	 * @return the score to accept a proposal in the current state of the negotiation
	 */
	
	public double scoreAcc(Criterion criterion, int m, List<CriterionProposal> accepted){

		// Case criterion is sat
		
		if(this.getSatisfaction(criterion).equals(Satisfiable.TRUE))
			return 1;
		
		// Otherwise computes the score of acceptability
		System.out.println(satValues);
		int subset = m - getT(accepted);
		int total = getDomainSize() - (satValues.size() + getT(accepted));
		System.out.println("domain size  " +getDomainSize() +" |sat values "+ satValues.size() +
				" |acc and not sat " + getT(accepted)+ "| m : " + m);
		double combi = combination(subset, total);
		System.out.println("Score = "+ combi );
		return combi;
	}

	
	/**
	 * 
	 * @param accepted: the list of accepted proposals from the same type
	 * @return T: the number of accepted propsals which are not sat  T = card(M and not(S))
	 */
	public int  getT(List<CriterionProposal> accepted){

		int t = 0;

		for(CriterionProposal a : accepted){
			if(!isSatifiable(a.getValue()))
				t++;
		}

		return t;
	}
//
//	//  Method to compute combination of k in the set of size n
//
//	

	
	
	public static int fact(int n) {
	      if (n <= 1)
	            return 1;
	      else
	            return n * fact(n - 1);
	}

	public double combination(int k, int n){
		int total = fact(n);
		int sub = fact(k) * fact(n-k);
		return (total/sub);
	}
	

}
