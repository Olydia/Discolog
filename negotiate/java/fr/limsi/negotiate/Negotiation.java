package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.restaurant.*;

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
	public DialogueContext context ;
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
	
	public Criterion LeastScoredCriterion (O option){
		// initialiser la valeur de minUtility
		Class<? extends Criterion> min= option.getCriteria().get(0);
		Criterion leastScored = option.getValue(min);
		int minUtility = criteriaPreferences.getRank(min) * 
				getCriterionNegotiation(min).getSelf().getScore(option.getValue(min));
		
		for (Class<? extends Criterion> cr: option.getCriteria()){
			// get the criterion rank 
			int rank = criteriaPreferences.getRank(cr);
//			// get the type of the criterion
			CriterionNegotiation<Criterion> criterion = getCriterionNegotiation(cr);
			int utility = rank * criterion.getSelf().getScore(option.getValue(cr));
			if(minUtility < utility){
				minUtility = utility;
				leastScored = option.getValue(cr);
				
			}
				
		}
		return leastScored;
	}
	
	public O getPreferredOption(O firstOption, O secondOption) {

		// 2. en regardant les proposals
		return(optionUtility(firstOption) < optionUtility(secondOption)? secondOption: firstOption);
	}

	// Methods of the mental state

	public boolean isInOther(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (less, more);
		Class<? extends Criterion> c =  p.getType();
		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (cn.getOther().getPreferences().contains(p));
	}

	public boolean isInself(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (less, more);
		Class<? extends Criterion> c =  p.getType();
		CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
		return (cn.getSelf().getPreferences().contains(p));
	}

	public boolean isInOAS(Criterion less, Criterion more) {
		ValuePreference<Criterion> p = new ValuePreference<Criterion> (less, more);
		Class<? extends Criterion> c =  p.getType();
		if(c !=null) {
			CriterionNegotiation<Criterion> cn = this.getCriterionNegotiation(c);
			return (cn.getOas().getPreferences().contains(p));
		}
		return false;
	}

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
			this.context.getListStatements().add(new Statement(less, more, true, "State"));
		}


	}

	public void updateOASMentalState(Criterion less, Criterion more){
		Class<? extends Criterion> type =  new ValuePreference<Criterion>(less, more).getType();

		if(type != null){
			this.getCriterionNegotiation(type).addOAS(less, more);
			this.context.getListStatements().add(new Statement(less, more, false, "State"));

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

	public boolean isAcceptableOption(Option option, int dom){

	List<Option> options =	(List<Option>) (Arrays.asList(getOptions()));
				//getOptionsWithoutStatus(Proposal.Status.REJECTED);
		if(this.optionUtility(option)< 0 && dom>=0)
			return false;
		else {
			List<Option> sortedOptions = sortOptions(options);
			if (dom==0)
				 return sortedOptions.indexOf(option)< sortedOptions.size()/2 ;
				
			if (dom >0) 
				return (sortedOptions.indexOf(option)< sortedOptions.size()/4);
			else 
				return ((sortedOptions.indexOf(option)< sortedOptions.size()/2)  || 
						checkStatus(option).equals(Proposal.Status.OPEN));
		}
	
	}
	public boolean isAcceptable (Proposal proposal, int dom){

		if (proposal instanceof CriterionProposal) {
			Criterion criterion = (Criterion) proposal.getValue();
			CriterionNegotiation<Criterion> criterionNegotiation =	
					getCriterionNegotiation(criterion);
			return (criterionNegotiation.isAcceptableCriterion(criterion, dom));
		}

		if(proposal instanceof OptionProposal){
			Option option = (Option) proposal.getValue();
			return(isAcceptableOption(option, dom));
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public ValuePreference<Criterion> reactToRejectedProp(Proposal proposal){
		Criterion value = Cuisine.ITALIAN;
		this.criteriaNegotiation.get(0).getSelf().getPreferences().get(0);
		
		if(proposal instanceof CriterionProposal){
			 value = (Criterion) proposal.getValue();


		}
		if(proposal instanceof OptionProposal){
			value = LeastScoredCriterion((O) proposal.getValue());

		}
		
		return(new ValuePreference<Criterion>(value, currentMostPreferredCriterion(value)));

			
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
		ArrayList<Option> options = new ArrayList<Option>((List<Option>) 
				Arrays.asList(this.getOptions()));
		
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
		this.context.updateProposals(proposal);
		if (proposal instanceof CriterionProposal) {
			addCriterionProposal((CriterionProposal) proposal);
			this.context.updateDiscussedCriterion((Class<? extends Criterion>) proposal.getValue().getClass());

		}

		if(proposal instanceof OptionProposal)
			this.propose((OptionProposal) proposal);
	}

	
	public void updateProposalStatus(Proposal proposal, Status status) {
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

	public CriterionProposal criterionProposal(Criterion c){
		if(c == null)
			return null;
		return (new CriterionProposal(c));
	}

	public OptionProposal optionProposal(Option o){
		if(o == null)
			return null;
		return (new OptionProposal(o));

	}

	public Criterion mostPreferredCriterion (Class <? extends Criterion> criterion) {

		return (getCriterionNegotiation(criterion).getSelf().getMostPreferred());
	}
	
	public Criterion currentMostPreferredCriterion (Class <? extends Criterion> criterion) {

		return (getCriterionNegotiation(criterion).getTheCurrentMostPreffered());
	}
	
	public Criterion currentMostPreferredCriterion (Criterion criterion) {

		return (getCriterionNegotiation(criterion.getClass()).getTheCurrentMostPreffered());
	}
	
	public Option mostPreferredOption(){
		ArrayList<Option> options = new ArrayList<Option>((List<Option>) 
				Arrays.asList(this.getOptions()));
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
	public ValuePreference<Criterion> getRandomPreference (Class<? extends Criterion> c){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(c);
		if (model.getPreference(model.getSelf(),model.getOas()) == null){
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
			return model.getPreference(model.getSelf(),model.getOas()) ;
		
		
				
	}

	public ValuePreference<Criterion> askUserPreference (Class<? extends Criterion> c){
		CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(c);
		return (model.getPreference(model.getSelf(),model.getOther()));
	}

	public ValuePreference<Criterion> reactUserStatement(String uttType){

		if (this.context.getLastStatement(uttType,true) != null) {
			ValuePreference<Criterion> userStatement = context.getLastStatement(uttType,true).getStatedPreference();
			
			if(userStatement.getLess() == null && userStatement.getMore() == null){
				Criterion mostPref = this.getCriterionNegotiation(context.getLastStatement(uttType,true).getType()).getSelf().
						getMostPreferred();
				return (new ValuePreference<Criterion>(null, mostPref));
			}
			else
				return (getPref(userStatement));
		}

		else return null;
	}


	public ValuePreference<Criterion> getPrefOnCriterion(Criterion userStatement){
		ValuePreference<Criterion> pref = new ValuePreference<Criterion>(null, userStatement);
		return (getPref(pref));
	}

	public ValuePreference<Criterion> reactToProposal(CriterionProposal p){
		Criterion criterion = p.getValue();
		return (getPrefOnCriterion(criterion));
	}


	public ValuePreference<Criterion> getPref(ValuePreference<Criterion> userStatement){
			
			if(userStatement.getLess() == null){
				CriterionNegotiation<Criterion> mostPref = this.getCriterionNegotiation(userStatement.getMore().getClass());
				return (mostPref.getSelf().reactToCriterion(userStatement.getMore()));
			}
			if(userStatement.getMore() == null){
				CriterionNegotiation<Criterion> leastPref = this.getCriterionNegotiation(userStatement.getLess().getClass());
				return (leastPref.getSelf().reactToCriterion(userStatement.getLess()));
			}
			
			CriterionNegotiation<Criterion> model = this.getCriterionNegotiation(userStatement.getMore().getClass());
			if(userStatement.getMore().equals(model.getSelf().getMostPreferred()))
				return (new ValuePreference<Criterion>(null, userStatement.getMore()));
			
			if(userStatement.getMore().equals(model.getSelf().getLeastPreferred()))
				return (new ValuePreference<Criterion>(userStatement.getMore(), null));
			
			return(model.getSelf().
					isPreferred(userStatement.getLess(), userStatement.getMore()) ? userStatement: 
						new ValuePreference<Criterion>(userStatement.getMore(), userStatement.getLess()));
		
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
	
	public boolean negotiationFailure(int dom){
		if(dom >= 0)
			return (getOptionsWithoutStatus(Proposal.Status.REJECTED).isEmpty() || getAcceptableOptions(dom).isEmpty());
		
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
	
}
