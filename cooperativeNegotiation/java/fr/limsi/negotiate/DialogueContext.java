package fr.limsi.negotiate;

import java.util.*;

public class DialogueContext {
	private List<NegoUtterance> history;
	private List<Class<? extends Criterion>> discussedCriteria; 
	
	public DialogueContext() {
		this.history =new ArrayList<NegoUtterance>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
	}
	public List<Class<? extends Criterion>> getDiscussedCriteria() {
		return discussedCriteria;
	}

	public void setDiscussedCriteria(List<Class<? extends Criterion>> discussedCriteria) {
		this.discussedCriteria = discussedCriteria;
	}

	public void setHistory(List<NegoUtterance> history) {
		this.history = history;
	}

	
	public List<NegoUtterance> getHistory() {
		return history;
	}
	public void addUtt (NegoUtterance ut){
		this.history.add(ut);
	}

	public NegoUtterance getLastMove(){
		return history.get(history.size()-1);
	}
	
	public NegoUtterance getLastMove(boolean external){
		for(int i=history.size()-1; i>=0; i--){
			if(history.get(i).isExtrenal()== external)
				return history.get(i);
		}
		return null;
	}
	
	public Class<? extends Criterion> getCurrentDisucussedCriterion(){
		return this.discussedCriteria.get(discussedCriteria.size() -1);
	}

	public void updateDiscussion(Class<? extends Criterion> newCriterion){
		this.discussedCriteria.add(newCriterion);
	}


}