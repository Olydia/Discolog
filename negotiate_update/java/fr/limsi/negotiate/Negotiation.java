package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.PreferenceStatement.Acceptable;
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
	private int maxTurns;
	private int dom;
	public Class<O> type; 



	public Negotiation (CriterionNegotiation<Criterion>[] criteriaNegotiation, 
			CriteriaClassPrefModel<O> criteriaPreferences) {
		this.criteriaNegotiation = Arrays.asList(criteriaNegotiation);
		this.criteriaPreferences = criteriaPreferences;
		this.proposals = new ArrayList<OptionProposal>();
		this.context = new DialogueContext();
		this.type = criteriaPreferences.type;
	}

	public  void initiateNegotiation (int dominance, int maxTurn){
		setDom(dominance);
		setMaxTurns(maxTurn);

	}
	public int getDom() {
		return dom;
	}

	public void setDom(int dom) {
		this.dom = dom;
	}

	public DialogueContext getContext() {
		return context;
	}

	public void setMaxTurns (int turns){
		maxTurns = turns;
	}

	public int getMaxTurns() {
		return maxTurns;
	}

	public void propose (OptionProposal proposal) { 
		if(!proposals.contains(proposal))
			proposals.add(proposal);
		else
			updateProposal(proposal);
	}

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

	@SuppressWarnings("unchecked")
	public List<Proposal> getProposalsWithStatus(Status status){
		List<Proposal> prop =new ArrayList<Proposal>();
		for(CriterionNegotiation<Criterion> c: this.criteriaNegotiation){
			prop.addAll((Collection<? extends Proposal>) c.getProposals(status));
		}
		for (OptionProposal p: proposals){
			if (p.getStatus().equals(status))
				prop.add(p);
		}
		return prop;
	}

	@SuppressWarnings("unchecked")
	public List<Proposal> getProposalsWithoutStatus(Status status){
		List<Proposal> prop =new ArrayList<Proposal>();
		for(CriterionNegotiation<Criterion> c: this.criteriaNegotiation){
			prop.addAll((Collection<? extends Proposal>) c.getProposalswithoutStatus(status));
		}
		for (OptionProposal p: proposals){
			if (!p.getStatus().equals(status))
				prop.add(p);
		}
		return prop;
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
		//if(context.getDiscussedCriteria().containsAll(this.criteriaPreferences.getValues()))
		ArrayList<Class<? extends Criterion>> nonAccepted = new ArrayList<Class<? extends Criterion>>();
		for (Class<? extends Criterion> elem: this.criteriaPreferences.sortCriteria()){
			if(!context.getDiscussedCriteria().contains(elem))
				return elem;
			if(getCriterionNegotiation(elem).getProposals(Status.ACCEPTED).isEmpty())
				nonAccepted.add(elem);
		}
		for (Class<? extends Criterion> elem: nonAccepted)
			if(this.statedValues(elem))
				return elem;

		return null;
	}

	public int optionUtility (Option option){
		int Utility = 0;
		int spokenTurns = getProposalsWithoutStatus(Status.ACCEPTED).size();
		List<Class<? extends Criterion>> priorCriteria = this.criteriaPreferences.importantCriteria(getDom(), spokenTurns);
		for (Class<? extends Criterion> c:priorCriteria ){
			// get the criterion rank 
			int rank = criteriaPreferences.getRank(c);

			// get the type of the criterion
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(c);
			Utility += rank * criterion.getSelf().getScore(option.getValue(c));
		}
		return Utility;
	}

	public Criterion leastScoredCriterion (O option){
		ArrayList<Criterion> nonAcceptedCriteria = new ArrayList<Criterion> ();

		for (Class<? extends Criterion> cr: option.getCriteria()){
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(cr);
			if(!criterion.getSelf().isAcceptable(option.getValue(cr), getDom()))
				nonAcceptedCriteria.add(option.getValue(cr));

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


	// Methods of the mental state

	public void updateProposal(OptionProposal proposal){
		for(OptionProposal c: proposals){
			if(proposal.getValue().equals(c.getValue())) {
				int index = proposals.indexOf(c);
				proposals.set(index, proposal);
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

	/**
	 * add an information about other preferences
	 * @param Criterion
	 * @param isLikable
	 */

	public void updateOtherMentalState(Criterion criterion, PreferenceStatement.Acceptable isLikable){
		Class<? extends Criterion> type =  criterion.getClass();
		if(type != null)
			this.getCriterionNegotiation(type).addOther(criterion, isLikable);

	}
	/**
	 * add an information about what self communicated about its preferences
	 * @param Criterion
	 * @param isLikable
	 */

	public void updateOASMentalState(Criterion criterion, PreferenceStatement.Acceptable isLikable){
		Class<? extends Criterion> type =  criterion.getClass();

		if(type != null)
			this.getCriterionNegotiation(type).addOAS(criterion, isLikable);

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

	public boolean isAcceptableOption(Option option){

		List<Option> options =	(Arrays.asList(getOptions()));
		//getOptionsWithoutStatus(Proposal.Status.REJECTED);
		if(this.optionUtility(option)< 0 && getDom()>0)
			return false;
		else {
			List<Option> sortedOptions = sortOptions(options);
			if (getDom()== 0)
				return sortedOptions.indexOf(option)< sortedOptions.size()/2 ;

			if (getDom()>0) 
				return (sortedOptions.indexOf(option)< sortedOptions.size()/4);
			else 
				return ((sortedOptions.indexOf(option)< sortedOptions.size()/2)  || 
						(this.context.isInspeakerProposals(option, false, Status.OPEN)&&
								!context.getLastProposal(Status.OPEN).getValue().equals(option)));
		}

	}
	public boolean isAcceptable (Proposal proposal){
		int spokenTurns = getProposalsWithoutStatus(Status.ACCEPTED).size();
		List<Class<?extends Criterion>> importantCriteria = this.criteriaPreferences.importantCriteria(getDom(), spokenTurns);
		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			if(!importantCriteria.contains(criterion.getClass()))
				return true;

			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			boolean value = criterionNegotiation.
					getSelf().isAcceptable(criterion, getDom());
			//System.out.println("criterion: "+ criterion+ " is acceptable ?: "+ value);
			return (value);
		}

		if(proposal instanceof OptionProposal){
			Option option = (Option) proposal.getValue();
			return(isAcceptableOption(option));
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

	public ArrayList<Option> getAcceptableOptions (){
		ArrayList<Option> acceptableOptions = new ArrayList<Option>();
		for (Option O: getOptionsWithoutStatus(Proposal.Status.REJECTED)){
			if(isAcceptableOption(O))
				acceptableOptions.add(O);
		}
		return (ArrayList<Option>) sortOptions(acceptableOptions);
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


	public void updateProposal(Proposal proposal) {
		// update the dialogue context
		if (proposal instanceof CriterionProposal) {
			CriterionNegotiation<Criterion> criterionNegotiation = 
					getCriterionNegotiation((Criterion)proposal.getValue());
			// get the index of the criterionNegotiation of type
			int indexList = criteriaNegotiation.indexOf(criterionNegotiation);
			criteriaNegotiation.get(indexList).
			updateProposal((CriterionProposal) proposal);		
		}
		if(proposal instanceof OptionProposal){
			updateProposal((OptionProposal)proposal);
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
	public Proposal createProposal(Object o, boolean isSelf, Status status){
		if(o == null)
			return null;
		else{
			Proposal p = createProposal(o, isSelf);
			p.setStatus(status);
			return p;
		}

	}

	public Criterion mostPreferredCriterion (Class <? extends Criterion> criterion) {

		return (getCriterionNegotiation(criterion).getSelf().getMostPreferred());
	}

	public Criterion currentMostPreferredCriterion (Class <? extends Criterion> criterion) {

		return (getCriterionNegotiation(criterion).getTheCurrentMostPreffered(dom));
	}

	public Criterion currentMostPreferredCriterion (Criterion criterion) {

		return (getCriterionNegotiation(criterion.getClass()).getTheCurrentMostPreffered(getDom()));
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

	public CriterionProposal generateRandomCriterionProposal (Class <? extends Criterion> criterion){
		return (new CriterionProposal(true, currentMostPreferredCriterion(criterion)));
	}

	public OptionProposal generateRandomOptionProposal(){
		return (new OptionProposal(true, currentMostPreferredOption()));

	}

	public PreferenceStatement askUserPreference (Class<? extends Criterion> c){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(c);
		if(model.getOther().getAcceptableValues().isEmpty() && model.getOther().getNonAcceptableValues().isEmpty()){
			PreferenceStatement ask =  new PreferenceStatement(null, Acceptable.UNKNOWN, false, "Ask");
			ask.setType(c);
			return ask;
		}
		else{
			for(Criterion crit: model.getSelf().sortCriteria()){
				if(!model.getOther().getAcceptableValues().contains(c) || !model.getOther().getNonAcceptableValues().contains(c)){
					PreferenceStatement ask =  new PreferenceStatement(crit, Acceptable.UNKNOWN, false, "Ask");
					ask.setType(c);
					return ask;
				}

			}
		}
		PreferenceStatement ask =  new PreferenceStatement(null, Acceptable.UNKNOWN, false, "Ask");
		ask.setType(c);
		return ask;

	}

	public PreferenceStatement reactAsk(){

		PreferenceStatement user = null;

		if (this.context.getLastStatement("Ask",true) != null) {
			user = context.getLastStatement("Ask",true);

		}
		Class<? extends Criterion> utype = user.getType();
		CriterionNegotiation<Criterion> model = getCriterionNegotiation(utype);
		Criterion userStatement = user.getStatedValue();

		// what kind of type do you like ?
		if(userStatement == null) {
			return (new PreferenceStatement(model.getTheCurrentMostPreffered(getDom()),
					new Boolean(true), false, "State"));

		}
		// do you like c ?
		else  {

			return (new PreferenceStatement(userStatement,new Boolean(model.getSelf().isAcceptable(userStatement, getDom())), false, "State"));

		}

	}

	public PreferenceStatement reactUserStatement(String uttType){
		// trier par order de preference 
		// get score des elements de lastStaement 
		// enlever ceux qui sont dans oas

		if (this.context.getLastStatement(uttType,true) != null) {
			PreferenceStatement statement = context.getLastStatement(uttType,true);
			Criterion userStatement = statement.getStatedValue();

			CriterionNegotiation<Criterion> model = getCriterionNegotiation(userStatement);
			if(userStatement != null) {
				// reactToCriterion
				if(!model.getOas().getAcceptableValues().contains(userStatement) ||
						!model.getOas().getNonAcceptableValues().contains(userStatement))
					return (new PreferenceStatement(userStatement, 
							model.getSelf().isAcceptable(userStatement, getDom()), false, "State" ));
				else 
					return (new PreferenceStatement(model.getnewStatement(),
							model.getSelf().isAcceptable(userStatement, getDom()), false, "State" ));
			}	

		}
		Criterion best = this.getCriterionNegotiation(this.context.getCurrentDiscussedCriterion()).
				getMostPreffered();
		return (new PreferenceStatement(best, Boolean.TRUE, false, "State"));
	}

	// This method checks for a criterion, if all the values have been stated in the dialogue.
	public boolean statedValues(Class<? extends Criterion> criterion){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation
				(criterion);
		for(Criterion c: model.getSelf().getValues()){
			if(!model.getOas().getAcceptableValues().contains(c) ||
					!model.getOas().getNonAcceptableValues().contains(c))
				return true;
		}
		return false;
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
	public List<Option> computeAcceptableOptions(){
		ArrayList<Option> acceptedOptions= new ArrayList<Option>();
		ArrayList<Option> optionProposals = (ArrayList<Option>) getPossibleProposalOptions();
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

		return sortOptions(optionProposals);
	}

	public Map<Class<? extends Criterion>, List<Criterion>> acceptedCriteria(){
		Map<Class<? extends Criterion>, List<Criterion>> acceptedCriteria = 
				new HashMap<Class<? extends Criterion>, List<Criterion>>();
		for (CriterionNegotiation<Criterion> cr : this.criteriaNegotiation){
			acceptedCriteria.put(cr.criterionType, cr.getProposals(Status.ACCEPTED));
		}
		return acceptedCriteria;
	}

	/**
	 * computes an option defined with a criterion c 
	 * from the list of acceptable options. 
	 * @param  Criterion c
	 * @return option defined with a criterion c
	 */
	public Option computeAcceptedOption(Criterion c){
		List<Option> accepted = computeAcceptableOptions();
		for (Option o: accepted){
			if(o.getValue(c.getClass()).equals(c))
				return o;
		}
		return accepted.get(0);
	}

	/**
	 * computeAcceptedOption computes from the list of accepted values during the negotiation
	 * an option that contains all the accepted values 
	 * This is called when a submssive agent proposes
	 * @return option whith accepted values
	 */
	public Option computeAcceptedOption(){
		List<Criterion> accepted = lastAcceptedValues();
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
	public boolean negotiationFailure(){
		Statement lastUtterance = this.getContext().getLastStatement();
		if (getContext().getHistory().size()>= 20 && 
				!(lastUtterance.getUtteranceType().equals("Propose") || lastUtterance.getUtteranceType().equals("Accept")))

			return true;
		if(getDom()>=0)
			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty() || 
					getAcceptableOptions().isEmpty());

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
	public List<Option> getPossibleProposalOptions(){
		// Step1: compute acceptable options
		// Step2: delete proposed options
		// Step3: take an option with values acceptable by the user
		int otherDom = -getDom();
		ArrayList<Option> accOptions = getAcceptableOptions();
		ArrayList<Criterion> otherAcceptable = new ArrayList<Criterion>();
		ArrayList<Option> acceptable = new ArrayList<Option>();
		ArrayList<Option> removables = new ArrayList<Option>();


		for(Option op: accOptions){
			if(this.getContext().getProposals().contains(new OptionProposal(op)))
				removables.add(op);
		}
		accOptions.removeAll(removables);
		for(CriterionNegotiation<Criterion> cn: this.criteriaNegotiation ){
			otherAcceptable.addAll(cn.getOther().acceptableCriteria(otherDom));
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

	public Proposal computeProposal (){
		CriterionNegotiation<Criterion> cr = getCriterionNegotiation(
				getContext().getCurrentDiscussedCriterion());
		List<CriterionProposal> currentProposals=cr.computeProposal(getDom(), context);
		if (currentProposals.isEmpty()){				
			cr = getCriterionNegotiation(openNewTopic());
			List<CriterionProposal> values= cr.computeProposal(getDom(), context);
			if(values.isEmpty()){
				List<Option> options = getPossibleProposalOptions();
				if(options.isEmpty())
					return new OptionProposal(true,computeAcceptableOptions().get(0));
				else
					return new OptionProposal(true, options.get(0));		
			}
			else
				return values.get(0);

		}
		else return currentProposals.get(0);
	}
}

