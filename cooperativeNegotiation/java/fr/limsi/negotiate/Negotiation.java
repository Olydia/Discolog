package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Utterance.UtType;

public class Negotiation<O extends Option> {
	private List<CriterionNegotiation<Criterion>> valueNegotiation;
	private ArrayList<OptionProposal> proposals;
	private Class<O> topic;
	private int relation=0; 
	private DialogueContext context ;


	public Negotiation(List<CriterionNegotiation<Criterion>> valueNegotiation,
			Self<Class<? extends Criterion>> criteriaNegotiation, Class<O> topic) {
		this.valueNegotiation = valueNegotiation;
		this.proposals = new ArrayList<OptionProposal>();
		this.setContext(new DialogueContext());
		this.topic=topic;
	}

	public Negotiation(CriterionNegotiation<Criterion>[] valueNegotiation, Class<O> topic) {
		this.valueNegotiation = Arrays.asList(valueNegotiation);
		this.proposals = new ArrayList<OptionProposal>();
		this.topic=topic;
	}

	public CriterionNegotiation<Criterion> getValueNegotiation(Class<? extends Criterion> type){
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			if(value.getType().equals(type))
				return value;
		}
		return null;
	}

	public Option[] getOptions(){
		return (topic.getEnumConstants()); 
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
		return relation;
	}

	public void setDominance(int dominance) {
		this.relation = dominance;
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
	// t is the number of non accepted proposals
	public double self(){
		int t = 0;
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			t = t + (value.getProposals().size() - value.getProposalsWithStatus(Status.ACCEPTED).size());
		}

		double s=0;
		if( t< NegotiationParameters.tau)
			return this.relation;
		else{
			s= relation - ((NegotiationParameters.delta/relation) *(t-NegotiationParameters.tau));
			return Math.max(0, s);
		}
	}

	public float acceptability(Option o){
		float acc =0;
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			Criterion c = o.getValue(value.getType());
			acc = acc+value.acceptability(c, self());
		}
		return acc/valueNegotiation.size();
	}

	public void addStatement(Statement<Criterion> s, boolean external){
		Criterion elem = s.getValue();
		getValueNegotiation(elem.getClass()).addStatement(s, external);
	}
	public Proposal createProposal(Object o, boolean isSelf){
		if(o == null)
			return null;
		if(o instanceof Criterion)
			return new CriterionProposal(isSelf, (Criterion) o);
		if(o instanceof Option)
			return (new OptionProposal(isSelf,(Option) o));

		return null;

	}
	public Proposal createProposal(Object o, boolean isSelf, Status status){
		if(o == null)
			return null;
		else{
			Proposal p = createProposal(o, isSelf);
			p.setStatus(status);
			return p;
		}

	}

	public void addProposal(Proposal proposal) {
		if (proposal instanceof CriterionProposal) {
			Criterion value = (Criterion) proposal.getValue();
			getValueNegotiation(value.getClass()).propose((CriterionProposal) proposal);

		}

		if(proposal instanceof OptionProposal)
			this.propose((OptionProposal) proposal);
	}

	public void updateProposal(OptionProposal proposal){
		for(OptionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				int index = proposals.indexOf(c);
				proposals.set(index, proposal);
			}
		}
	}

	public DialogueContext getContext() {
		return context;
	}

	public void setContext(DialogueContext contex) {
		this.context = contex;
	}
	public O chooseValue(List<O> V){
		V.sort(new Comparator<O>() {
			@Override
			public int compare(O c1, O c2){
				return (int)(acceptability(c1) - acceptability(c1));
			}
		});
		return V.get(0);
	}
	public boolean negotiationFailure(){
		Utterance lastUtterance = this.getContext().getLastMove();
		List<Option> remainOptions = new ArrayList<Option>();
		if (getContext().getHistory().size()>= 20 && 
				!(lastUtterance.getType().equals(UtType.PROPOSE) || lastUtterance.getType().equals(UtType.ACCEPT)))
			return true;

		//if(getDominance()>=0){
			List<OptionProposal> rejected = getOptionsProposals(Status.REJECTED);
			for(Option o: getOptions()){
				if(!rejected.contains(o))
					remainOptions.add(o);
			}
			return (remainOptions.isEmpty());
					//|| 
				//	getAcceptableOptions().isEmpty());
		//}
//		else
//			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty());
	}

	public List<OptionProposal>  getOptionsProposals(Status status){
		List<OptionProposal> prop =new ArrayList<OptionProposal>();

		for (OptionProposal p: proposals){
			if (p.getStatus().equals(status))
				prop.add(p);
		}
		return prop;
	}

}
