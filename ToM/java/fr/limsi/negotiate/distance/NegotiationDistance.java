package fr.limsi.negotiate.distance;
import  fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class NegotiationDistance {
	private Negotiation<? extends Option> model;
	private Negotiation<? extends Option> model2;

	public NegotiationDistance(Negotiation<? extends Option> model, Negotiation<? extends Option> model2) {
		// TODO : exception if type of model 1 different from mode 2
		this.model=model;
		this.model2=model2;
	}
	


	public double distance (){
		double sum = 0;
		int cmp =0;
		for(Class<? extends Criterion> criterion: model.getCriteria().getElements()){
			
			CriterionNegotiation<Criterion> cr1 = model.getValueNegotiation(criterion);
			
			CriterionNegotiation<Criterion> cr2 = model2.getValueNegotiation(criterion);
			
			Distance current = new Distance (cr1.getSelf().getSelfPreferences(), cr2.getSelf().getSelfPreferences(), cr1.getElements());
			sum+= current.distance();
			cmp ++;
		}
		//Distance criteria = new Distance(model.getCriteria().getPreferences(),model2.getCriteria().getPreferences(),model.getCriteria().getElements());
	
		
	return sum/cmp ;
	
	}
	
	public static void main(String[] args) {
		
		totalOrderedModels tm = new totalOrderedModels();
		
		NegotiationDistance distance12 = new NegotiationDistance(tm.model1(), tm.model2());
		NegotiationDistance distance13 = new NegotiationDistance(tm.model1(), tm.model3());
		NegotiationDistance distance14 = new NegotiationDistance(tm.model1(), tm.model4());
		
		NegotiationDistance distance23 = new NegotiationDistance(tm.model2(), tm.model3());
		NegotiationDistance distance24 = new NegotiationDistance(tm.model2(), tm.model4());

		NegotiationDistance distance34 = new NegotiationDistance(tm.model3(), tm.model4());

		System.out.println( "1, 2: " + distance12.distance());
		System.out.println( "1, 3: " + distance13.distance());
		System.out.println("1, 4: " + distance14.distance());
		
		System.out.println( "2, 3: " +distance23.distance());
		System.out.println("2, 4: " +distance24.distance());
		
		System.out.println("3, 4: " +distance34.distance());


	}

}
