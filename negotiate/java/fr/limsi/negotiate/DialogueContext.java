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
	private List<Statement> history;
	private int cmpState = 0;

	//private Proposal lastProposal;

	/**
	 * calculates the number of consecuvtive proposals made by a speaker
	 * @param external: true if is user, otherwiser agent
	 * @return if the max of proposals defined to minimum 2 is reached
	 */
	public boolean isMaxProposals(boolean external) {
		List<Statement> speaker = getSpeakerStatements(external);
		int cpt = 0;
		if(speaker.size()<2)
			return false;
		else{
			for(int i=speaker.size()-1; i>0; i--){
				if(speaker.get(i).getUtteranceType().equals("Propose")&&
					speaker.get(i-1).getUtteranceType().equals("Propose"))
					cpt++;
				else
					cpt =0;
					
			}
		}
		return (cpt>=1);

	}

	public DialogueContext() {
		this.listStatements =new ArrayList<PreferenceStatement>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		//this.currentDiscussedCriterion = Cuisine.class;
		this.proposals = new Stack<Proposal>();
		this.history = new ArrayList<Statement>();
		// communicatedProposal values attributions: oas: true / other: false

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

	public boolean isProposed(Proposal proposal){
		for(Proposal p: proposals){
			if(p.getValue().equals(proposal.getValue()))
				return true;
		}
		return false;
	}

	public boolean isProposed (Proposal proposal, Status status){
		for(Proposal p: proposals){
			if(p.getValue().equals(proposal.getValue()) && p.getStatus().equals(proposal.getStatus()))
				return true;
		}
		return false;

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
	public List<Proposal> getProposals(Status status){
		List<Proposal> props = new ArrayList<Proposal>();
		for(Proposal proposal: proposals){
			if(proposal.getStatus().equals(status))
				props.add(proposal);
		}
		return props;
	}

	public void updateProposals(Proposal proposal) {
		//		if(!this.proposals.isEmpty()){
		//			if(this.proposals.peek().status.equals(Status.OPEN) && proposal.status.equals(Status.OPEN))
		//				setCmpProposal(cmpProposal +1);
		//			else 
		//				setCmpProposal(0);
		//		}
		this.proposals.push(proposal);

	}

	public Proposal getLastProposal(Status status) {
		for(int i= proposals.size()-1; i>=0; i --){
			if(proposals.get(i).getStatus().equals(status))
				return proposals.get(i);

		}
		return null;
	}

	public Proposal getLastCriterionProposal(Status status) {
		for(int i= proposals.size()-1; i>=0; i --){
			if(proposals.get(i) instanceof CriterionProposal && proposals.get(i).getStatus().toString().equals(status))
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
	public List<Statement> getSpeakerStatements(boolean isExternal){
		List<Statement> speaker = new ArrayList<Statement>();
		for (Statement s: history){
			if(s.isExternal() == isExternal)
				speaker.add(s);
		}
		return speaker;		

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
	public int takedTurns(boolean external){
		int cmp =0;
		for(Statement st: history){
			if(st.isExternal() == external)
				cmp ++;
		}
		return cmp;
	}

}