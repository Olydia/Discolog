package fr.limsi.negotiate;

import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.lang.Utterance;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.plugin.DecompositionPlugin;
import fr.limsi.negotiate.NegoUtterance.UtType;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.lang.*;
import fr.limsi.negotiate.restaurant.*;

// TODO:  Further optimizations:
//
// * use same Disco instance for all ToM's and for base agent, so
//   avoid translation!  (Provide alternate constructor for Tom)
//
// * for ToM's don't add occurrences to segment (allows more GC?)

public class NegotiatorAgent extends Agent {

	private Negotiation<? extends Option> negotiation;

	public Negotiation<? extends Option> getNegotiation () { return negotiation; }

	public static final int DOMINANT = 1, PEER = 0, SUBMISSIVE = -1;

	private int relation = DOMINANT;

	public int getRelation () { return relation; }

	// use these next two set methods to temporarily change model for each ToM

	public void setNegotiation (Negotiation<? extends Option> negotiation) {
		this.negotiation = negotiation;
	}

	public void setRelation (int relation) { this.relation = relation; }

	public NegotiatorAgent (String name, Negotiation<? extends Option> negotiation) { 
		super(name); 
		setNegotiation(negotiation);
		// since agent has multiple choices, add DecompositionPlugin in order
		// for agent to "look ahead" to utterance choices (as in user menus) 
		new DecompositionPlugin(agenda, 25, true, true);
	}

	public static void main (String[] args) {
		GenerateModel model = new GenerateModel();

		Dual dual = new Dual(
				new NegotiatorAgent("Agent1", model.model1()), 
				new NegotiatorAgent("Agent2", model.model1()), 
				true);

		// note not loading Negotiotion.xml!
		dual.interaction1.load("models/Negotiate.xml");
		dual.interaction2.load("models/Negotiate.xml");
		((NegotiatorAgent) dual.interaction1.getSystem()).setRelation(DOMINANT);
		((NegotiatorAgent) dual.interaction2.getSystem()).setRelation(SUBMISSIVE);
		((NegotiatorAgent) dual.interaction1.getSystem()).getNegotiation().setDominance(DOMINANT);
		((NegotiatorAgent) dual.interaction2.getSystem()).getNegotiation().setDominance(SUBMISSIVE);

		dual.start();
	}

	@Override
	// for consistency, overriding this to used compiled version of dialogue tree
	// for normal agent operation 
	public Plugin.Item respondIf (Interaction interaction, boolean guess) {
		Disco disco = interaction.getDisco();
		return Agenda.newItem(respond((Utterance) disco.getLastOccurrence(), disco), null);
	}

	@Override
	// overriding this for ToM.predict()
	public final Plugin.Item predict (Interaction interaction) {
		return respondIf(interaction, false);
	}

	/**
	 * Return response to given utterance in current negotiation state,
	 * or null if no response.
	 * 
	 * This function is a manual "compilation" of the dialogue tree in Negotiation.d4g.xml
	 * into plain Java.
	 *
	 * @param utterance Last user utterance or null if agent going first
	 * @param disco Needed for constructing new utterances
	 */
	public Utterance respond (Utterance utterance, Disco disco) {
		if ( utterance == null ) {
			Class<? extends Criterion> opent = getNegotiation().getCriteria().sortValues().get(0);

			if(relation == DOMINANT){
				Criterion value = getNegotiation().getValueNegotiation(opent).getSelf().sortValues().get(0);
				return new Propose(disco, false, new CriterionProposal(true, value));
			} else if(relation == SUBMISSIVE){
				return new AskPreference(disco, false, opent, null);
			}
		} else if (negotiation.negotiationFailure()){
			return new Say(disco, false, "Sorry, but I no longer want to do for dinner!");

		} else if ( utterance instanceof AskPreference ) {
			PreferenceMove ask = (PreferenceMove)getNegotiation().getContext().getLastMove(true);
			Statement<Criterion> state = respondToAsk(ask);
			return new StatePreference(disco, false, state.getValue(), state.getStatus());

		} else if ( utterance instanceof Propose ) {
			// fill in similarly
			return null;

		} else if ( utterance instanceof Accept ) {
			// fill in similarly
			return null;

		} else if ( utterance instanceof Reject ) {
			// fill in similarly
			return null;

		} else if ( utterance instanceof StatePreference) {
			// fill in similarly
			if(relation == DOMINANT){
				
			}
		}
		return null;


	}

	// JavasScript helpers from Negotiation.d4g.xml translated to Java

	public boolean otherAsks(){
		return (getNegotiation().getContext().getLastMove(true).getType().equals(UtType.ASK));
	}


	public Statement<Criterion> respondToAsk(PreferenceMove ask){
		if(ask.getValue() == null){
			Criterion value = getNegotiation().getValueNegotiation(ask.getStatementType()).getSelf().sortValues().get(0);
			return new Statement<Criterion>(value, Satisfiable.TRUE);
		}
		else{
			Satisfiable sat = getNegotiation().getValueNegotiation(ask.getStatementType()).
					getSelf().isSatisfiable(ask.getValue().getValue());
			return new Statement<Criterion>(ask.getValue().getValue(), sat);
		}
	}

}
