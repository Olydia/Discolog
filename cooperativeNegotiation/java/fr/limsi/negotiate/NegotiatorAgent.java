package fr.limsi.negotiate;

import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.lang.Utterance;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.plugin.DecompositionPlugin;
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
		dual.interaction1.load("models/models_Sub/Negotiate.xml");
		dual.interaction2.load("models/models_Sub/Negotiate.xml");
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
			// compute an utterance intro dom vs submissive
			return null;

		} else if ( negotiation.negotiationFailure() )
			return new Say(disco, false, "Sorry, but I no longer want to do for dinner!");

		else {
			if ( utterance instanceof StatePreference ) {
				 if ( proposalFromUserState(getLastUserPref()) )
					return new Propose(disco, false, proposalFromPreference(getLastUserPref(), true));

				// fill in similarly for rest of cases

				else return null;            

			} else if ( utterance instanceof AskPreference ) {
				// fill in similarly
				return null;

			} else if ( utterance instanceof Propose ) {
				// fill in similarly
				return null;

			} else if ( utterance instanceof Accept ) {
				// fill in similarly
				return null;

			} else if ( utterance instanceof Reject ) {
				// fill in similarly
				return null;

			} else if ( utterance instanceof StatePreference ) {
				// fill in similarly
				return null;

			} else return null;
		}
	}

	// JavasScript helpers from Negotiation.d4g.xml translated to Java

	//   private boolean proposalFromUserState (ValuePreference<Criterion> userPref) {
	//      Criterion more = userPref.getMore();
	//      return more != null && negotiation.isAcceptable(createProposal(more, true), relation);
	//   }
	//
	//   private Proposal proposalFromPreference (ValuePreference<Criterion> preference, boolean isSelf) {
	//      return preference == null ? null : createProposal(preference.getMore(), isSelf);
	//   }
	//
	//   private Proposal createProposal (Object value, boolean isSelf) {
	//      if ( value == null ) return null;
	//      return negotiation.createProposal(value, isSelf);
	//   }
	//
	//   private ValuePreference<Criterion> getLastUserPref () { 
	//      PreferenceStatement last = getLastUserStatement("State");
	//      return last != null ? last.getStatedPreference() : getPreference(Cuisine.class);
	//   }
	//
	//   private PreferenceStatement getLastUserStatement (String type) {
	//      return negotiation.getContext().getLastStatement(type, true);
	//   }
	//
	//   private ValuePreference<Criterion> getPreference (Class<? extends Criterion> criterion) {
	//      return negotiation.getRandomPreference(criterion);
	//   }

}
