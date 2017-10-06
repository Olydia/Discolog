package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Statement;
import fr.limsi.negotiate.lang.StatePreference;

public class HModels {
	
	private List<MHypothesis> hypotheses ;
	private List<Class<? extends Criterion>> criteria; 

	
	public HModels(List<Class<? extends Criterion>> criteria) {
		hypotheses = new ArrayList<MHypothesis>();
		this.criteria = criteria;
		for(int i=3; i<10; i++){
			hypotheses.add(new MHypothesis(i, criteria));
		}
	}


	public List<MHypothesis> getHypotheses() {
		return hypotheses;
	}


	public List<Class<? extends Criterion>> getCriteria() {
		return criteria;
	}

	
	public double guess(Utterance u){
		double pow = 0;
		if(u instanceof StatePreference){
			Statement<Criterion> c = new Statement<Criterion>(((StatePreference) u).getValue(), 
															((StatePreference) u).getLikable());
			return reviseHypothese(c);

		}
		return pow;

	}
	
	public double reviseHypothese(Statement<Criterion> critrion){
		int max = 1;
		double maxP = 0.3;
		//List<Integer> maxPow = new ArrayList<Integer>();
		
		for (Iterator<MHypothesis> it = hypotheses.iterator(); it.hasNext();) {	
			MHypothesis current = it.next();
			current.revise(critrion);
			if(current.getHypothesis().size()> max){
				max = current.getHypothesis().size();
				maxP = current.getPow();
			}
		}
		
		return maxP;
	}
	
}
