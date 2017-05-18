package fr.limsi.negotiate.ToM;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.NegotiatorAgent;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.lang.PreferenceUtterance;
import fr.limsi.negotiate.lang.ProposalUtterance;

public class ToMNegotiator extends NegotiatorAgent{
	public ArrayList<Double> pow_hyp;
	
	public ToMNegotiator(String name, Negotiation<? extends Option> negotiation) {
		super(name, negotiation);
		setPow_hyp();

	}
		
	@Override
	public Utterance respond (Utterance utterance, Disco disco) {
		Utterance u = respondTo(utterance, disco);
		System.out.println(this.getName() + " " + guess(getNegotiation(), disco, utterance, u));

		//System.out.println(u.format()+ " -> " + u.getType());
		return u ;
	}
//	@Override
// 	public Utterance respondTo(Utterance utterance, Disco disco){
//		// getSelflastutterance
//		//otherToM.hypo_pow = otherToM.guess(selfUt, utterance, disco);
//		return super.respondTo(utterance, disco);	
//		}
	
	//----------------------------------------- for ToM ----------------------------------------
		public Utterance guessUtt(Negotiation<? extends Option> nego, double pow, Utterance utt, Disco disco){
			NegotiatorAgent agent = new NegotiatorAgent("current", nego);
			agent.setRelation(pow);
			return agent.respondTo(utt, disco);
		}
		
		public ArrayList<Double> guess (Negotiation<? extends Option> nego, Disco disco, 
								Utterance previousUtt, Utterance guessUtt ) {
			ArrayList<Double> power = new ArrayList<Double>();
			Negotiation<? extends Option> current;
			try {
				current = nego.clone();
				
				for (double pow: this.pow_hyp){
					current.setDominance(pow);
					if(identicalUtterances(guessUtt, guessUtt(current, pow, previousUtt, disco)))
						power.add(pow);
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pow_hyp= power;
			return power;
		}

		public void setPow_hyp(){
			
			pow_hyp = 	new ArrayList<Double> ();
			for(int i=1; i<10; i++){
				pow_hyp.add(i/10.0);
			}
		}
		
		boolean identicalUtterances(Utterance ut, Utterance otherUt){
			if(ut.getType().equals(otherUt.getType())){
				if (ut instanceof PreferenceUtterance){
					Criterion c1 =  ((PreferenceUtterance) ut).getValue();
					Criterion c2 =  ((PreferenceUtterance) otherUt).getValue();
					return (c1.equals(c2));

				}
				else if (ut instanceof ProposalUtterance){
					Proposal c1 =  ((ProposalUtterance) ut).getProposal();
					Proposal c2 =  ((ProposalUtterance) otherUt).getProposal();
					return (c1.equals(c2));
				}
			}
			return false;
		}

}
