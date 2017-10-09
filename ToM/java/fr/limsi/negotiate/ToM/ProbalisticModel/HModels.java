package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.lang.*;

public class HModels {
	
	private List<MHypothesis> hypotheses ;
	private List<Class<? extends Criterion>> criteria; 

	
	public HModels(List<Class<? extends Criterion>> criteria) {
		hypotheses = new ArrayList<MHypothesis>();
		this.criteria = criteria;
		for(int i=3; i<10; i++){
			double pow =i/10.0;
			hypotheses.add(new MHypothesis(pow, criteria));
		}
	}


	public List<MHypothesis> getHypotheses() {
		return hypotheses;
	}


	public List<Class<? extends Criterion>> getCriteria() {
		return criteria;
	}

	
	public double guess(Utterance u, double previousPow){
		System.out.println(u.format());
		if(u instanceof StatePreference){
			Statement<Criterion> c  = new Statement<Criterion>(((StatePreference) u).getValue(), 
															((StatePreference) u).getLikable());
			return reviseHypothese(c, previousPow);

		}
		else if(u instanceof Reject){
			return updateReject(((Reject) u).getProposal(), previousPow);
		}
		
		else if(u instanceof RejectState){
			Proposal reject = ((RejectState) u).getProposal();
			Criterion justify = ((RejectState) u).getJustify();
			if(reject.getValue().equals(justify))
				return updateReject(reject, previousPow);
			else{
				updateReject(reject, previousPow);
				return reviseHypothese(new Statement<Criterion>(justify, Satisfiable.FALSE), previousPow);
			}
		}
		return previousPow;
	}
	
	public double updateReject(Proposal rejected, double previousPow){
		if(rejected instanceof CriterionProposal){
			Criterion elem = (Criterion) rejected.getValue();
			return reviseHypothese(new Statement<Criterion>(elem, Satisfiable.FALSE), previousPow);
		}
		 // Creer une fonction 
		return 0.3; 
	}
	
	public double reviseHypothese(Statement<Criterion> critrion, double previousPow){

		Map <Double,Integer> maxPow = new HashMap<Double,Integer>();
		
		for (Iterator<MHypothesis> it = hypotheses.iterator(); it.hasNext();) {	
			MHypothesis current = it.next();
			current.revise(critrion);
			maxPow.put(current.getPow(), current.getHypothesis().size());
//			if(current.getHypothesis().size()>= max){
//				max = current.getHypothesis().size();
//				maxP = current.getPow();
//			}
		}
		
		return reviseOtherPow(maxPow, previousPow);
	}
	

	public  Map<Double, Integer> sortPower(Map<Double, Integer> unsortMap){
		Map<Double, Integer> result = unsortMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));



		return (result);
		
	}
	
	public double reviseOtherPow(Map<Double, Integer> values, double previousPow){
		Map<Double, Integer> result = sortPower(values); 

		int max = java.util.Collections.max(result.values());

		List<Double> keys = new ArrayList<Double>();

		for(Iterator<Entry<Double, Integer>> it = result.entrySet().iterator(); it.hasNext();){
			Entry<Double, Integer> entry = it.next();

			if(entry.getValue().equals(max))
				keys.add(entry.getKey());
		}
		
		if(result.values().size() == keys.size())
			return previousPow;

		OptionalDouble average = keys.stream()
				.mapToDouble(a -> a).average();

		return average.isPresent() ? average.getAsDouble() : 0; 
	}
	
//	public Statement<Criterion> isStatement(Utterance u){
//		if(u instanceof StatePreference)
//			 return (new Statement<Criterion>(((StatePreference) u).getValue(), 
//					((StatePreference) u).getLikable()));
//		if (u instanceof RejectState)
//			return (new Statement<Criterion>(((RejectState) u).getJustify(),Satisfiable.FALSE));
//		
//		return null;
//	}
	
	
	
}
