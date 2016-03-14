package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.restaurant.Cuisine;

public class DialogueContext {

	private List <Statement> listStatements;
	private Class<? extends Criterion> discussedCriterion ;
	
	public DialogueContext() {
		this.listStatements =new ArrayList<Statement>();
		discussedCriterion = Cuisine.class;
	}
	
	public Class<? extends Criterion> getDiscussedCriterion() {
		return discussedCriterion;
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

	public Statement getLastUserStatement(){
		for (int i = listStatements.size() - 1 ; i >= 0 ; i--)
			if(listStatements.get(i).isExternal())
				return(listStatements.get(i));
		
		return null;

	}
	
	public Statement getLastAgentStatement(){
		for (int i = listStatements.size() - 1 ; i >= 0 ; i--)
			if(!listStatements.get(i).isExternal())
				return(listStatements.get(i));
		
		//return new Statement(null, null, false);
		return null;
	}

}
