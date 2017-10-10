package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;

public class ToMNegotiatorProba extends NegotiatorAgent{

	private HModels otherModel;
	public Negotiation<? extends Option> previousState;
	//public Negotiation<? extends Option> other;
	public double other;


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

	public ToMNegotiatorProba(String name, Negotiation<? extends Option> negotiation) {
		super(name, negotiation);
		
		List<Class<? extends Criterion>> criteria  = negotiation.getCriteria().getElements();
		
		this.otherModel =  new HModels(criteria);
		
		this.previousState = negotiation;

		this.other = 1 - getNegotiation().getDominance();

		
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
		//cloneNegotiation();


	}


	

	@Override
	public Utterance respond (Utterance utterance, Disco disco) {
		//Utterance selfPrevious = getNegotiation().getContext().getLastMove(false);
		if (utterance != null)
			//guessProba(disco,selfPrevious, utterance, previousState);
		this.setOther(otherModel.guess(utterance, getOther()));
		System.out.println(getOther());
		Utterance u = respondTo(utterance, disco);
		return u ;
	}


	
}
