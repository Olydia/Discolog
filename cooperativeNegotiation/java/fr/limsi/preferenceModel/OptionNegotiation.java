package fr.limsi.preferenceModel;

import java.util.*;

public class OptionNegotiation<O extends Option> {
	private List<CriterionNegotiation<Criterion>> valueNegotiation;
	private Self<Class<? extends Criterion>> criteriaNegotiation;
	private ArrayList<OptionProposal> proposals;

	
	public OptionNegotiation(List<CriterionNegotiation<Criterion>> valueNegotiation,
							Self<Class<? extends Criterion>> criteriaNegotiation) {
		this.criteriaNegotiation = criteriaNegotiation;
		this.valueNegotiation = valueNegotiation;
		this.proposals = new ArrayList<OptionProposal>();
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
	
	public void propose(OptionProposal p){
		this.proposals.add(p);
	}
	
	public boolean isOther (Option option){
		for(OptionProposal p : proposals){
			if(p.getValue().equals(option) && !p.isSelf())
				return true;
		}
		return false;
	}
	
	public boolean isSelf (Option option){
		for(OptionProposal p : proposals){
			if(p.getValue().equals(option) && p.isSelf())
				return true;
		}
		return false;
	}
	
}
