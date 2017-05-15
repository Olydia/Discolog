package fr.limsi.negotiate.ToM;

import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.NegotiatorAgent;
import fr.limsi.negotiate.Option;

public class ToMNegotiator extends NegotiatorAgent{
	private  Guess otherToM;
	
	public ToMNegotiator(String name, Negotiation<? extends Option> negotiation, Guess otherToM) {
		super(name, negotiation);
		this.otherToM = otherToM;
	}
	
	@Override
 	public Utterance respondTo(Utterance utterance, Disco disco){
		// getSelflastutterance
		otherToM.hypo_pow = otherToM.guess(selfUt, utterance, disco);
		return super.respondTo(utterance, disco);	
		}

}
