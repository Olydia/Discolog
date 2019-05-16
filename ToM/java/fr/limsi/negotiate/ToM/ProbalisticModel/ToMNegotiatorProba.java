package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;
import java.util.concurrent.TimeUnit;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.lang.*;

/**
 * extension de l'agent négociateur afin qu'il soit capable de prédir la dominance de son interlocuteur
 * et ensuite adapter sa stratégie de négociation
 * @author ouldouali
 * @param otherModel: hypothèses sur l'état mental de l'interlocuteur
 * @param other: la valeur de dominance prédite à l'état courant
 * @param guessed : historique des valeurs de dominance prédite aux tours précédents
 * @param state: Stratégie d'adaptation de l'agent (complémentaire, mimic, ou ne s'adapte pas
 *
 */


public class ToMNegotiatorProba extends NegotiatorAgent{
	private HModels otherModel;
	public Negotiation<? extends Option> previousState;
	public double other;
	private List<Double> guessed;
	private ADAPT state;



	public List<Double> getGuessed() {
		return guessed;
	}


	public void setGuessed(List<Double> guessed) {
		this.guessed = guessed;
	}


	public double getOther() {
		return other;
	}


	public void setOther(double other) {
		this.other = other;
	}


	/**
	 * 
	 * @param Ajout modif
	 * @param 
	 */

	public ToMNegotiatorProba(String name, Negotiation<? extends Option> negotiation, ADAPT state) {
		super(name, negotiation);

		this.state = state;
		List<Class<? extends Criterion>> criteria  = negotiation.getCriteria().getElements();

		this.otherModel =  new HModels(criteria);

		this.previousState = negotiation;

		this.other = 1 - getNegotiation().getDominance();

		this.guessed = new ArrayList<Double>();



	}


	public Negotiation<? extends Option> getPreviousState() {
		return previousState;
	}

	public void setPreviousState(Negotiation<? extends Option> previousState) {
		this.previousState = previousState;
		//this.previousState.propose(new OptionProposal(ToyRestaurant.ARRIBA_MEXICO));
	}
	//********** Initiate the model of toher
	// Input : List of Criteria Class<? extends Criterion>




	@Override
	public void execute (Task occurrence, Interaction interaction, Plan contributes) {	  
		super.execute(occurrence, interaction, contributes);


	}


	@Override
	public Utterance respond (Utterance utterance, Disco disco) {

//		System.out.println("-------------------------------------------------------------------------------------");
	//	if ( utterance != null )  System.out.println(utterance.format() + "\n");
//		//System.out.println(otherModel.getHypotheses());

//
	

		if (utterance != null){
			double other = guess(utterance, getOther());
			this.setOther(other);
			guessed.add(other);

			switch(this.state){
			case MIMIC:
				mimic(other);
				break;
			case COMPLEMENT:
				complement(other);
				break;
			case NONADAPT:
				break;
			default:
				break;

			}

		//	System.out.println( this.getName() +" predicted the pow of the other to : " + other);

		}


		Utterance u = respondTo(utterance, disco);
		
		
		return u ;
	}

	public void adapt(double value){
		super.relation = value;
		getNegotiation().setAdaptativePow(value);
	}

	public void complement (double guess){
		this.adapt(1 - guess);
	}

	public void mimic(double guess){
		this.adapt(guess);

	}


	// ***** TOM computing
	/**
	 * Compute hypotheses about the other's lead of dialogue
	 * Large propose: Dominant
	 * Large ask: submissive
	 * @return 
	 */
	public  List<PowHypothesis> leadDialogue(){
		float prop = getProposePropotion();
		float ask = getAskPropotion();
		if(prop>0.5)
			return otherModel.getDom();

		else if(ask>0.5)
			return otherModel.getSub();
		else
			return otherModel.getHypotheses();
	}

	public double guess(Utterance u, double previousPow){
		if(otherModel.isEmpty())
			return 0.5;
		//System.out.println(u.format());

		List<PowHypothesis> models = leadDialogue();
		// si c'est superieurs r�cuperer uniquement les pow> 0.5 et les donner en entr�e


		if(u instanceof StatePreference){

			Statement<Criterion> c  = new Statement<Criterion>(((StatePreference) u).getValue(), 
					((StatePreference) u).getLikable());
			return this.otherModel.reviseHypothese(models, c, previousPow);

		}else if(u instanceof Reject){
			//return this.otherModel.updateReject(models, ((Reject) u).getProposal(), previousPow);
			return this.updateProposal(models,((Reject) u).getProposal(), false, previousPow);


		}else if (u instanceof Accept || u instanceof Propose){
			return updateProposal(models,((ProposalUtterance) u).getProposal(), true, previousPow);


		}else if (u instanceof AcceptPropose){
			// Il manque le cas du propose 
			updateProposal(models, ((AcceptPropose) u).getAccepted(),true, previousPow);
			return updateProposal(models, ((AcceptPropose) u).getProposal() ,true, previousPow);


		}else if(u instanceof RejectState){

			Proposal reject = ((RejectState) u).getProposal();
			Criterion justify = ((RejectState) u).getJustify();
			if(reject.getValue().equals(justify))
				return this.updateProposal(models,reject, false, previousPow);
			//this.otherModel.updateReject(models, reject, previousPow);

			else{
				//this.otherModel.updateReject(models, reject, previousPow);
				this.updateProposal(models,reject, false, previousPow);
				return this.otherModel.reviseHypothese(new Statement<Criterion>
				(justify, Satisfiable.FALSE), previousPow);
			}

		}else if(u instanceof RejectPropose){
			//this.otherModel.updateReject(models, ((RejectPropose) u).getReject(), previousPow);
			this.updateProposal(models,((RejectPropose) u).getProposal(), false, previousPow);
			return this.updateProposal(models,((RejectPropose) u).getProposal(), true, previousPow);

		}else if(u instanceof AskPreference){
			Map<Double, Float> values =this.otherModel.getHypothesesSize(models); 
			//System.out.println(values);// get the number of elements divided by total
			return this.otherModel.reviseOtherPow(values, previousPow);

		}



		return previousPow;
	}

	// Accept Update

	/**
	 * 
	 * @param models
	 * @param c: criterion that is proposed
	 * @param accept true if it is Accept or Propose, False if Reject
	 * @return the score of the acceptability affected to each hypothesis
	 */
	public Map <Double,Float> scoreProposal(List<PowHypothesis> models,
			CriterionProposal c, boolean accept){

		Map <Double,Float> acc = new HashMap<Double,Float>();

		Class<? extends Criterion> cType =c.getValue().getClass();

		List<CriterionProposal> accepted = this.getNegotiation().getValueNegotiation(cType).
				getProposalsWithStatus(Status.ACCEPTED);

		List<CriterionProposal> rejected = this.getNegotiation().getValueNegotiation(cType).
				getProposalsWithStatus(Status.REJECTED);

		// update case Propose or Accept
		if(accept){
			for(PowHypothesis model: models){

				double self = this.getNegotiation().computeSelf(model.getPow());

				acc.put(model.getPow(),
						model.scoreAcc(c.getValue(),accepted, self, 
								getNegotiation().getContext().isFirstMove(true)));
			}
		}
		// case Reject
		else{
			for(PowHypothesis model: models){

				double self = this.getNegotiation().computeSelf(model.getPow());

				acc.put(model.getPow(),
						model.scoreReject(c.getValue(),rejected, accepted, self, 
								getNegotiation().getContext().isFirstMove(true)));
			}
		}
		//System.out.println("Values of acceptability " + acc);
		return acc;
	}


	public double updateProposal( List<PowHypothesis> models, Proposal accepted, 
			boolean accept, double previousPow){

		if(accepted instanceof CriterionProposal){

			Map <Double,Float> acc = scoreProposal(models, (CriterionProposal) accepted, accept);
			return this.otherModel.reviseOtherPow(acc, previousPow);
		}

		return previousPow;
	}


	public float getProposePropotion(){
		List<NegotiationUtterance> otherUtt = this.getNegotiation().getContext().getHistory(true);
		int proposals = 0;
		for(NegotiationUtterance u: otherUtt){
			if(u instanceof ProposalUtterance){
				if(!(u instanceof Accept))
					proposals ++;

			}
		}
		return ((float) proposals/otherUtt.size());
	}

	public float getAskPropotion(){
		List<NegotiationUtterance> otherUtt = this.getNegotiation().getContext().getHistory(true);
		int ask = 0;
		for(NegotiationUtterance u: otherUtt){
			if(u instanceof AskPreference)
				ask ++;
		}
		return ((float)ask/otherUtt.size());
	}
	

	public enum ADAPT {
		COMPLEMENT,
		MIMIC,
		NONADAPT;
	}

	public void pause(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}