package fr.limsi.preferenceModel;

import java.util.ArrayList;

public class OptionNegotiation<O> {
	ArrayList<CriterionNegotiation<? extends Criterion>> valueNegotiation;
	CriterionNegotiation<Class<? extends Criterion>> criteriaNegotiation;
	
	
	public OptionNegotiation(ArrayList<CriterionNegotiation<? extends Criterion>> valueNegotiation,
	CriterionNegotiation<Class<? extends Criterion>> criteriaNegotiation) {
		this.criteriaNegotiation = criteriaNegotiation;
		this.valueNegotiation = valueNegotiation;
	}
	
	public float SatisfactionOther(O option) {
		
	}
	
//	public float SatisfactionSelf(O option) {
//		
//	}
	
}
