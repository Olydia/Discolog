package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.limsi.negotiate.restaurant.Cuisine;

public class DialogueContext {

	private List <Statement> listStatements;
	private Class<? extends Criterion> discussedCriterion ;
	private Stack<Proposal> proposals;

	
	public DialogueContext() {
		this.listStatements =new ArrayList<Statement>();
		this.discussedCriterion = Cuisine.class;
		this.proposals = new Stack<Proposal>();
	}
	
	public Class<? extends Criterion> getDiscussedCriterion() {
		return discussedCriterion;
	}
	
	public Statement createstatement (Criterion less, Criterion more, boolean external, String utteranceType){
		return(new Statement(less, more, external,utteranceType));
	}
	
	public void updateDiscussedCriterion(Class<? extends Criterion> discussedCriterion) {
		this.discussedCriterion = discussedCriterion;
	}
	public void updateDiscussedCriterion(Criterion more, Criterion less) {
		ValuePreference<Criterion> pref = new ValuePreference<Criterion> (more, less);
		this.discussedCriterion = pref.getType();

	}

	
	public List<Statement> getListStatements() {
		return listStatements;
	}

	public void setListStatements(List<Statement> listStatements) {
		this.listStatements = listStatements;
	} 

	public Statement getLastStatement(String statementType, boolean external){
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
	
	public Proposal getLastProposal( String status){
		for(int i= proposals.size()-1; i>=0; i --){
			if(proposals.get(i).getStatus().toString().equals(status))
			return proposals.get(i);
			
		}
		return null;
	}

}
