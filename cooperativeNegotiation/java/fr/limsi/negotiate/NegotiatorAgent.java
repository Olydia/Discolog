package fr.limsi.negotiate;

import java.util.*;

import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.lang.Utterance;
import edu.wpi.disco.lang.Say;
import edu.wpi.disco.plugin.DecompositionPlugin;
import fr.limsi.negotiate.NegoUtterance.UtType;
import fr.limsi.negotiate.Proposal.Status;
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

	public static double  DOMINANT = 0.9, SUBMISSIVE = 0.1;

	private double relation = DOMINANT;

	public double getRelation () { return relation; }

	// use these next two set methods to temporarily change model for each ToM

	public void setNegotiation (Negotiation<? extends Option> negotiation) {
		this.negotiation = negotiation;
	}

	public void setRelation (double relation) { this.relation = relation; }

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
				false);

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

			if(relation > NegotiationParameters.sigma){

				Criterion value = getNegotiation().getValueNegotiation(opent).getSelf().sortValues().get(0);
				return new Propose(disco, false, new CriterionProposal(true, value));

		}
			//	else
//
//				return new AskPreference(disco, false, opent, null);
			


		}
//		else if (relation >  NegotiationParameters.sigma && negotiation.negotiationFailure(utterance)){
//
//			return new Say(disco, false, "Sorry, but I no longer want to do for dinner!");
//
//		} else if (negotiation.negotiationSuccess(relation)!= null){
//
//			Option o = negotiation.negotiationSuccess(relation);
//			return new Say(disco, false, "Let' book a table at the" + o.print() + " " + o.getClass().getSimpleName());
//
//		} else if ( utterance instanceof AskPreference ) {
//
//			PreferenceMove ask = (PreferenceMove)getNegotiation().getContext().getLastMove(true);
//			Statement<Criterion> state = respondToAsk(ask);
//			return new StatePreference(disco, false, state.getValue(), state.getStatus());
//
//		} else if(isLastStatement(utterance)){
//			
//			List<Proposal> proposals = getNegotiation().remainProposals();
//				if(proposals.isEmpty())
//					return new Say(disco, false, "Sorry, but I no longer want to do for dinner!");
//				
//				else
//					return new Propose(disco, false, createProposal(sortProposals(proposals).get(0), true));
//		
//		// DOMINANT case only propose !		
//		}else if (relation > NegotiationParameters.sigma && !getNegotiation().remainProposals().isEmpty()){
//
//				return new Propose(disco, false, createProposal(getNegotiation().chooseProposal(utterance), true));
//		
//		//SUBMISSIVE cases
//		}else {
//			// REJECT
//			if(utterance instanceof Propose){
//
//				Proposal p = (Proposal)utterance.getSlotValue("proposal");
//				if(acceptablity(p)< NegotiationParameters.beta && getNegotiation().computeT()< NegotiationParameters.tau){
//					p.setStatus(Status.REJECTED);
//					return new Reject(disco, false,p);
//				}
//	
//			} else {
//				// ACCEPT
//				Class<? extends Criterion> c=getNegotiation().getContext().getCurrentDisucussedCriterion();
//				Proposal p = getAcceptableProposal(c);
//				if(p != null){
//					p.setStatus(Status.ACCEPTED);
//
//					return new Accept(disco, false, p);
//
//				} else if(canPropose() != null) {
//					
//					// Propose
//					return new Propose (disco, false, canPropose());
//
//				} else{
//					
//					//Ask
//					PreferenceMove ask = ask();
//					if (ask!= null){
//						return new AskPreference(disco, false, ask.getValueType(), ask.getValue().getValue());
//
//					} else {
//						List<Criterion> sts = getPossibleStatements();
//						if(sts.isEmpty())
//							
//							return finalStatement(disco);
//						
//						else {
//						// do a statement from the remain values !
//							Criterion  value = sts.get(0);
//							Satisfiable status = getNegotiation().getValueNegotiation(value.getClass()).getSelf().isSatisfiable(value);
//							return new StatePreference(disco, false, value, status);
//						}
//					}
//				}
//			}
//		}
		return null;
	}

	public Utterance finalStatement(Disco disco){
		return new Say(disco, true, "I've told you all I like about "+ getNegotiation().getTopic().getSimpleName() + "s !");
	}


	public Proposal canPropose() {
		List<Criterion> oStats = getOtherStatements(Satisfiable.TRUE);
		if(!oStats.isEmpty()){

			for(Criterion c: oStats){

				if (getNegotiation().getValueNegotiation
						(c.getClass()).acceptability(c, getNegotiation().self())>= NegotiationParameters.beta)
					return new CriterionProposal(true, c);
			}

		} else {

			List<Criterion> elem = new ArrayList<Criterion> ();

			for(CriterionNegotiation<Criterion> nego: getNegotiation().getValuesNegotiation()){
				if(nego.getProposalsWithStatus(Status.ACCEPTED).isEmpty())
					return null;
				else 
					elem.add(nego.getProposalsWithStatus(Status.ACCEPTED).get(0).getValue());
			}

			Option o = getNegotiation().computeOption(elem);

			if(o != null)
				return new OptionProposal(true, o);
		}

		return null;
	}

	private Proposal getAcceptableProposal(Class<? extends Criterion> c) {

		//1. Check acceptable values in the current discussed criterion
		List<Proposal> proposals = new ArrayList<Proposal>();
		proposals.addAll(getNegotiation().getValueNegotiation(c).getProposalsWithStatus(Status.OPEN));

		for(Proposal p: sortProposals(proposals)){
			if(acceptablity(p)>= NegotiationParameters.beta)
				return p;

		}
		//2. if 1 is not valid, check acceptable proposals in the previous discussed criteria

		List<Proposal> previousP = new ArrayList<Proposal>();

		for( int i =0; i< getNegotiation().getContext().getRemainDiscussedCrt().size()-1; i++ ){
			Class<? extends Criterion> discussed = getNegotiation().getContext().getRemainDiscussedCrt().get(i);
			previousP.addAll(getNegotiation().getValueNegotiation(discussed).getProposalsWithStatus(Status.OPEN));
		}

		previousP.addAll(getNegotiation().getOptionsProposals(Status.OPEN));
		Proposal p = sortProposals(previousP).get(0);
		if(acceptablity(p)>= NegotiationParameters.beta)
			return p;

		//3. Otherwise there is nothing to accept
		return null;		
	}

	// JavasScript helpers from Negotiation.d4g.xml translated to Java


	public Statement<Criterion> respondToAsk(PreferenceMove ask){
		if(ask.getValue() == null){
			Criterion value = getNegotiation().getValueNegotiation(ask.getValueType()).getSelf().sortValues().get(0);
			return new Statement<Criterion>(value, Satisfiable.TRUE);
		}
		else{
			Satisfiable sat = getNegotiation().getValueNegotiation(ask.getValueType()).
					getSelf().isSatisfiable(ask.getValue().getValue());
			return new Statement<Criterion>(ask.getValue().getValue(), sat);
		}
	}

	public Proposal createProposal(Object o, boolean isSelf){
		if(o == null)
			return null;
		if(o instanceof Criterion)
			return new CriterionProposal(isSelf, (Criterion) o);
		if(o instanceof Option)
			return (new OptionProposal(isSelf,(Option) o));

		return null;

	}
	public Proposal createProposal(Object o, boolean isSelf, Status status){
		if(o == null)
			return null;
		else{
			Proposal p = createProposal(o, isSelf);
			p.setStatus(status);
			return p;
		}

	}
	public float acceptablity(Proposal p){
		if(p.getValue() instanceof CriterionProposal){
			@SuppressWarnings("unchecked")
			CriterionNegotiation<Criterion> model = this.getNegotiation().getValueNegotiation(
					(Class<? extends Criterion>) p.getValue().getClass());
			return model.acceptability((Criterion)p.getValue(), getNegotiation().self());
		}
		else 
			return getNegotiation().acceptability((Option)p.getValue());
	}


	public List<Proposal> sortProposals(List<Proposal> props){
		props.sort(new Comparator<Proposal>(){
			public int compare(Proposal p1, Proposal p2){
				return Float.compare(acceptablity(p2), acceptablity(p1));
			}
		});
		return props;
	}
	/**
	 * Returns the list of other statements which has not been Rejected or Accepted
	 * @param status
	 * @return 
	 */
	public List<Criterion> getOtherStatements(Satisfiable status){
		List<Criterion> statements = new ArrayList<Criterion> ();
		for( int i = getNegotiation().getContext().getRemainDiscussedCrt().size()-1; i>=0 ; i--){
			Class<? extends Criterion> discussed = getNegotiation().getContext().getRemainDiscussedCrt().get(i);
			CriterionNegotiation<Criterion> cn = getNegotiation().getValueNegotiation(discussed);
			for(Criterion c: cn.getOther().getPreferences(status)){
				if(!cn.isAccepted(c) && !cn.isRejected(c))
					statements.add(c);
			}
		}
		return statements;
	}

	public PreferenceMove ask(){
		Class<? extends Criterion> c;
		c=getNegotiation().getContext().getCurrentDisucussedCriterion();
		Criterion currentAsk = getNegotiation().getValueNegotiation(c).ask();
		if(currentAsk == null){
			c =  getNegotiation().getContext().openNewDiscussion(getNegotiation().getCriteria().getElements());
			if(c == null)
				return null;
		}

		return new PreferenceMove(new Statement<Criterion>(currentAsk),c, false, UtType.ASK);
	}

	public List<Criterion> getPossibleStatements(){

		List<Criterion> statements = new ArrayList<Criterion>();
		Class<? extends Criterion> c=getNegotiation().getContext().getCurrentDisucussedCriterion();
		statements.addAll(getNegotiation().getValueNegotiation(c).remainValues());
		
		c =  getNegotiation().getContext().openNewDiscussion(getNegotiation().getCriteria().getElements());
		
		if(c != null)
			statements.addAll(getNegotiation().getValueNegotiation(c).remainValues());


		return statements;
	}

	public boolean isLastStatement(Utterance utterance){
		if(utterance instanceof Say){
			
		 Say utt = (Say) utterance;
		 
		if(utt.getText().equals("I've told you all I like about "+ 
				getNegotiation().getTopic().getSimpleName() + "s !"))
				
			return true;
		}
		return false;
	}
}
