package fr.limsi.negotiate;

import java.util.List;

public class DistanceNegotiation <O extends Option> {
	private Negotiation<O> p1;
	private Negotiation<O> p2;

	public DistanceNegotiation(Negotiation<O> p1, Negotiation<O> p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public  double calculateDistance(CriterionPrefModel<Criterion> cm1, CriterionPrefModel<Criterion> cm2){
		double euclidian = 0;
		if(cm1.getValues().equals(cm2.getValues())){
			for(Criterion c : cm1.getValues()){
				double distance = cm1.getScore(c) - cm2.getScore(c);
				euclidian = euclidian + Math.pow(distance, 2);
			}
		}
			return Math.sqrt(euclidian);	
	}
	
	public double finalDistance(){
		List<Class<? extends Criterion>> classes = p1.criteriaPreferences.getValues();
		double somme = 0;
		for (Class<? extends Criterion> c : classes) {
			
			somme += calculateDistance(p1.getCriterionNegotiation(c).getSelf(), p2.getCriterionNegotiation(c).getSelf());
		}
		return somme;
	}
}
