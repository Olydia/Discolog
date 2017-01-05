package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.NegoUtterance.UtType;

public class DialogueContext {
	
	private List<NegoUtterance> history;
	private List<Class<? extends Criterion>> discussedCriteria; 
		private List<Class<? extends Criterion>> closedCriteria; 

	
	public DialogueContext() {
		
		this.history =new ArrayList<NegoUtterance>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		this.setClosedCriteria(new ArrayList<Class<? extends Criterion>>());
	}
	
	public List<Class<? extends Criterion>> getRemainDiscussedCrt() {
		
		 List<Class<? extends Criterion>> remain= new ArrayList<Class<? extends Criterion>>();
		 
		 if(closedCriteria.isEmpty())
			 return discussedCriteria;
		
		 for( Class<? extends Criterion> elm: discussedCriteria)
			if(!closedCriteria.contains(elm))
				remain.add(elm);
		
		return remain;
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
		
		updateDiscussion(ut);
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

	public void updateDiscussion(NegoUtterance newUtt){
		
		if(newUtt.getValueType()!= null){
			if(newUtt.getType().equals(UtType.ACCEPT))
				this.updateClosed(newUtt.getValueType());
			
			else if(!newUtt.getValueType().equals(getCurrentDisucussedCriterion()))		
				this.discussedCriteria.add(newUtt.getValueType());
		}
	}
	
	public Class<? extends Criterion> openNewDiscussion(List <Class<? extends Criterion>> criteria){
		List <Class<? extends Criterion>> crit = new ArrayList <Class<? extends Criterion>> ();
		for(Class<? extends Criterion> c: criteria){
			if(! getDiscussedCriteria().contains(c))
				crit.add(c);
		}
		crit.addAll(getRemainDiscussedCrt());
		
		if(crit.isEmpty())
			return null ;
		
		return crit.get(0);
	}
	
	public List<Class<? extends Criterion>> getClosedCriteria() {
		return closedCriteria;
	}
	
	public void setClosedCriteria(List<Class<? extends Criterion>> closedCriteria) {
		this.closedCriteria = closedCriteria;
	}

	public void  updateClosed(Class<? extends Criterion> criterion){
		this.discussedCriteria.add(criterion);
	}
	
}