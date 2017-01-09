package fr.limsi.negotiate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import fr.limsi.negotiate.NegoUtterance.UtType;

public class DialogueContext {

	private List<NegoUtterance> history;
	private List<Class<? extends Criterion>> discussedCriteria; 
	private List<Class<? extends Criterion>> closedCriteria; 
	private Class<? extends Option> topic;

	public DialogueContext(Class<? extends Option> topic) {

		this.history =new ArrayList<NegoUtterance>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		this.setClosedCriteria(new ArrayList<Class<? extends Criterion>>());
		this.setTopic(topic);
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
		if  (!(ut.getValue() instanceof OptionProposal))
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
		if(discussedCriteria.isEmpty())
			this.discussedCriteria.add(newUtt.getValueType());

		else if(newUtt.getType().equals(UtType.ACCEPT))
			this.updateClosed(newUtt.getValueType());

		else if(!discussedCriteria.contains(newUtt.getValueType()))
			this.discussedCriteria.add(newUtt.getValueType());


	}

	public List<Class<? extends Criterion>> getPossibleDiscussions(List <Class<? extends Criterion>> criteria){
		List <Class<? extends Criterion>> crit = new ArrayList <Class<? extends Criterion>> ();
		for(Class<? extends Criterion> c: criteria){
			if(! getDiscussedCriteria().contains(c))
				crit.add(c);
		}
		crit.addAll(getRemainDiscussedCrt());

		return crit;
	}
	Class<? extends Criterion> openNewDiscussion(List <Class<? extends Criterion>> criteria){
		List<Class<? extends Criterion>> discussions = getPossibleDiscussions(criteria);
		if(discussions.isEmpty())
			return null;
		else
			return discussions.get(0);
	}

	public List<Class<? extends Criterion>> getClosedCriteria() {
		return closedCriteria;
	}

	public void setClosedCriteria(List<Class<? extends Criterion>> closedCriteria) {
		this.closedCriteria = closedCriteria;
	}

	public void  updateClosed(Class<? extends Criterion> criterion){
		this.discussedCriteria.add(criterion);
		if(criterion.equals(getCurrentDisucussedCriterion()))
			openNewDiscussion(getElements());
	}

	public Class<? extends Option> getTopic() {
		return topic;
	}

	public void setTopic(Class<? extends Option> topic) {
		this.topic = topic;
	}
	public List<Class<? extends Criterion>> getElements() {
		try {
			Method m = topic.getDeclaredMethod("getCriteria");
			Object[] v = topic.getEnumConstants();
			m.setAccessible(true);
			@SuppressWarnings("unchecked")
			List<Class<? extends Criterion>> value = (List<Class<? extends Criterion>>)m.invoke(v[0]);

			return (value);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
}