package fr.limsi.preferenceModel;

import java.util.*;

public class Negotiation<O extends Option> {
	private List<CriterionNegotiation<Criterion>> valueNegotiation;
	private ArrayList<OptionProposal> proposals;
	private int dominance=0; 

	
	public Negotiation(List<CriterionNegotiation<Criterion>> valueNegotiation,
							Self<Class<? extends Criterion>> criteriaNegotiation) {
		this.valueNegotiation = valueNegotiation;
		this.proposals = new ArrayList<OptionProposal>();
	}
	

	public CriterionNegotiation<Criterion> getValueNegotiation(Class<? extends Criterion> type){
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			if(value.getType().equals(type))
				return value;
		}
		return null;
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

	public int getDominance() {
		return dominance;
	}

	public void setDominance(int dominance) {
		this.dominance = dominance;
	}
	
	public float satisfiability(O option) {
		float satisfaction = 0;
		for(Class<? extends Criterion> criterion : option.getCriteria()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += value.getSelf().satisfaction(option.getValue(criterion));
		}
		return satisfaction;
	}
	
	public float other(O option) {
		float satisfaction = 0;
		for(Class<? extends Criterion> criterion : option.getCriteria()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += value.getOther().other(option.getValue(criterion));
		}
		
		return satisfaction;
	}
	public double self(){
		return 0;
	}
}
