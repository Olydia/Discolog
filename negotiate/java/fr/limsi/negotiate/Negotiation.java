package fr.limsi.negotiate;

import java.util.*;
import fr.limsi.negotiate.Proposal.Status;

/**
 * This class defines the negotiation on a defined option (for example Restaurant) 
 * To negotiate we defined the list of propositions on table Proposal
 * 
 * 
 */

public class Negotiation<O extends Option> {

	private List<OptionProposal> proposals;
	public List<CriterionNegotiation<Criterion>> criteriaNegotiation;
	public CriteriaClassPrefModel<O> criteriaPreferences; 
	private DialogueContext context ;
	public DialogueContext getContext() {
		return context;
	}

	public Class<O> type; 



	public Negotiation (CriterionNegotiation<Criterion>[] criteriaNegotiation, 
			CriteriaClassPrefModel<O> criteriaPreferences) {
		this.criteriaNegotiation = Arrays.asList(criteriaNegotiation);
		this.criteriaPreferences = criteriaPreferences;
		this.proposals = new ArrayList<OptionProposal>();
		this.context = new DialogueContext();
		this.type = criteriaPreferences.type;
	}


	public void propose (OptionProposal proposal) { 
		if(!proposals.contains(proposal))
			proposals.add(proposal); 
		updateProposal(proposal, Status.OPEN);}

	public Option[] getOptions(){
		return (type.getEnumConstants()); 
	}

	public Option getOptionWithValue(Criterion criterion){
		List<Option> options = sortOptions(getOptionsWithoutStatus(Proposal.Status.REJECTED));
		Class<? extends Criterion> CriterionType = criterion.getClass();
		for(Option p: options){
			if(p.getValue(CriterionType).equals(criterion))
				return p;
		}
		return options.get(0);
	}
	public List<OptionProposal> getProposals() {
		return proposals;

	}


	public List<Option> getOptionsPropWithStatus(Proposal.Status status) {
		List<Option> pStatus = new ArrayList<Option>();	
		for (OptionProposal p: proposals){
			if (p.getStatus().equals(status))
				pStatus.add(p.getValue());
		}
		return pStatus;
	}

	public CriterionNegotiation<Criterion> getCriterionNegotiation(Class<? extends Criterion> c){
		for (CriterionNegotiation<Criterion> cn: criteriaNegotiation){
			Class<? extends Criterion> cnType = cn.getCriterionType();
			if (cnType.equals(c))
				return cn;
		}
		return null;
	}

	public CriterionNegotiation<Criterion> getCriterionNegotiation( Criterion c){
		for (CriterionNegotiation<Criterion> cn: criteriaNegotiation){
			Class<? extends Criterion> cnType = cn.getCriterionType();
			if (cnType.equals(c.getClass()))
				return cn;
		}
		return null;
	}

	public Class<? extends Criterion> openNewTopic(){
		for (Class<? extends Criterion> elem: this.criteriaPreferences.getValues()){
			if(!context.getDiscussedCriteria().contains(elem))
				return elem;
		}
		for (Class<? extends Criterion> elem: this.criteriaPreferences.getValues()){
			if(this.statedValues(elem))
				return elem;
		}
		return null;
	}

	public int optionUtility (Option option){
		int Utility = 0;
		for (Class<? extends Criterion> c: option.getCriteria()){
			// get the criterion rank 
			int rank = criteriaPreferences.getRank(c);
			// get the type of the criterion
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(c);
			// Utility = Sum(rank(c) * score(v_c)) 
			Utility += rank * criterion.getSelf().getScore(option.getValue(c));
		}
		return Utility;
	}

	public Criterion LeastScoredCriterion (O option, int dom){
		ArrayList<Criterion> nonAcceptedCriteria = new ArrayList<Criterion> ();
		// initialiser la valeur de minUtility
		//		Class<? extends Criterion> min= option.getCriteria().get(0);
		//		Criterion leastScored = option.getValue(min);
		//		int minUtility = criteriaPreferences.getRank(min) * 
		//				getCriterionNegotiation(min).getSelf().getScore(option.getValue(min));

		for (Class<? extends Criterion> cr: option.getCriteria()){
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(cr);
			if(!criterion.isSelfAcceptableCriterion(option.getValue(cr), dom))
				nonAcceptedCriteria.add(option.getValue(cr));

			//			// get the criterion rank 
			//			int rank = criteriaPreferences.getRank(cr);
			//			//			// get the type of the criterion
			//			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(cr);
			//			int utility = rank * criterion.getSelf().getScore(option.getValue(cr));
			//			if(minUtility < utility){
			//				minUtility = utility;
			//				leastScored = option.getValue(cr);

			//			}

		}
		nonAcceptedCriteria.sort(new Comparator<Criterion>() {
			@Override
			public int compare(Criterion c1, Criterion c2){
				return (getCriterionNegotiation(c1).getSelf().getScore(c1) - 
						getCriterionNegotiation(c2).getSelf().getScore(c2));
			}
		});
		return nonAcceptedCriteria.get(nonAcceptedCriteria.size()-1);
	}

	public O getPreferredOption(O firstOption, O secondOption) {

		// 2. en regardant les proposals
		return(optionUtility(firstOption) < optionUtility(secondOption)? secondOption:
			firstOption);
	}

	// Methods of the mental state

	public void updateProposal(OptionProposal proposal, Status status){
		for(OptionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				c.setStatus(status);
				//c.setIsSelf(value);
			}
		}
	}
	public void addCriterionProposal(CriterionProposal propose){
		CriterionNegotiation<Criterion> criterionNegotiation = getCriterionNegotiation(propose.criterion);
		// get the index of the criterionNegotiation of type
		int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
		criteriaNegotiation.get(indexList).propose(propose);
		// add the new proposal to the corresponding 	
	}

	public void updateOtherMentalState(Criterion less, Criterion more){
		Class<? extends Criterion> type =  new ValuePreference<Criterion>(less, more).getType();

		if(type != null){
			this.getCriterionNegotiation(type).addOther(less, more);
		}


	}

	public void updateOASMentalState(Criterion less, Criterion more){
		Class<? extends Criterion> type =  new ValuePreference<Criterion>(less, more).getType();

		if(type != null){
			this.getCriterionNegotiation(type).addOAS(less, more);

		}

	}

	public void printAllMentalState() {
		System.out.println("#### Options proposals ####");
		for(OptionProposal c : proposals)
			System.out.print(c.print() + "|");

		System.out.println(" \n \n #### Criteria mental model ####");	
		for(CriterionNegotiation<Criterion> c: criteriaNegotiation) {
			c.printMentalState();
			System.out.println();
		}
	}

	// Proposals methods 

	public boolean isAcceptableOption(Option option, int dom){

		List<Option> options =	(Arrays.asList(getOptions()));
		//getOptionsWithoutStatus(Proposal.Status.REJECTED);
		if(this.optionUtility(option)< 0 && dom>0)
			return false;
		else {
			List<Option> sortedOptions = sortOptions(options);
			if (dom== 0)
				return sortedOptions.indexOf(option)< sortedOptions.size()/2 ;

			if (dom >0) 
				return (sortedOptions.indexOf(option)< sortedOptions.size()/4);
			else 
				return ((sortedOptions.indexOf(option)< sortedOptions.size()/2)  || 
						(this.context.isInspeakerProposals(option, false, Proposal.Status.OPEN)&&
								!context.getLastProposal("OPEN").getValue().equals(option)));
		}

	}
	public boolean isAcceptable (Proposal proposal, int dom){

		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			boolean value = criterionNegotiation.
					isSelfAcceptableCriterion(criterion, dom);
			//System.out.println("criterion: "+ criterion+ " is acceptable ?: "+ value);
			return (value);
		}

		if(proposal instanceof OptionProposal){
			Option option = (Option) proposal.getValue();
			return(isAcceptableOption(option, dom));
		}
		return false;
	}
	/**
	 * 
	 * @param proposal
	 * @return ValuePreference<Criterion>
	 * 
	 * This method allows to a submissive agent to react to a non acceptable proposal.
	 */

	@SuppressWarnings("unchecked")
	public ValuePreference<Criterion> reactToRejectedProp(Proposal proposal){
		Criterion value = null;
		int dom = -2;
		//this.criteriaNegotiation.get(0).getSelf().getPreferences().get(0);

		if(proposal instanceof CriterionProposal){
			value = (Criterion) proposal.getValue();


		}
		if(proposal instanceof OptionProposal){
			value = LeastScoredCriterion((O) proposal.getValue(), dom);

		}
		CriterionNegotiation<Criterion> model = getCriterionNegotiation(value.getClass());
		// revoir cette m�thode c'est fu n'importe quoi !! 
		return(model.reactToCriterion(value, context.comminicatedProposals(true,model.getCriterionType())).
				orElse(new ValuePreference<Criterion>(value,
						model.getMostPreffered())));

	}
	public List<Option> sortOptions( List<Option> options) {

		// Supprimer les options qui contiennent au moins un critere rejeté.
		// pour chaque critere recuper la liste de criteres rejeté.
		// pour chaque valeur de l'option voir si elle appartient a cette liste de critere rejeté si oui remove lement

		options.sort(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2){
				return (optionUtility(o2) - optionUtility(o1));
			}
		});
		return options;
	}


	private ArrayList<Option> getOptionsWithoutStatus(Proposal.Status status) {
		ArrayList<Option> options = new ArrayList<Option>(Arrays.asList(this.getOptions()));

		List<Option> optionsWithStatus = getOptionsPropWithStatus(status);
		for (CriterionNegotiation<Criterion> CN : criteriaNegotiation) {

			List<Criterion> rejected = CN.getProposals(status);
			Iterator<Option> it = options.iterator();

			while (it.hasNext()){
				Option O = it.next();
				if(rejected.contains(O.getValue(CN.getCriterionType())) ||
						optionsWithStatus.contains(O)){		
					it.remove();
				}
			}
		}
		return options;
	}

	// getAcceptableOptions computes the utility of all the remaining options that are not rejected yet and returns
	//whom are acceptables following the agent preferences

	public ArrayList<Option> getAcceptableOptions (int dom){
		ArrayList<Option> acceptableOptions = new ArrayList<Option>();
		for (Option O: getOptionsWithoutStatus(Proposal.Status.REJECTED)){
			if(isAcceptableOption(O, dom))
				acceptableOptions.add(O);
		}
		return acceptableOptions;
	}

	@SuppressWarnings("unchecked")
	public void addProposal(Proposal proposal) {
		if (proposal instanceof CriterionProposal) {
			addCriterionProposal((CriterionProposal) proposal);
			this.getContext().updateDiscussedCriterion((Class<? extends Criterion>) proposal.getValue().getClass());

		}

		if(proposal instanceof OptionProposal)
			this.propose((OptionProposal) proposal);
	}


	public void updateProposalStatus(Proposal proposal, Status status) {
		// update the dialogue context
		if (proposal instanceof CriterionProposal) {
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			criteriaNegotiation.get(indexList).
			updateProposal((CriterionProposal) proposal, status);		
		}
		if(proposal instanceof OptionProposal){
			updateProposal((OptionProposal)proposal, status);
		}
	}

	public Status checkStatus(Option p) {
		for(OptionProposal prop : proposals) {
			if(prop.getValue().equals(p))
				return prop.status;	
		}
		return null;
	}

	public Status checkProposalStatus (Proposal proposal) {
		Status status = null;
		if(proposal instanceof CriterionProposal){
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			status = criteriaNegotiation.get(indexList).checkStatus((Criterion) proposal.getValue());

		}

		if(proposal instanceof OptionProposal){
			status = this.checkStatus((Option)proposal.getValue());
		}

		if(status == null)  throw 
		new NullPointerException(proposal.toString() + " is not proposed yet");
		return status;
	}

	// testing functions 

	public Proposal createProposal(Object o, boolean isSelf){
		if(o == null)
			return null;
		if(o instanceof Criterion)
			return new CriterionProposal(isSelf, (Criterion) o);
		if(o instanceof Option)
			return (new OptionProposal(isSelf,(Option) o));

		return null;

	}

	public Criterion mostPreferredCriterion (Class <? extends Criterion> criterion) {

		return (getCriterionNegotiation(criterion).getSelf().getMostPreferred());
	}

	public Criterion currentMostPreferredCriterion (Class <? extends Criterion> criterion, int dom) {

		return (getCriterionNegotiation(criterion).getTheCurrentMostPreffered(dom));
	}

	public Criterion currentMostPreferredCriterion (Criterion criterion, int relation) {

		return (getCriterionNegotiation(criterion.getClass()).getTheCurrentMostPreffered(relation));
	}

	public Option mostPreferredOption(){
		ArrayList<Option> options = new ArrayList<Option>(Arrays.asList(this.getOptions()));
		return(sortOptions(options).get(0));

	}

	public Option currentMostPreferredOption(){
		ArrayList<Option> options =	getOptionsWithoutStatus(Proposal.Status.REJECTED);
		if(!options.isEmpty())
			return(sortOptions(options).get(0));
		else 
			return (mostPreferredOption());
	}

	public CriterionProposal generateRandomCriterionProposal (Class <? extends Criterion> criterion, int dom){
		return (new CriterionProposal(true, currentMostPreferredCriterion(criterion, dom)));
	}

	public OptionProposal generateRandomOptionProposal(){
		return (new OptionProposal(true, currentMostPreferredOption()));

	}

	public ValuePreference<Criterion> getRandomPreference (Class<? extends Criterion> c){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(c);
		if (model.getSelectedPreferences(model.getSelf(),model.getOas()).isEmpty()){
			Class<? extends Criterion> topic =  openNewTopic();
			if(topic!= null)
				return getRandomPreference(topic) ;

			else {
				int criterionModel = new Random().nextInt((this.criteriaNegotiation.size()));
				CriterionNegotiation<Criterion> randomCN =this.criteriaNegotiation.get(criterionModel);
				int index = new Random().nextInt((randomCN.getSelf().getPreferences().size()));
				return (randomCN.getSelf().getPreferences().get(index));
			}
			//				 return (model.getSelf().getPreferences().
			//							get(new Random().
			//									nextInt(model.getSelf().getPreferences().size()-1)));
		}
		else 
			return model.getSelectedPreferences(model.getSelf(),model.getOas()).get(0) ;
	}

	public ValuePreference<Criterion> askUserPreference (Class<? extends Criterion> c){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(c);
		if (model.getOther().getPreferences().isEmpty())
			return new ValuePreference<Criterion>(null, null);
		else {
			for(ValuePreference<Criterion> pref :model.getSelectedPreferences(model.getSelf(),model.getOther())){
				if(!model.isIn(model.getOas(), pref))
					return pref;
			}
		}
		return (model.getSelectedPreferences(model.getSelf(),model.getOther())).get(0);
	}

	public ValuePreference<Criterion> reactAsk(int dom){

		PreferenceStatement user = null;

		if (this.context.getLastStatement("Ask",true) != null) {
			user = context.getLastStatement("Ask",true);

		}
		Class<? extends Criterion> utype = user.getType();
		ValuePreference<Criterion> userStatement = user.getStatedPreference();

		// what kind of type do you like ?
		if(userStatement.getLess() == null && userStatement.getMore() == null) {
			return new ValuePreference<Criterion>(null, 
					getCriterionNegotiation(utype).getMostPreffered());
		}
		// do you like c ?
		if(userStatement.getLess() == null || userStatement.getMore() == null) 
		{Criterion c= null;
		if (userStatement.getLess() == null)
			c =userStatement.getMore();
		if (userStatement.getMore() == null)
			c =userStatement.getLess();
		if(getCriterionNegotiation(c).getMostPreffered().equals(c))
			return new ValuePreference<Criterion> (null, c);

		if(getCriterionNegotiation(c).getLeastPreffered().equals(c))
			return new ValuePreference<Criterion> (c, null);
		if(getCriterionNegotiation(c).isAcceptableCriterion(c, dom, getCriterionNegotiation(c).getSelf()))
			return new ValuePreference<Criterion>(getCriterionNegotiation(c).getLeastPreffered(), c);
		else 
			return new ValuePreference<Criterion> (c,getCriterionNegotiation(c).getTheCurrentMostPreffered(dom));
		}
		//do you like less or more ?
		int moreScore = getCriterionNegotiation(utype).getSelf().getScore(userStatement.getMore());
		int lessScore = getCriterionNegotiation(utype).getSelf().getScore(userStatement.getLess());
		if(moreScore >= lessScore)
			return (new ValuePreference<>(userStatement.getLess(), userStatement.getMore()));
		else 

			return (new ValuePreference<>(userStatement.getMore(), userStatement.getLess()));

	}

	public ValuePreference<Criterion> reactUserStatement(String uttType){
		// trier par order de preference 
		// get score des elements de lastStaement 
		// enlever ceux qui sont dans oas

		if (this.context.getLastStatement(uttType,true) != null) {
			ValuePreference<Criterion> userStatement = context.getLastStatement(uttType,true).
					getStatedPreference();

			if(userStatement.getLess() == null && userStatement.getMore() == null){
				Criterion mostPref = this.getCriterionNegotiation(context.getLastStatement(uttType,true).
						getType()).getSelf().getMostPreferred();
				return (new ValuePreference<Criterion>(null, mostPref));
			}
			else{
				ValuePreference<Criterion> pref = computePreference(userStatement);
				return pref;
			}
		}
		// if there is no statement to react to, state the mostPreferred value 
		// of the current discussed criterion.

		Criterion best = this.getCriterionNegotiation(this.context.getCurrentDiscussedCriterion()).
				getMostPreffered();
		return new ValuePreference<Criterion>(null, best);


	}

	public ValuePreference<Criterion> reactToProposal(CriterionProposal p){
		Criterion criterion = p.getValue();
		CriterionNegotiation<Criterion> model = getCriterionNegotiation(criterion);

		return (model.reactToCriterion(criterion, 
				context.comminicatedProposals(true, model.criterionType)).
				orElse(new ValuePreference<Criterion>(null, model.getMostPreffered())));
	}

	// This method checks for a criterion, if all the values have been stated in the dialogue
	public boolean statedValues(Class<? extends Criterion> criterion){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation
				(criterion);
		return(!model.getSelectedPreferences(model.getSelf(), model.getOas()).isEmpty());
	}

	public ValuePreference<Criterion> computePreference(ValuePreference<Criterion> userStatement){

		if(userStatement.getLess() == null){
			CriterionNegotiation<Criterion> modelFromMore = this.getCriterionNegotiation
					(userStatement.getMore().getClass());
			return modelFromMore.reactToCriterion(userStatement.getMore(),
					context.comminicatedProposals(true, modelFromMore.criterionType)).
					orElse(new ValuePreference<Criterion> (null, modelFromMore.getSelf().getMostPreferred()));
		}
		if(userStatement.getMore() == null){
			CriterionNegotiation<Criterion> modelFromLess = this.getCriterionNegotiation
					(userStatement.getLess().getClass());
			return modelFromLess.reactToCriterion(userStatement.getLess(),
					context.comminicatedProposals(true, modelFromLess.criterionType)).
					orElse(new ValuePreference<Criterion> (null, modelFromLess.getSelf().getMostPreferred()));
		}

		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation
				(userStatement.getMore().getClass());
		// Check if its is equal to the mostPref value and it is not stated 

		if(userStatement.getMore().equals(model.getSelf().getMostPreferred()) && 
				!model.isInOAS(null, userStatement.getMore()))
			return new ValuePreference<Criterion> (null, userStatement.getMore());

		if(userStatement.getMore().equals(model.getSelf().getLeastPreferred()) && 
				!model.isInOAS(userStatement.getMore(), null))
			return new ValuePreference<Criterion> (userStatement.getMore(), null);

		if(userStatement.getLess().equals(model.getSelf().getMostPreferred()) && 
				!model.isInOAS(null, userStatement.getLess()))
			return new ValuePreference<Criterion> (null, userStatement.getLess());

		if(userStatement.getLess().equals(model.getSelf().getLeastPreferred()) && 
				!model.isInOAS(userStatement.getLess(), null))
			return new ValuePreference<Criterion> (userStatement.getLess(), null);

		// The agent reacts to the stated Preference
		ValuePreference<Criterion> c = (model.getSelf().
				isPreferred(userStatement.getLess(), userStatement.getMore()) ? userStatement: 
					new ValuePreference<Criterion>(userStatement.getMore(), userStatement.getLess()));
		// If the preference is not expressed 
		if (model.isInOAS(c.getLess(),c.getMore()))
			//ValuePreference<Criterion>  more =  
			return model.reactToCriterion(c.getMore(),
					context.comminicatedProposals(true, model.criterionType)).
					orElse(model.reactToCriterion(c.getLess(),
							context.comminicatedProposals(true, model.criterionType)).
							orElse(c));	
		return c;
	}


	public boolean allCriteriaAccepted(){
		for (CriterionNegotiation<Criterion> n: this.criteriaNegotiation){
			if(n.getProposals(Proposal.Status.ACCEPTED).isEmpty())
				return false;
		}
		return true;
	}
	// Called only after cheking that allCriteriaAccepted() is true
	public ArrayList<Criterion> lastAcceptedValues(){
		ArrayList<Criterion> accepted = new ArrayList<Criterion>();
		for (CriterionNegotiation<Criterion> n: this.criteriaNegotiation){
			int index = n.getProposals(Proposal.Status.ACCEPTED).size() -1;
			if(index>=0)
				accepted.add(n.getProposals(Proposal.Status.ACCEPTED).get(index));
		}
		return accepted;
	}
	@SuppressWarnings("unchecked")
	public List<Option> computeAcceptableOptions(int dom){
		ArrayList<Option> acceptedOptions= new ArrayList<Option>();
		ArrayList<Option> optionProposals = (ArrayList<Option>) getPossibleProposalOptions(dom);
		Map<Class<? extends Criterion>, List<Criterion>>  acceptedCriteria = this.acceptedCriteria();
		for(Class<? extends Criterion> cr : acceptedCriteria.keySet()){
			if(!acceptedCriteria.get(cr).isEmpty()){
				for(Option op: optionProposals){
					if(acceptedCriteria.get(cr).contains(op.getValue(cr)))
						acceptedOptions.add(op);
				}
				optionProposals.clear();
				optionProposals = (ArrayList<Option>) acceptedOptions.clone();
				acceptedOptions.clear();

			}
			
			//if(!acceptedOptions.isEmpty())
		}
		if(optionProposals.isEmpty())
			optionProposals.add(getOptionWithValue((Criterion)this.context.getLastCriterionProposal(Status.ACCEPTED).getValue()));

		return optionProposals;
	}

	public Map<Class<? extends Criterion>, List<Criterion>> acceptedCriteria(){
		Map<Class<? extends Criterion>, List<Criterion>> acceptedCriteria = 
				new HashMap<Class<? extends Criterion>, List<Criterion>>();
		for (CriterionNegotiation<Criterion> cr : this.criteriaNegotiation){
			acceptedCriteria.put(cr.criterionType, cr.getProposals(Status.ACCEPTED));
		}
		return acceptedCriteria;
	}

	public Option computeAcceptedOption(){
		ArrayList<Criterion> accepted = lastAcceptedValues();
		if(accepted.isEmpty())
			return null;
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

	public void printPrefs(){

		System.out.println(criteriaPreferences.printPreferences());

		for(CriterionNegotiation<Criterion> cr : criteriaNegotiation)
			System.out.println(cr.getSelf().printPreferences());

	}
	public boolean negotiationFailure(int dom){
		Statement lastUtterance = this.getContext().getLastStatement();
		if (getContext().getHistory().size()>= 15 && 
				!(lastUtterance.getUtteranceType().equals("Propose") || lastUtterance.getUtteranceType().equals("Accept")))

			return true;
		if(dom>=0)
			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty() || 
					getAcceptableOptions(dom).isEmpty());

		else
			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty());
	}

	public String getOptionFrVersion(String optionSimpleName){
		String fr = "";
		switch(optionSimpleName) {
		case "Cuisine":
			fr = "la cuisine";
			break;
		case "Cost":
			fr = "le prix";
			break;
		case "Ambiance":
			fr = "l'ambiance";
			break;
		case "Restaurant":
			fr = "le restaurant";
			break;
		default:
			System.out.println("THE VALUE "+ optionSimpleName+" DOESN'T EXIST");
			break;
		}
		return fr;
	}
	// This method is Nullable
	public List<Option> getPossibleProposalOptions(int dom){
		// Step1: compute acceptable options
		// Step2: delete proposed options
		// Step3: take an option with values acceptable by the user
		ArrayList<Option> accOptions = getAcceptableOptions(dom);
		ArrayList<Criterion> otherAcceptable = new ArrayList<Criterion>();
		ArrayList<Option> acceptable = new ArrayList<Option>();
		ArrayList<Option> removables = new ArrayList<Option>();


		for(Option op: accOptions){
			if(this.getContext().getProposals().contains(new OptionProposal(op)))
				removables.add(op);
		}
		accOptions.removeAll(removables);
		for(CriterionNegotiation<Criterion> cn: this.criteriaNegotiation ){
			otherAcceptable.addAll(cn.acceptableCriteria(-dom, cn.other));
		}
		if(otherAcceptable.isEmpty())
			return (this.sortOptions(accOptions));

		for(Option o: accOptions){
			for(Criterion oc: otherAcceptable){
				if(o.getValue(oc.getClass()).equals(oc) && !acceptable.contains(o))
					acceptable.add(o);
			}
		}

		acceptable.sort(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2){
				return (Collections.frequency(acceptable, o2) - Collections.frequency(acceptable,o1));
			}
		});

		return acceptable;
	}

	public boolean isProposed (Proposal proposal, Status status){

		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			return(criteriaNegotiation.get(indexList).
					isProposed((CriterionProposal) proposal, status));
		}

		if(proposal instanceof OptionProposal){
			for (OptionProposal p: proposals) {
				if(p.getValue().equals(proposal.getValue()) && p.getStatus().equals(status))
					return true;
			}
		}
		return false;
	}
	public Proposal computeProposal (int dom){
		CriterionNegotiation<Criterion> cr = getCriterionNegotiation(
				getContext().getCurrentDiscussedCriterion());

		if (cr.computeProposal(dom, context).isEmpty()){
			//			// First propose an option if there is any open new topic
			//			List<Option> options = getPossibleProposalOptions(dom);
			//			if(options.isEmpty()){
			//				cr = getCriterionNegotiation(openNewTopic());
			//				List<CriterionProposal> values= cr.computeProposal(dom);
			//				if(values.isEmpty())
			//				return new OptionProposal(computeAcceptedOption());
			//				
			//			}
			//			else
			//				return  new OptionProposal(true, options.get(0));
			//				
			cr = getCriterionNegotiation(openNewTopic());
			List<CriterionProposal> values= cr.computeProposal(dom, context);
			if(values.isEmpty()){
				List<Option> options = getPossibleProposalOptions(dom);
				if(options.isEmpty())
					return new OptionProposal(computeAcceptedOption());
				else
					return new OptionProposal(true, options.get(0));		
			}
			else
				return values.get(0);

		}
		else return cr.computeProposal(dom, context).get(0);
	}
}

