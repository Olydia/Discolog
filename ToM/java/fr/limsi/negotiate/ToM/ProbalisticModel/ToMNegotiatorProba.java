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
		
		System.out.println("-------------------------------------------------------------------------------------");
		if ( utterance != null )  System.out.println(utterance.format() + "\n");
		System.out.println(otherModel.getHypotheses());


//		try {
//			Thread.currentThread().sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int time = new Random().nextInt(500) + 550;
//		//pause(time);
//		try {
//			Thread.sleep(time);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
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

			System.out.println( this.getName() +" predicted the pow of the other to : " + other);

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

		//System.out.println(u.format());
		
		List<PowHypothesis> models = leadDialogue();
		// si c'est superieurs r�cuperer uniquement les pow> 0.5 et les donner en entr�e
		

		if(u instanceof StatePreference){

			Statement<Criterion> c  = new Statement<Criterion>(((StatePreference) u).getValue(), 
					((StatePreference) u).getLikable());
			return this.otherModel.reviseHypothese(models, c, previousPow);

		}else if(u instanceof Reject){
			return this.otherModel.updateReject(models, ((Reject) u).getProposal(), previousPow);



		}else if (u instanceof Accept || u instanceof Propose){
			return updateAccept(models,((ProposalUtterance) u).getProposal(), previousPow);


		}else if (u instanceof AcceptPropose){
			// Il manque le cas du propose 
			return updateAccept(models, ((AcceptPropose) u).getAccepted(), previousPow);


		}else if(u instanceof RejectState){

			Proposal reject = ((RejectState) u).getProposal();
			Criterion justify = ((RejectState) u).getJustify();
			if(reject.getValue().equals(justify))
				return this.otherModel.updateReject(models, reject, previousPow);

			else{
				this.otherModel.updateReject(models, reject, previousPow);
				return this.otherModel.reviseHypothese(new Statement<Criterion>
				(justify, Satisfiable.FALSE), previousPow);
			}

		}else if(u instanceof RejectPropose){
			this.otherModel.updateReject(models, ((RejectPropose) u).getReject(), previousPow);
			return this.updateAccept(models,((RejectPropose) u).getProposal(), previousPow);
		
		}else if(u instanceof AskPreference){
			Map<Double, Float> values =this.otherModel.getHypothesesSize(models); 
			//System.out.println(values);// get the number of elements divided by total
			 return this.otherModel.reviseOtherPow(values, previousPow);
			
		}
				


		return previousPow;
	}

	// Accept Update

	public Map <Double,Float> getAcceptability(CriterionProposal c){

		return getAcceptability(otherModel.getHypotheses(), c);
	}


	public Map <Double,Float> getAcceptability(List<PowHypothesis> models,
			CriterionProposal c){

		Map <Double,Float> acc = new HashMap<Double,Float>();

		Class<? extends Criterion> cType =c.getValue().getClass();

		List<CriterionProposal> accepted = this.getNegotiation().getValueNegotiation(cType).
				getProposalsWithStatus(Status.ACCEPTED);

		//		if(accepted.contains(c))
		//			accepted.remove(c);

		for(PowHypothesis model: models){

			double self = this.getNegotiation().computeSelf(model.getPow());
			//System.out.println(model.getPow() + " self "+ self);
			acc.put(model.getPow(), model.scoreAcc(c.getValue(),accepted, self, 
					getNegotiation().getContext().isFirstMove(true)));
		}

		//System.out.println("Values of acceptability " + acc);
		return acc;
	}
	
	
	public double updateAccept( List<PowHypothesis> models, Proposal accepted, double previousPow){

		if(accepted instanceof CriterionProposal){

			Map <Double,Float> acc = getAcceptability(models, (CriterionProposal) accepted);
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
//	public static void main (String[] args) {
//
//		totalOrderedModels model = new totalOrderedModels();
//
//		Negotiation<Restaurant> a = model.model1();
//		a.setDominance(0.7);
//		a.addProposal(new CriterionProposal(true, Cuisine.CHINESE));
//		a.addProposal(new CriterionProposal(true, Cuisine.JAPANESE));
//		a.addProposal(new CriterionProposal(true, Cuisine.ITALIAN));
//
//		//		CriterionProposal ac = new CriterionProposal(false, Cuisine.CHINESE);
//		//		ac.setStatus(Status.REJECTED);
//
//		//		CriterionNegotiation<Criterion>cn =a.getValueNegotiation(ac.getValue().getClass());
//		//		a.addStatement(new Statement<Criterion>(ac.getValue(),Satisfiable.FALSE), false);
//		//		
//		//		CriterionProposal ac2 = new CriterionProposal(false, Cuisine.JAPANESE);
//		//		ac.setStatus(Status.ACCEPTED);
//		//		cn.updateProposal(ac2);
//		//		a.addStatement(new Statement<Criterion>(ac2.getValue(),Satisfiable.TRUE), false);
//		//		
//		//		
//		//		
//		//		OptionProposal p = new OptionProposal(true, Restaurant.A_LA_TURKA);
//		//		p.setStatus(Status.REJECTED);
//		//		a.updateProposal(p);
//		//		
//		//		OptionProposal p2 = new OptionProposal(true, Restaurant.ABA_TURKISH);
//		//		p2.setStatus(Status.REJECTED);
//		//		a.updateProposal(p2);
//		// add the accept
//		CriterionProposal ac1 = new CriterionProposal(false, Cuisine.ITALIAN);
//		ac1.setStatus(Status.ACCEPTED);
//
//		//ToMNegotiatorProba tom = new ToMNegotiatorProba("test", a);
//
//		//System.out.println(tom.updateAccept(ac1, 0.6));
//
//	}

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
