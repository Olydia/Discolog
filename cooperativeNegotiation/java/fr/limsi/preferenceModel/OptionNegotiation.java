package fr.limsi.preferenceModel;

import java.util.*;

public class OptionNegotiation<O extends Option> {
	List<CriterionNegotiation<Criterion>> valueNegotiation;
	Self<Class<? extends Criterion>> criteriaNegotiation;
	
	
	public OptionNegotiation(List<CriterionNegotiation<Criterion>> valueNegotiation,
							Self<Class<? extends Criterion>> criteriaNegotiation) {
		this.criteriaNegotiation = criteriaNegotiation;
		this.valueNegotiation = valueNegotiation;
	}
	
	public float satisfactionOther(O option) {
		float satisfaction = 0;
		for(Class<? extends Criterion> criterion : criteriaNegotiation.getElements()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += criteriaNegotiation.satisfaction(criterion) * 
							value.getOther().satisfaction(option.getValue(criterion));
		}
		
		return satisfaction;
	}
	public CriterionNegotiation<Criterion> getValueNegotiation(Class<? extends Criterion> type){
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			if(value.getType().equals(type))
				return value;
		}
		return null;
	}
	public float satisfactionSelf(O option) {
		float satisfaction = 0;
		for(Class<? extends Criterion> criterion : criteriaNegotiation.getElements()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += criteriaNegotiation.satisfaction(criterion) * 
							value.getSelf().satisfaction(option.getValue(criterion));
		}
		return satisfaction;
	}
	
	
}
