package fr.limsi.negotiate;

import java.util.*;

import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.lang.*;


public class Negotiation<O extends Option> implements Cloneable {
	private List<CriterionNegotiation<Criterion>> valueNegotiation;


	private Self_C<O> criteria;
	private ArrayList<OptionProposal> proposals;
	private Class<O> topic;
	private double relation=0; 
	private DC context_bis;


	public Negotiation(CriterionNegotiation<Criterion>[] valueNegotiation,
			Self_C<O>  criteriaNegotiation, Class<O> topic) {

		this.valueNegotiation = Arrays.asList(valueNegotiation);
		this.proposals = new ArrayList<OptionProposal>();
		this.topic=topic;
		setCriteria(criteriaNegotiation);
		this.context_bis =  new DC(criteria.sortValues());

	}
	
	public DC getContext() {
		return context_bis;
	}

	public void setContext_bis(DC context_bis) {
		this.context_bis = context_bis;
	}

	public Negotiation(List<CriterionNegotiation<Criterion>>valueNegotiation, double relation,
			Self_C<O>  criteriaNegotiation, Class<O> topic, DC c, 
			ArrayList<OptionProposal> proposals) {

		this.valueNegotiation = valueNegotiation;
		this.relation = relation;
		this.proposals = proposals;
		this.topic=topic;
		setCriteria(criteriaNegotiation);
		this.context_bis = c;
	}
	
	// for Guess class
	public Negotiation(List<CriterionNegotiation<Criterion>>valueNegotiation,
			Self_C<O>  criteriaNegotiation, Class<O> topic, DC c, 
			ArrayList<OptionProposal> proposals) {

		this.valueNegotiation = valueNegotiation;
		this.proposals = proposals;
		this.topic=topic;
		setCriteria(criteriaNegotiation);
		this.context_bis = c;
	}
	
	@SuppressWarnings("unchecked")
	public Negotiation(Negotiation<? extends Option> negotiation) {
		this.valueNegotiation = negotiation.getValuesNegotiation();
		this.proposals = negotiation.getProposals();
		this.topic= (Class<O>) negotiation.getTopic();
		setCriteria((Self_C<O>) negotiation.getCriteria());
		this.context_bis = negotiation.getContext();	}

	public ArrayList<OptionProposal> getProposals() {
		return proposals;
	}

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

	public O[] getOptions(){
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
		int n=0;
		for(Class<? extends Criterion> criterion : option.getCriteria()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += value.getSelf().satisfaction(option.getValue(criterion));
			n++;
		}
		return satisfaction/n;
	}

	public float other(O option) {
		float satisfaction = 0;
		int n=0;
		for(Class<? extends Criterion> criterion : option.getCriteria()){
			CriterionNegotiation<Criterion> value = this.getValueNegotiation(criterion);
			satisfaction += value.getOther().other(option.getValue(criterion));
		}

		return satisfaction/n;
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
	
	public double selfTest(int t){
		
		double s=0;
		if( t< NegotiationParameters.tau)
			return this.relation;
		else{
			s= relation - ((NegotiationParameters.delta/relation) *(t-NegotiationParameters.tau));
			return Math.max(0, s);
		}
	}

	public int computeT() {

		return getContext().getNonAcceptedProposals().size();
	}

	public float tolerable(Option o){
		float acc =0;
		for(CriterionNegotiation<Criterion> value: valueNegotiation){
			Criterion c = o.getValue(value.getType());
			acc = acc+value.tolerable(c, self());
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



	public boolean negotiationFailure(Utterance utterance){
		if (getContext().getHistory().size()>= 40 && 
				!(utterance instanceof Propose || utterance instanceof Accept))
			return true;

		//if(getDominance()>=0){
		List<Option> remainOptions= nonRejectedOptions();
		 return getAcceptableOptions(remainOptions).isEmpty();
		//return (remainOptions.isEmpty());
		//|| 
		//	getAcceptableOptions().isEmpty());
		//}
		//		else
		//			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty());
	}


	// used to compute the value for booktable task
	public boolean negotiationSuccess(){
		//double relation = getDominance();
		//		if(utt instanceof Accept){
		//			Proposal p = ((Accept) utt).getProposal();
		//			if( p instanceof OptionProposal)
		//				return (Option) p.getValue();
		//		}

		if(!getOptionsProposals(Status.ACCEPTED).isEmpty())
			//return getOptionsProposals(Status.ACCEPTED).get(0).getValue();
			return true;
		//		if(relation == NegotiatorAgent.DOMINANT){
		//			for(OptionProposal o: getOptionsProposals(Status.OPEN)){
		//				if(!o.isSelf() && isAcceptable(o))
		//					return o.getValue();
		//			}
		//		}
		return false;
	}

	@SuppressWarnings("unchecked")
	boolean isAcceptable(Option option){
		double satisfiability= satisfiability((O)option);
		return satisfiability >= NegotiationParameters.beta * self();
	}
	// test of acceptability

	public boolean isAcceptable(Proposal p){

		if(p instanceof CriterionProposal){
			Criterion c = (Criterion)p.getValue();
			return getValueNegotiation(c.getClass()).isAcceptable(c, self());
		}

		if(p instanceof OptionProposal){
			return isAcceptable((Option)p.getValue());
		}

		return false;
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
			//			List<Class<? extends Criterion>> closed = getContext().getClosedCriteria();
			//			if(!closed.isEmpty()){
			//				for(Option o: options){
			//					int i = 0; boolean correspond = true;
			//					while(i<closed.size() && correspond){
			//						CriterionNegotiation<Criterion> c = getValueNegotiation(closed.get(i));
			//					}
			//				}
			//
			//				
			//			}
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

	/**
	 * Used to compute the condition of negotiation failure, in the case where all the remain proposals are
	 * no longer satisfiable for the agent.
	 * @return
	 */
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
		if (V.isEmpty())
			return null;
		V.sort(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2){
				return Float.compare(tolerable(o2), tolerable(o1));
			}
		});
		return V.get(0);
	}

	public Criterion chooseCriterionProposal(){
		ArrayList<Criterion> argmax = new ArrayList<Criterion>();

		List<Class<? extends Criterion>> discussions = getContext().getPossibleDiscussions(getCriteria().sortValues());
		discussions = getCriteria().sortValues(discussions);
		double self = this.self();

		for(int i = discussions.size()-1; i>=0; i--){
			CriterionNegotiation<Criterion> elm = getValueNegotiation(discussions.get(i));
			List<Criterion> acc = elm.getAcceptableValues(elm.remainProposals(), self);
			if(acc.isEmpty())	
				return null;

			Criterion value = elm.chooseValue(acc,self);
			argmax.add(value);


		}
		argmax.sort(new Comparator<Criterion>() {
			@Override
			public int compare(Criterion c1, Criterion c2){
				CriterionNegotiation<Criterion> cn1 = getValueNegotiation(c1.getClass());
				CriterionNegotiation<Criterion> cn2 = getValueNegotiation(c2.getClass());
				return Float.compare(cn2.tolerable(c2, self()), cn1.tolerable(c1, self()));
			}
		});

		return argmax.get(0);

	}
	public List<Option> getAcceptableOptions(List<Option> options){
		List<Option> acceptables = new ArrayList<Option>();
		for(Option o: options){
			if(isAcceptable(o))
				acceptables.add(o);
		}
		return acceptables;
	}

	public Proposal chooseProposal() {
		// get the agent acceptable options which have not been rejected during the negotiation
		List<Option> acceptables = getAcceptableOptions(nonRejectedOptions());
		Option bestOption = chooseOption(acceptables);

		// All the criteria have been discussed and closed (a value was accepted)
		if(getContext().getPossibleDiscussions(this.getCriteria().getElements()).isEmpty())
			return new OptionProposal(true, bestOption);

		Criterion c = chooseCriterionProposal();
		if(c!=null){
		//Any value is accepted yet 
			if(getContext().getClosedCriteria().isEmpty() && c != null){
				return new CriterionProposal(true,c);
			}
			
			// Otherwise
			return(tolerable(bestOption) > getValueNegotiation(c.getClass()).tolerable(c, self())?
					new OptionProposal(true, bestOption): new CriterionProposal(true, c));
		}
		return new OptionProposal(true, bestOption);

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

	public Criterion justifyReject(Proposal p){
		if(p instanceof CriterionProposal)
			return (Criterion)p.getValue();

		else {
			double min = 1;
			Criterion least = null;
			Option option = (Option) p.getValue();
			for (Class<? extends Criterion> cr: option.getCriteria()){
				double sat = getValueNegotiation(cr).getSelf().satisfaction(option.getValue(cr));
				if(sat<= min) {
					min = sat;
					least = option.getValue(cr);
				}
			}

			return least;
		}
	}

	// used to compute the Accept utterance format

	public boolean isLastProposal(Proposal accepted){
		return getContext().getLastProposal().equals(accepted);
	}
	// check if the last utterance is a Propose
	public boolean isPropose(boolean isSelf){
		NegotiationUtterance uttSelf =  getContext().getHistory().get(getContext().getHistory().size()-2);//getContext().getLastMove(!isSelf);
		
				
		return (uttSelf instanceof ProposalUtterance);

	}
	
	public void clearNegotiation(){
		this.proposals.clear();
		this.context_bis.clearNegotiation();
		for(CriterionNegotiation<Criterion> cn : getValuesNegotiation())
			cn.clearNegotiation();
	}

	  @SuppressWarnings("unchecked")
	@Override
	public Negotiation<? extends Option> clone() throws CloneNotSupportedException {
	        return (Negotiation<? extends Option>) super.clone();
	    }

}