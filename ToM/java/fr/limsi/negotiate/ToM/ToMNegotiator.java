package fr.limsi.negotiate.ToM;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.lang.*;

public class ToMNegotiator extends NegotiatorAgent{
	
	public HashMap<Double, List<List<Self_Ci<Criterion>>>> otherModel;

	
	public ToMNegotiator(String name, Negotiation<? extends Option> negotiation) {
		super(name, negotiation);
		this.otherModel = new HashMap<Double, List<List<Self_Ci<Criterion>>>> ();
		
		List<List<Self_Ci<Criterion>>>  prefs = setPreferences(negotiation.getCriteria().getElements());
		for(double pow:setPow_hyp()){
			ArrayList<List<Self_Ci<Criterion>>> copy = new ArrayList<List<Self_Ci<Criterion>>>();
			copy.addAll(prefs);
			otherModel.put(pow, copy);
		}
			

	}
	
	//********** Initiate the model of toher
	// Input : List of Criteria Class<? extends Criterion>
	
	
	public List<List<Self_Ci<Criterion>>> setPreferences( List<Class<? extends Criterion>> elem){
		Models<? extends Option> m = new Models<>();

		List<List<Self_Ci<Criterion>>> models = new ArrayList<List<Self_Ci<Criterion>>>();
		for( Class<? extends Criterion> e: elem) {
			
			models.add(m.createModelCriterion(Arrays.asList(e.getEnumConstants())));
		}
 
		return m.getCombination(0, models);
	}
	
	
	public ArrayList<Double> setPow_hyp(){

		ArrayList<Double> pow_hyp =	new ArrayList<Double> ();
		for(int i=3; i<10; i++){
			pow_hyp.add(i/10.0);
		}
		return pow_hyp;
	}

	@Override
	public Utterance respond (Utterance utterance, Disco disco) {
		Utterance u = respondTo(utterance, disco);
		try {
//			long startTime = System.currentTimeMillis();
			Negotiation<? extends Option> nego = getNegotiation().clone();
//			for(int i =0; i<10000000; i++)
//				guessTest(nego, disco, utterance, u, nego.getDominance());
//
//			long stopTime = System.currentTimeMillis();
//			long elapsedTime = stopTime - startTime;
//			System.out.println("ELEPSED TIME: " + elapsedTime);

			System.out.println(this.getName() + " Hypotheses on pow: " + guess(nego, disco, utterance, u));

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return u ;
	}

	//----------------------------------------- for ToM ----------------------------------------
	public Negotiation<? extends Option> createModel()
	
	/**
	 * 
	 * @param disco
	 * @param previousUtt
	 * @param guessUtt
	 * @return
	 */
	

	public ArrayList<Double> guess (Disco disco, Utterance previousUtt, Utterance guessUtt ) {
		ArrayList<Double> power = new ArrayList<Double>();
		//for(Map.Entry<Double, List<List<Self_Ci<Criterion>>>> entry : otherModel.entrySet()){
			for (double pow: this.pow_hyp){
			current.setDominance(pow);
			Utterance guessed = guessUtt(current, pow, previousUtt, disco);
			//System.out.println(pow + " : " + guessed.toString());
			if(identicalUtterances(guessUtt, guessed))
				power.add(pow);
		}
		pow_hyp= power;
		return power;
	}
	
	public Utterance guessUtt(Negotiation<? extends Option> nego, double pow, Utterance utt, Disco disco){
		NegotiatorAgent agent = new NegotiatorAgent("current", nego);
		agent.setRelation(pow);
		return agent.respondTo(utt, disco);
	}


	public boolean guessTest (Negotiation<? extends Option> current, Disco disco, 
			Utterance previousUtt, Utterance guessUtt, double pow){
		Utterance guessed = guessUtt(current, pow, previousUtt, disco);
		//System.out.println(pow + " : " + guessed.toString());
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
