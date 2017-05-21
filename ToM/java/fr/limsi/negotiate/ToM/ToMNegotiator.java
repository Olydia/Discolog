package fr.limsi.negotiate.ToM;


import java.util.ArrayList;
import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.NegotiatorAgent;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.lang.AcceptPropose;
import fr.limsi.negotiate.lang.AskPreference;
import fr.limsi.negotiate.lang.PreferenceUtterance;
import fr.limsi.negotiate.lang.ProposalUtterance;
import fr.limsi.negotiate.lang.RejectPropose;
import fr.limsi.negotiate.lang.RejectState;

public class ToMNegotiator extends NegotiatorAgent{
	public ArrayList<Double> pow_hyp;

	public ToMNegotiator(String name, Negotiation<? extends Option> negotiation) {
		super(name, negotiation);
		setPow_hyp();

	}

	@Override
	public Utterance respond (Utterance utterance, Disco disco) {
		Utterance u = respondTo(utterance, disco);
		System.out.println(u.format());
		Negotiation<? extends Option> nego;
		try {
			nego = getNegotiation().clone();
			System.out.println(this.getName() + " " + guess(nego, disco, utterance, u));

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return u ;
	}

	//----------------------------------------- for ToM ----------------------------------------
	public Utterance guessUtt(Negotiation<? extends Option> nego, double pow, Utterance utt, Disco disco){
		NegotiatorAgent agent = new NegotiatorAgent("current", nego);
		agent.setRelation(pow);
		return agent.respondTo(utt, disco);
	}

	public ArrayList<Double> guess (Negotiation<? extends Option> current, Disco disco, 
			Utterance previousUtt, Utterance guessUtt ) {
		ArrayList<Double> power = new ArrayList<Double>();

		for (double pow: this.pow_hyp){
			current.setDominance(pow);
			if(identicalUtterances(guessUtt, guessUtt(current, pow, previousUtt, disco)))
				power.add(pow);
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
