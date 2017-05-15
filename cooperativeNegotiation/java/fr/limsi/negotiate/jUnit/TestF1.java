package fr.limsi.negotiate.jUnit;

import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.NegotiatorAgent;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class TestF1 {
	
	 private NegotiatorAgent agent;
	 
	 
	public NegotiatorAgent getAgent() {
		return agent;
	}

	public void setAgent(NegotiatorAgent agent) {
		this.agent = agent;
	}

	public TestF1( Negotiation<? extends Option> negotiation, double pow) {
		this.agent = new NegotiatorAgent("agent", negotiation);
		this.agent.setRelation(pow);
	}

	public static void main (String[] args) {
		totalOrderedModels model = new totalOrderedModels();
		TestF1 test = new TestF1(model.model1(), 0.8);
		
		 Interaction interaction = new Interaction(
		            test.getAgent(), 
		            new User("user"),
		            args.length > 0 && args[0].length() > 0 ? args[0] : null);
		      // do not guess recipes, since using DecompositionPlugin below
		      interaction.setGuess(false); 
		      // TODO: enable random choice among applicable utterances (disabled
		      //       for now to make debugging easier
		      // Agent.RANDOM = new Random(12345);
		interaction.start(true); // give user first turn
		
	}

}
