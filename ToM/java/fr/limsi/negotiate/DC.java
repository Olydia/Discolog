package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;
import fr.limsi.negotiate.lang.*;


public class DC {

	private List<NegotiationUtterance> history;
	private List<Class<? extends Criterion>> discussedCriteria; 
	private List<Class<? extends Criterion>> closedCriteria; 
	private List<Class<? extends Criterion>> topicValues;

	public DC(List<Class<? extends Criterion>> topicValues) {

		this.history =new ArrayList<NegotiationUtterance>();
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

	public void setHistory(List<NegotiationUtterance> history) {

		this.history = history;
	}


	public List<NegotiationUtterance> getHistory() {

		return history;
	}

	public void addUtt (NegotiationUtterance ut){
		if  (!(ut.getValue() instanceof OptionProposal && !ut.getType().equals(ut instanceof AcceptPropose)))
			updateDiscussion(ut);

		this.history.add(ut);
	}

	public NegotiationUtterance getLastMove(){

		return history.get(history.size()-1);
	}

	public NegotiationUtterance getLastMove(boolean external){

		for(int i=history.size()-1; i>=0; i--){
			if(history.get(i).getExternal()== external)
				return history.get(i);
		}
		return null;
	}



	public Class<? extends Criterion> getCurrentDisucussedCriterion(){

		return this.discussedCriteria.get(discussedCriteria.size() -1);
	}

	@SuppressWarnings("unchecked")
	Class<? extends Criterion>  getValueType(NegotiationUtterance newUtt){
		if(newUtt instanceof PreferenceUtterance)
			return ((PreferenceUtterance) newUtt).getCriterion();

		return (Class<? extends Criterion>) ((ProposalUtterance) newUtt).getProposal().getValue().getClass();
	}


	public void updateDiscussion(NegotiationUtterance newUtt){
		Class<? extends Criterion> newDi = getValueType(newUtt);
		if(discussedCriteria.isEmpty())
			this.discussedCriteria.add(newDi);

		else if(newUtt instanceof Accept || newUtt instanceof AcceptPropose)
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

	public Proposal getLastProposal(){
		for (int i = history.size()-1; i>= 0; i--){
			NegotiationUtterance utt = history.get(i);
			if(utt instanceof ProposalUtterance)
				return  (Proposal) utt.getValue();
		}
		return null;

	}

	public List<Proposal> getNonAcceptedProposals(){
		List<Proposal> moves = new ArrayList<Proposal>();

		for (NegotiationUtterance utt : history){
			if(utt instanceof ProposalUtterance) {
				
				if(utt instanceof Propose){
					moves.add(((Propose) utt).getProposal());
				}
				else if (utt instanceof AcceptPropose || utt instanceof RejectPropose){
					moves.add(((RejectPropose) utt).getProposal());
				}
				//Suppression des propositions acceptées durant la negociation
				
				else if(utt instanceof Accept){
					int index = moves.lastIndexOf(((Accept) utt).getProposal());
					moves.remove(index);
				}
				else if(utt instanceof AcceptPropose){
					int index = moves.lastIndexOf(((AcceptPropose) utt).getAccepted());
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
