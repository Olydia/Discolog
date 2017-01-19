package fr.limsi.negotiate.distance;
import  fr.limsi.negotiate.*;

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
			//sum+= current.Knn();
			cmp ++;
		}
		//Distance criteria = new Distance(model.getCriteria().getPreferences(),model2.getCriteria().getPreferences(),model.getCriteria().getElements());
	
		
	return sum/cmp ;
	
	}

}
