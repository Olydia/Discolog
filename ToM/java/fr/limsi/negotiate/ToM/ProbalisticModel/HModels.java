package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class HModels {

	private List<PowHypothesis> hypotheses ;
	private List<Class<? extends Criterion>> criteria; 


	public HModels(List<Class<? extends Criterion>> criteria) {
		hypotheses = new ArrayList<PowHypothesis>();
		this.criteria = criteria;
		for(int i=3; i<10; i++){
			double pow =i/10.0;
			hypotheses.add(new PowHypothesis(pow, criteria));
		}
	}


	public List<PowHypothesis> getHypotheses() {
		return hypotheses;
	}


	public List<Class<? extends Criterion>> getCriteria() {
		return criteria;
	}



	public double updateReject(List<PowHypothesis> models, 
			Proposal rejected, double previousPow){
		if(rejected instanceof CriterionProposal){
			Criterion elem = (Criterion) rejected.getValue();
			return reviseHypothese(models, new Statement<Criterion>
			(elem, Satisfiable.FALSE), previousPow);
		}
		// Creer une fonction 
		return previousPow; 
	}
	
	public double reviseHypothese(List<PowHypothesis> models, Statement<Criterion> critrion, double previousPow){

		Map <Double,Float> maxPow = new HashMap<Double,Float>();
		//System.out.println(maxPow);
		for (Iterator<PowHypothesis> it = models.iterator(); it.hasNext();) {	
			PowHypothesis current = it.next();
			current.revise(critrion);
			
			int currentSize = current.getHypothesis().size();
			if(currentSize == 0)
				it.remove();
			else {
				//System.out.println(current.getPow() + " restant "+ currentSize + "total "+ current.getInitModels() );
				float rationSat = (float) ((float) currentSize / current.getInitModels()); 
				maxPow.put(current.getPow(), ((float)rationSat));

			}

		}

		return reviseOtherPow(maxPow, previousPow);
	}

	public double reviseHypothese(Statement<Criterion> critrion, double previousPow){
		return reviseHypothese(hypotheses, critrion, previousPow);
	}

	

	public  Map<Double, Float> sortPower(Map<Double, Float> unsortMap){
		Map<Double, Float> result = unsortMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));



		return (result);

	}

	public double reviseOtherPow(Map<Double, Float> values, double previousPow){

		Map<Double, Float> result = sortPower(values); 
		System.out.println(result);

		float max = java.util.Collections.max(result.values());
		
		List<Double> keys = new ArrayList<Double>();

		for(Iterator<Entry<Double, Float>> it = result.entrySet().iterator(); it.hasNext();){

			Entry<Double, Float> entry = it.next();

			if(entry.getValue().equals(max))
				keys.add(entry.getKey());
		}


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

	public List<PowHypothesis> getDom(){
		List<PowHypothesis> p = new ArrayList<PowHypothesis>();
		for(PowHypothesis h: this.hypotheses){
			if(h.getPow()> 0.5)
				p.add(h);
		}
		return p;
	}

	public List<PowHypothesis> getSub(){
		List<PowHypothesis> p = new ArrayList<PowHypothesis>();
		for(PowHypothesis h: this.hypotheses){
			if(h.getPow()<= 0.5)
				p.add(h);
		}
		return p;
	}
	
	public Map<Double, Float> getHypothesesSize(){
		return getHypothesesSize(this.hypotheses);
	}


	public Map<Double, Float> getHypothesesSize(List<PowHypothesis> models) {
		Map<Double, Float> sizes = new HashMap<Double, Float>();
		for(PowHypothesis h: models){
			float mean =((float)  h.getHypothesis().size()) / h.getInitModels();
			sizes.put(h.getPow(), mean);
		}
		return sizes;
	}
}