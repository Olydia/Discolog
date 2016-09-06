package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.restaurant.Cuisine;

public class DialogueContext {

	private List <Statement> listStatements;
	private ArrayList<Class<? extends Criterion>> discussedCriteria ;
	public Class<? extends Criterion> currentDiscussedCriterion; 	
	private Stack<Proposal> proposals;
	
	//private Proposal lastProposal;


	public DialogueContext() {
		this.listStatements =new ArrayList<Statement>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		this.currentDiscussedCriterion = Cuisine.class;
		this.proposals = new Stack<Proposal>();
	}
	public ArrayList<Class<? extends Criterion>> getDiscussedCriteria() {
		return discussedCriteria;
	}


	public Class<? extends Criterion> getCurrentDiscussedCriterion() {
		return currentDiscussedCriterion;
	}

	public Statement createStatement (Criterion less, Criterion more, boolean external, String utteranceType){
		return(new Statement(less, more, external, utteranceType));
	}

	public void updateDiscussedCriterion(Class<? extends Criterion> discussedCriterion) {
		if(!discussedCriteria.contains(discussedCriterion))
			this.discussedCriteria.add(discussedCriterion);
		currentDiscussedCriterion = discussedCriterion;
	}
	
	public void updateDiscussedCriterion(Criterion more, Criterion less) {
		ValuePreference<Criterion> pref = new ValuePreference<Criterion> (more, less);
		updateDiscussedCriterion(pref.getType());
	}


	public List<Statement> getListStatements() {
		return listStatements;
	}

	public void setListStatements(List<Statement> listStatements) {
		this.listStatements = listStatements;
	} 

	public Statement getLastStatement(String statementType, boolean external) {
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
	
	public boolean isInspeakerProposals(Object o, boolean who, Status status){
		for(Proposal p: this.getSpeakerProposals(who)){
			return (p.getValue().equals(o)&& p.getStatus().equals(status));
		}
		return false;
	}

}
