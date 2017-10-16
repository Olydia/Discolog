package fr.limsi.negotiate.ToM.ProbalisticModel;

import java.util.*;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.lang.*;
import fr.limsi.negotiate.restaurant.*;
import fr.limsi.negotiate.restaurant.totalOrderedModels;


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
		this.setOther(guess(utterance, getOther()));
		System.out.println(getOther());
		Utterance u = respondTo(utterance, disco);
		return u ;
	}


	// ***** TOM computing
	
	public double guess(Utterance u, double previousPow){
		
		System.out.println(u.format());
		
		if(u instanceof StatePreference){
			
			Statement<Criterion> c  = new Statement<Criterion>(((StatePreference) u).getValue(), 
															((StatePreference) u).getLikable());
			return this.otherModel.reviseHypothese(c, previousPow);

		}else if(u instanceof Reject){
			return this.otherModel.updateReject(((Reject) u).getProposal(), previousPow);
		
		
		
		}else if (u instanceof Accept || u instanceof Propose){
			return updateAccept(((ProposalUtterance) u).getProposal(), previousPow);
			
			
		}else if (u instanceof AcceptPropose){
			// Il manque le cas du propose 
			return updateAccept(((AcceptPropose) u).getAccepted(), previousPow);
			
			
		}else if(u instanceof RejectState){
			
			Proposal reject = ((RejectState) u).getProposal();
			Criterion justify = ((RejectState) u).getJustify();
			if(reject.getValue().equals(justify))
				return this.otherModel.updateReject(reject, previousPow);
			
			else{
				this.otherModel.updateReject(reject, previousPow);
				return this.otherModel.reviseHypothese(new Statement<Criterion>
										(justify, Satisfiable.FALSE), previousPow);
			}
			
		}else if(u instanceof RejectPropose){
			this.otherModel.updateReject(((RejectPropose) u).getReject(), previousPow);
			return this.updateAccept(((RejectPropose) u).getProposal(), previousPow);
		}
	
//		else if(u instanceof AskPreference)
//			return 0.4;
//		
		
		
		return previousPow;
	}
	
	// Accept Update
	
	public Map <Double,Float> getAcceptability(CriterionProposal c){
		
		Map <Double,Float> acc = new HashMap<Double,Float>();
		
		Class<? extends Criterion> cType =c.getValue().getClass();
		
		List<CriterionProposal> accepted = this.getNegotiation().getValueNegotiation(cType).
											getProposalsWithStatus(Status.ACCEPTED);
		
		if(accepted.contains(c))
			accepted.remove(c);
		
		for(PowHypothesis model: otherModel.getHypotheses()){
			
			double self = this.getNegotiation().computeSelf(model.getPow());
			
			acc.put(model.getPow(), model.scoreAcc(c.getValue(),accepted, self));
		}
		System.out.println(acc);
		return acc;
	}
	
	
	public double updateAccept(Proposal accepted, double previousPow){
		
		if(accepted instanceof CriterionProposal){
			
			Map <Double,Float> acc = getAcceptability((CriterionProposal) accepted);
			return this.otherModel.reviseOtherPow(acc, previousPow);
		}
		
		return previousPow;
	}
	
	
	
	public static void main (String[] args) {
		
		totalOrderedModels model = new totalOrderedModels();

		Negotiation<Restaurant> a = model.model1();
		a.setDominance(0.7);
		a.addProposal(new CriterionProposal(true, Cuisine.CHINESE));
		a.addProposal(new CriterionProposal(true, Cuisine.JAPANESE));
		a.addProposal(new CriterionProposal(true, Cuisine.ITALIAN));
		
//		CriterionProposal ac = new CriterionProposal(false, Cuisine.CHINESE);
//		ac.setStatus(Status.REJECTED);
		
//		CriterionNegotiation<Criterion>cn =a.getValueNegotiation(ac.getValue().getClass());
//		cn.updateProposal(ac);
//		a.addStatement(new Statement<Criterion>(ac.getValue(),Satisfiable.FALSE), false);
//		
//		CriterionProposal ac2 = new CriterionProposal(false, Cuisine.JAPANESE);
//		ac.setStatus(Status.ACCEPTED);
//		cn.updateProposal(ac2);
//		a.addStatement(new Statement<Criterion>(ac2.getValue(),Satisfiable.TRUE), false);
//		
//		
//		
//		OptionProposal p = new OptionProposal(true, Restaurant.A_LA_TURKA);
//		p.setStatus(Status.REJECTED);
//		a.updateProposal(p);
//		
//		OptionProposal p2 = new OptionProposal(true, Restaurant.ABA_TURKISH);
//		p2.setStatus(Status.REJECTED);
//		a.updateProposal(p2);
		// add the accept
		CriterionProposal ac1 = new CriterionProposal(false, Cuisine.ITALIAN);
		ac1.setStatus(Status.ACCEPTED);

		ToMNegotiatorProba tom = new ToMNegotiatorProba("test", a);
		
		System.out.println(tom.updateAccept(ac1, 0.6));
		
	}

		
}
