package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import fr.limsi.negotiate.Proposal.Status;

public class DialogueContext {

	private List <PreferenceStatement> listStatements;
	private ArrayList<Class<? extends Criterion>> discussedCriteria ;
	public Class<? extends Criterion> currentDiscussedCriterion; 	
	private Stack<Proposal> proposals;
	private ArrayList<CommunicatedProp> acceptedValues;
	private ArrayList<CommunicatedProp> NonacceptedValues;
	private List<Statement> history;
	

	private int cmpState = 0;

	//private Proposal lastProposal;

	
	@SuppressWarnings("serial")
	public DialogueContext() {
		this.listStatements =new ArrayList<PreferenceStatement>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		//this.currentDiscussedCriterion = Cuisine.class;
		this.proposals = new Stack<Proposal>();
		this.history = new ArrayList<Statement>();
		// communicatedProposal values attributions: oas: true / other: false
		this.acceptedValues = new ArrayList<CommunicatedProp>()
								{{add(new CommunicatedProp(true));
								  add(new CommunicatedProp(false)); }};
		this.NonacceptedValues = new ArrayList<CommunicatedProp>()
									{{add(new CommunicatedProp(true));
									  add(new CommunicatedProp(false)); }};
	}
	
	public List<CommunicatedProp> getNonacceptedValues() {
		return NonacceptedValues;
	}
	
	public List<Statement> getHistory() {
		return history;
	}
	
	public Statement getLastStatement(){
		return history.get(history.size()-1);
	}
	public ArrayList<Class<? extends Criterion>> getDiscussedCriteria() {
		return discussedCriteria;
	}
	// 
	public void addStatement(Statement utt){
		history.add(utt);
		if(utt.getUtteranceType().equals("State"))
			setCmpState(getCmpState() + 1);
		else 
			setCmpState(0);
		
		if(utt instanceof PreferenceStatement){
			getListStatements().add((PreferenceStatement) utt);
            updateDiscussedCriterion(((PreferenceStatement) utt).getType());
		}
		
		if(utt instanceof ProposalStatement)	
			this.updateProposals(((ProposalStatement) utt).getProp());
	}
	
	public Class<? extends Criterion> getCurrentDiscussedCriterion() {
		return currentDiscussedCriterion;
	}

	public void updateDiscussedCriterion(Class<? extends Criterion> discussedCriterion) {
		if(!discussedCriteria.contains(discussedCriterion))
			this.discussedCriteria.add(discussedCriterion);
		currentDiscussedCriterion = discussedCriterion;
	}

	public List<PreferenceStatement> getListStatements() {
		return listStatements;
	}

	public void setListStatements(List<PreferenceStatement> listStatements) {
		this.listStatements = listStatements;
	} 

	public PreferenceStatement getLastStatement(String statementType, boolean external) {
		for (int i = listStatements.size() - 1 ; i >= 0 ; i--)
			if(listStatements.get(i).isExternal()== external && 
			listStatements.get(i).utteranceType.equals(statementType))
				return(listStatements.get(i));

		return null;

	}

	//	public Statement getLastAgentStatement(){
	//		for (int i = listStatements.size() - 1 ; i >= 0 ; i--)
	//			if(!listStatements.get(i).isExternal())
	//				return(listStatements.get(i));
	//		
	//		//return new Statement(null, null, false);
	//		return null;
	//	}

	public Stack<Proposal> getProposals() {
		return proposals;
	}

	public void updateProposals(Proposal proposal) {
		this.proposals.push(proposal);
		if(proposal.getStatus().equals(Status.OPEN)|| proposal.getStatus().equals(Status.ACCEPTED))
			updateCommunicatedProp(acceptedValues, proposal);
		if(proposal.getStatus().equals(Status.REJECTED))
			updateCommunicatedProp(NonacceptedValues, proposal);
		
	}
	
	public void updateCommunicatedProp(ArrayList<CommunicatedProp> propos, Proposal p){
		for(CommunicatedProp cp: propos){
			if(cp.isSelf() == p.isSelf)
				cp.getProp().add(p);
		}
	}
	public Proposal getLastProposal(String status) {
		for(int i= proposals.size()-1; i>=0; i --){
			if(proposals.get(i).getStatus().toString().equals(status))
				return proposals.get(i);

		}
		return null;
	}
	public List<Proposal> getSpeakerProposals(boolean who){
		List<Proposal> whoProp = new ArrayList<Proposal>();
		for(Proposal p: this.getProposals())
			if(p.isSelf()==(who))
				whoProp.add(p);

		return whoProp;
	}
	public List<Criterion> comminicatedProposals (boolean who, Class<?extends Criterion> type){
		List<Criterion> values = new ArrayList<Criterion>();
		for(Proposal p: this.getSpeakerProposals(who)){
			if(p.getValue().getClass().equals(type))
				if(isAcceptable(p, who) || isNotAcceptable(p, who))
					values.add((Criterion) p.getValue());
		}
		return values;
	}

	public boolean isAcceptable(Object o, boolean who){
		return (isInspeakerProposals(o, who, Status.OPEN) || 
				isInspeakerProposals(o, who, Status.ACCEPTED));
	}

	public boolean isNotAcceptable(Object o, boolean who){
		return (isInspeakerProposals(o, who, Status.REJECTED));
	}

	public boolean isInspeakerProposals(Object o, boolean who, Status status){
		for(Proposal p: this.getSpeakerProposals(who)){
			return (p.getValue().equals(o)&& p.getStatus().equals(status));
		}
		return false;
	}


	public ArrayList<CommunicatedProp> getAcceptedValues() {
		return acceptedValues;
	}

	public int getCmpState() {
		return cmpState;
	}
	public void setCmpState(int cmpState) {
		this.cmpState = cmpState;
	}

	public class CommunicatedProp {
		private boolean isSelf;
		private ArrayList<Proposal> prop;
		
		public boolean isSelf() {
			return isSelf;
		}

		public void setSelf(boolean isSelf) {
			this.isSelf = isSelf;
		}

		public ArrayList<Proposal> getProp() {
			return prop;
		}

		public void setProp(ArrayList<Proposal> prop) {
			this.prop = prop;
		}

		public CommunicatedProp(boolean isSelf) {
			this.isSelf = isSelf;
			this.prop = new ArrayList<Proposal> ();
		}
		
		public CommunicatedProp(boolean isSelf, ArrayList<Proposal> prop) {
			this.isSelf = isSelf;
			this.prop = prop;
		}
		
	}

}