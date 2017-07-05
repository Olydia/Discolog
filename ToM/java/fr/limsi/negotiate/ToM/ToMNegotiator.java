package fr.limsi.negotiate.ToM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.ToM.preferencesGeneration.ModelGenerator;
import fr.limsi.negotiate.ToM.preferencesGeneration.Models;
import fr.limsi.negotiate.ToM.preferencesGeneration.PrefNegotiation;
import fr.limsi.negotiate.lang.*;

public class ToMNegotiator extends NegotiatorAgent{
	
	public HashMap<Double, List<PrefNegotiation<Option>>> otherModel;
	public Negotiation<? extends Option> previousState;
	//public Negotiation<? extends Option> other;

	
	/**
	 * 
	 * @param Ajout modif
	 * @param 
	 */
	
	public Negotiation<? extends Option> otherMental;


	public ToMNegotiator(String name, Negotiation<? extends Option> negotiation) {
		super(name, negotiation);
		this.otherModel = new HashMap<Double, List<PrefNegotiation<Option>>> ();
		this.previousState = negotiation;
		
		
		@SuppressWarnings("unchecked")
		List<PrefNegotiation<Option>>  prefs = setPreferences(negotiation.getCriteria().getElements(), (Class<Option>) negotiation.getTopic());
		for(double pow:setPow_hyp()){
			List<PrefNegotiation<Option>> copy = new ArrayList<PrefNegotiation<Option>>();
			copy.addAll(prefs);
			otherModel.put(pow, copy);
		}	
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
	
	
	public List<PrefNegotiation<Option>> setPreferences( List<Class<? extends Criterion>> elem, 
			Class<Option> class1){
		Models<Option> m = new Models<Option>();
		return m.computeModels(elem, class1);

		
	}
	
	
	public ArrayList<Double> setPow_hyp(){

		ArrayList<Double> pow_hyp =	new ArrayList<Double> ();
		for(int i=3; i<10; i++){
			pow_hyp.add(i/10.0);
		}
		return pow_hyp;
	}

	   @Override
	   public void execute (Task occurrence, Interaction interaction, Plan contributes) {	  
	      super.execute(occurrence, interaction, contributes);
     	 cloneNegotiation();


	   }
	   
	   
	public void cloneNegotiation() {
	
		setPreviousState(getNegotiation().cloneNegotiation());
//		try {
//			setPreviousState(getNegotiation().clone());
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		setPreviousState(new Negotiation(getNegotiation().getValuesNegotiation(),
//															getNegotiation().getCriteria(),
//															getNegotiation().getTopic(),
//															getNegotiation().getContext(),
//															getNegotiation().getProposals()
//														  ));
	}


	@Override
	public Utterance respond (Utterance utterance, Disco disco) {
	//	try {
//			long startTime = System.currentTimeMillis();
	//Negotiation<? extends Option> nego = getNegotiation().clone();
//			for(int i =0; i<10000000; i++)
//				guessTest(nego, disco, utterance, u, nego.getDominance());
//
//			long stopTime = System.currentTimeMillis();
//			long elapsedTime = stopTime - startTime;
//			System.out.println("ELEPSED TIME: " + elapsedTime);
//			Utterance selfPrevious = getNegotiation().getContext().getLastMove(false);
//			guess(disco,selfPrevious, utterance, nego);

		//} catch (CloneNotSupportedException e) {
		//	e.printStackTrace();
		//}
		Utterance selfPrevious = getNegotiation().getContext().getLastMove(false);

		if (utterance != null)
			guessProba(disco,selfPrevious, utterance, previousState);
		
		Utterance u = respondTo(utterance, disco);
		return u ;
	}


	//----------------------------------------- for ToM ----------------------------------------
	public Negotiation<? extends Option> createModel(double pow, PrefNegotiation<Option> preferences,
			Negotiation<? extends Option> selfNego){
		ModelGenerator generator = new ModelGenerator();
		Negotiation<? extends Option> nego = generator.mirrorNegotiation(preferences, selfNego);
		nego.setDominance(pow);
		return nego;
	}
	
	/**
	 * 
	 * @param disco
	 * @param previousUtt
	 * @param guessUtt
	 * @return
	 */
	
	
	public void guess (Disco disco, Utterance previousUtt, Utterance guessUtt, Negotiation<? extends Option> selfNego) {
						
			for(Iterator<Entry<Double, List<PrefNegotiation<Option>>>> it = otherModel.entrySet().iterator(); it.hasNext(); ) {
				Map.Entry<Double, List<PrefNegotiation<Option>>> entry = it.next();
				/*
				 * il faut gerer la mise a jouer des elements
				 */
				for (Iterator<PrefNegotiation<Option>> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
					PrefNegotiation<Option> element = iterator.next();
					
					Utterance guessed = guessUtt(createModel(entry.getKey(), element, selfNego), entry.getKey(), previousUtt, disco);
					//System.out.println(" guessed "+ entry.getKey() + "utt " + guessed.format()+ " -> " + guessed.getType());

					if(!identicalUtterances(guessUtt, guessed))
						iterator.remove();
				}
				System.out.println( entry.getKey() +" " +entry.getValue().size());  
				
				if(entry.getValue().isEmpty()) {
			        it.remove();
			      }
			    }
			// ---------------------------------

	}
	
	
	public void guessProba (Disco disco, Utterance previousUtt, Utterance guessUtt, Negotiation<? extends Option> selfNego) {
		 HashMap<Double, Float> proba = new HashMap<Double, Float>();
		 
		for(Iterator<Entry<Double, List<PrefNegotiation<Option>>>> it = otherModel.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Double, List<PrefNegotiation<Option>>> entry = it.next();
			
			int total = entry.getValue().size();
			int correct = 0;
			for (Iterator<PrefNegotiation<Option>> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
				PrefNegotiation<Option> element = iterator.next();
				
				Utterance guessed = guessUtt(createModel(entry.getKey(), element, selfNego), entry.getKey(), previousUtt, disco);
				//System.out.println(" guessed "+ entry.getKey() + "utt " + guessed.format()+ " -> " + guessed.getType());

				if(identicalUtterances(guessUtt, guessed))
					correct ++;
			}
			float p = correct/total;
			proba.put(entry.getKey(), p);
			System.out.println( entry.getKey() +" " + correct + " " + total);
			System.out.printf("%.4f",  p);  
			System.out.println();
//			if(entry.getValue().isEmpty()) {
//		        it.remove();
//		      }
		    }
		// ---------------------------------

}
	public void updateOther(Utterance next){
		// Ajouter interpretation
		
		// 
	}
	
	public Utterance guessUtt(Negotiation<? extends Option> nego, double pow, Utterance utt, Disco disco){
		NegotiatorAgent agent = new NegotiatorAgent("current", nego);
		agent.setRelation(pow);
		return agent.respondTo(utt, disco);
	}


	public boolean guessTest (Negotiation<? extends Option> current, Disco disco, 
			Utterance previousUtt, Utterance guessUtt, double pow){
		Utterance guessed = guessUtt(current, pow, previousUtt, disco);
		return (identicalUtterances(guessUtt, guessed));
	}

	


	boolean identicalUtterances(Utterance ut, Utterance otherUt){

		if(ut.getType().equals(otherUt.getType())){
			if (ut instanceof PreferenceUtterance){
				Criterion c1 =  ((PreferenceUtterance) ut).getValue();
				Criterion c2 =  ((PreferenceUtterance) otherUt).getValue();
				if((c1 == null && c2!=null) || (c2 == null && c1!=null))
					return false;
				if(c1 == null && c2==null){
					if(ut instanceof AskPreference){
						Class<? extends Criterion> class1 = ((AskPreference) ut).getCriterion();
						Class<? extends Criterion> class2 = ((AskPreference) otherUt).getCriterion();
						return (class1.equals(class2));
					}
					return false;	

				}
				else 
					return (c1.equals(c2));


			}
			else if (ut instanceof ProposalUtterance){
				Proposal c1 =  ((ProposalUtterance) ut).getProposal();
				Proposal c2 =  ((ProposalUtterance) otherUt).getProposal();

				if(ut instanceof AcceptPropose){
					Proposal a1 = ((AcceptPropose) ut).getAccepted();
					Proposal a2 = ((AcceptPropose) otherUt).getAccepted();
					return (c1.equals(c2) && a1.equals(a2));

				}
				else if(ut instanceof RejectPropose){
					Proposal r1 = ((RejectPropose) ut).getReject();
					Proposal r2 = ((RejectPropose) otherUt).getReject();
					return (c1.equals(c2) && r1.equals(r2));	
				}
				else if (ut instanceof RejectState){
					Criterion cr1 = ((RejectState) ut).getJustify();
					Criterion cr2 = ((RejectState) otherUt).getJustify();
					return (c1.equals(c2) && cr1.equals(cr2));	

				}


				return (c1.equals(c2));

			}
			else if (ut instanceof Say)
				return (((Say) ut).getText().equals(((Say) otherUt).getText()));
		}
		return false;
	}

}
