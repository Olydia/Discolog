package fr.limsi.negotiate;

import java.util.*;
import edu.wpi.disco.lang.*;
import fr.limsi.negotiate.Proposal.Status;


public class Negotiation<O extends Option> {
	private List<CriterionNegotiation<Criterion>> valueNegotiation;


	private Self_C<O> criteria;
	private ArrayList<OptionProposal> proposals;
	private Class<O> topic;
	private double relation=0; 
	private DialogueContext context ;


	public Negotiation(CriterionNegotiation<Criterion>[] valueNegotiation,
			Self_C<O>  criteriaNegotiation, Class<O> topic) {

		this.valueNegotiation = Arrays.asList(valueNegotiation);
		this.proposals = new ArrayList<OptionProposal>();
		this.setContext(new DialogueContext());
		this.topic=topic;
		setCriteria(criteriaNegotiation);
	}
	//
	//	public Negotiation(CriterionNegotiation<Criterion>[] valueNegotiation, Class<O> topic) {
	//		this.valueNegotiation = Arrays.asList(valueNegotiation);
	//		this.proposals = new ArrayList<OptionProposal>();
	//		this.topic=topic;
	//	}

	public Class<O> getTopic() {
		return topic;
	}
	public List<CriterionNegotiation<Criterion>> getValuesNegotiation() {
		return valueNegotiation;
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

	public double getDominance() {
		return relation;
	}

	public void setDominance(double dominance) {
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
		int t = computeT();

		double s=0;
		if( t< NegotiationParameters.tau)
			return this.relation;
		else{
			s= relation - ((NegotiationParameters.delta/relation) *(t-NegotiationParameters.tau));
			return Math.max(0, s);
		}
	}

	public int computeT() {
		int t = 0;
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			t = t + (value.getProposals().size() - value.getProposalsWithStatus(Status.ACCEPTED).size());
		}
		return t;
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


	public boolean negotiationFailure(Utterance utterance){
		if (getContext().getHistory().size()>= 30 && 
				!(utterance instanceof Propose || utterance instanceof Accept))
			return true;

		//if(getDominance()>=0){
		List<Option> remainOptions=nonRejectedOptions();
		return (remainOptions.isEmpty());
		//|| 
		//	getAcceptableOptions().isEmpty());
		//}
		//		else
		//			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty());
	}

	public Option negotiationSuccess(double relation){
		if(!getOptionsProposals(Status.ACCEPTED).isEmpty())
			return getOptionsProposals(Status.ACCEPTED).get(0).getValue();
		if(relation == NegotiatorAgent.DOMINANT){
			for(OptionProposal o: getOptionsProposals(Status.OPEN)){
				if(!o.isSelf() && acceptability(o.getValue())>= NegotiationParameters.beta)
					return o.getValue();
			}
		}
		return null;
	}


	private List<Option>  nonRejectedOptions() {
		List<Option> remainOptions = new ArrayList<Option>();
		List<OptionProposal> rejected = getOptionsProposals(Status.REJECTED);
		for(Option o: getOptions()){
			if(!rejected.contains(new OptionProposal(o)))
				remainOptions.add(o);
		}
		return remainOptions;
	}

	public List<OptionProposal>  getOptionsProposals(Status status){
		List<OptionProposal> prop =new ArrayList<OptionProposal>();

		for (OptionProposal p: proposals){
			if (p.getStatus().equals(status))
				prop.add(p);
		}
		return prop;
	}


	public List<Option> remainOptions(){
		List<Option> options = nonRejectedOptions();
		ArrayList<Option> removable = new ArrayList<Option>();
		for(CriterionNegotiation<Criterion> elm: valueNegotiation){
			for(Option o: options){
				if(elm.isRejected(o.getValue(elm.getType())))
					removable.add(o);
			}
			options.removeAll(removable);
		}
		return options;

	}

	private List<CriterionProposal> remainCriterionProposals() {
		List<CriterionProposal> prop = new ArrayList<CriterionProposal>();
		for(CriterionNegotiation<Criterion> elm: valueNegotiation){
			for(Criterion c: elm.remainProposals())
				//if(elm.acceptability(c, self())>= NegotiationParameters.beta)
				prop.add(new CriterionProposal(true, c));
		}
		return prop;
	}

	public List<Proposal> remainProposals(){
		List<Proposal> prop = new ArrayList<Proposal>();
		prop.addAll(remainCriterionProposals());
		//for(Option o: remainOptions()){
		//if(acceptability(o)>= NegotiationParameters.beta)
		for(Option o: nonRejectedOptions()){
			prop.add(new OptionProposal(true, o));

		}		return prop;
	}


	public Option chooseOption(List<Option> V){
		V.sort(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2){
				return Float.compare(acceptability(o2), acceptability(o1));
			}
		});
		return V.get(0);
	}

	public Criterion chooseCriterionProposal(){
		ArrayList<Criterion> argmax = new ArrayList<Criterion>();
		for(CriterionNegotiation<Criterion> elm: valueNegotiation)
			argmax.add(elm.chooseValue(elm.remainProposals(),this.self()));

		argmax.sort(new Comparator<Criterion>() {
			@Override
			public int compare(Criterion c1, Criterion c2){
				CriterionNegotiation<Criterion> cn1 = getValueNegotiation(c1.getClass());
				CriterionNegotiation<Criterion> cn2 = getValueNegotiation(c2.getClass());
				return Float.compare(cn2.acceptability(c2, self()), cn1.acceptability(c1, self()));
			}
		});

		return argmax.get(0);
	}

	public Proposal chooseProposal(Utterance utterance) {
		Option bestOption = chooseOption(remainOptions());
		Criterion bestCriterion = chooseCriterionProposal();
		if(utterance instanceof Accept)
			return new OptionProposal(true,bestOption);

		else
			return(acceptability(bestOption) > getValueNegotiation(bestCriterion.getClass()).acceptability(bestCriterion, self())?
					new OptionProposal(true, bestOption): new CriterionProposal(true, bestCriterion));

	}

	public Self_C<O> getCriteria() {
		return criteria;
	}

	public void setCriteria(Self_C<O> criteria) {
		this.criteria = criteria;
	}

	public Option computeOption(List<Criterion> accepted){
		for (Option O : getOptions()){
			int i = 0;
			boolean match = true;
			while (i<accepted.size() && match){
				Criterion value = O.getValue(accepted.get(i).getClass());
				if(!accepted.get(i).equals(value))
					match = false;
				i++;
			}
			if(match)
				return O;
		}
		return null;
	}


}
