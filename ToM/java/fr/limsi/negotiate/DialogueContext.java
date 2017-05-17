package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.NegoUtterance.UtType;
import fr.limsi.negotiate.Proposal.Status;

public class DialogueContext {

	private List<NegoUtterance> history;
	private List<Class<? extends Criterion>> discussedCriteria; 
	private List<Class<? extends Criterion>> closedCriteria; 
	private List<Class<? extends Criterion>> topicValues;

	public DialogueContext(List<Class<? extends Criterion>> topicValues) {

		this.history =new ArrayList<NegoUtterance>();
		this.discussedCriteria = new ArrayList<Class<? extends Criterion>>();
		this.setClosedCriteria(new ArrayList<Class<? extends Criterion>>());
		this.setTopicValues(topicValues);
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
		if  (!(ut.getValue() instanceof OptionProposal && !ut.getType().equals(UtType.ACCEPTPROPOSE)))
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
		Class<? extends Criterion> newDi = newUtt.getValueType();
		if(discussedCriteria.isEmpty())
			this.discussedCriteria.add(newDi);

		else if(newUtt.getType().equals(UtType.ACCEPT) || newUtt.getType().equals(UtType.ACCEPTPROPOSE))
			this.closeDiscussion(newDi);


		else if(discussedCriteria.contains(newDi)){
			discussedCriteria.remove(newDi);
			this.discussedCriteria.add(newDi);

		}
		else 	this.discussedCriteria.add(newDi);

	}

	public List<Class<? extends Criterion>> getPossibleDiscussions(List <Class<? extends Criterion>> criteria){
		List <Class<? extends Criterion>> crit = new ArrayList <Class<? extends Criterion>> ();
		for(Class<? extends Criterion> c: criteria){
			if(!getDiscussedCriteria().contains(c))
				crit.add(c);
		}
		crit.addAll(getRemainDiscussedCrt());

		return crit;
	}
	Class<? extends Criterion> openNewDiscussion(List <Class<? extends Criterion>> criteria){
		List<Class<? extends Criterion>> discussions = getPossibleDiscussions(criteria);
		if(discussions.isEmpty())
			return null;
		else{
			Class<? extends Criterion> newD = discussions.get(0);
			this.discussedCriteria.add(newD);
			return newD;
		}
	}

	public List<Class<? extends Criterion>> getClosedCriteria() {
		return closedCriteria;
	}

	public void setClosedCriteria(List<Class<? extends Criterion>> closedCriteria) {
		this.closedCriteria = closedCriteria;
	}

	public void  closeDiscussion(Class<? extends Criterion> criterion){
		this.closedCriteria.add(criterion);
		if(criterion.equals(getCurrentDisucussedCriterion()))
			openNewDiscussion(this.topicValues);
	}

	public NegoUtterance getLastUtterance(boolean isExternal, UtType type){
		for (int i = history.size()-1; i>= 0; i--){
			NegoUtterance utt = history.get(i);
			if(utt.isExtrenal() == isExternal && utt.getType().equals(type))
				return utt;
		}
		return null;

	}

	public Proposal getLastProposal(){
		for (int i = history.size()-1; i>= 0; i--){
			NegoUtterance utt = history.get(i);
			if(isProposal(utt))
				return  (Proposal) utt.getValue();
		}
		return null;

	}

	public boolean isProposal(NegoUtterance utterance){
		UtType last = utterance.getType();
		return (last.equals(UtType.PROPOSE) ||last.equals(UtType.REJECTPROPOSE) || last.equals(UtType.ACCEPTPROPOSE));
	}

	public List<Proposal> getNegotiationMoves(){
		List<Proposal> moves = new ArrayList<Proposal>();
		for (NegoUtterance utt : history){
			if(utt instanceof NegotiationMove)
				moves.add((Proposal)utt.getValue());
		}
		return moves;	
	}

	public List<Proposal> getNonAcceptedProposals(){
		List<Proposal> moves = new ArrayList<Proposal>();

		for (NegoUtterance utt : history){
			if(utt instanceof NegotiationMove && !(utt.getType().equals(UtType.REJECT) || utt.getType().equals(UtType.REJECTSTATE))){
				Proposal p = null;

				if(utt.getType().equals(UtType.ACCEPT))

					p = ((NegotiationMove) utt).getProposal();

				else if(utt.getType().equals(UtType.ACCEPTPROPOSE)){

					p = (Proposal) ((ProposalMove) utt).getOther();
					moves.add(((ProposalMove) utt).getProposal());
				}
				// not an Accept nor AcceptPropose
				if(p == null)
					moves.add(((NegotiationMove) utt).getProposal());

				else{
					int index = moves.lastIndexOf(p);
					moves.remove(index);
				}
			}
		}
		return moves;
	}

	public void clearNegotiation(){
		this.history.clear();
		this.discussedCriteria.clear();
		this.closedCriteria.clear();
	}

	public Proposal otherProposal(Proposal current){
		boolean isExternal = !current.isSelf;
		if(current instanceof CriterionProposal){
			Proposal previous = (Proposal)getLastMove(isExternal).getValue();
			if(previous instanceof OptionProposal){
				@SuppressWarnings("unchecked")
				Class<? extends Criterion> type = (Class<? extends Criterion>) current.getValue().getClass();
				Option o = (Option)previous.getValue();
				if(o.getValue(type).equals(current.getValue()))
					return previous;
			}
		}
		return null;
	}

	public List<Class<? extends Criterion>> getTopicValues() {
		return topicValues;
	}

	public void setTopicValues(List<Class<? extends Criterion>> topicValues) {
		this.topicValues = topicValues;
	}


}