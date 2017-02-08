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

	public static double  DOMINANT = 0.7, SUBMISSIVE = 0.5;

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
		totalOrderedModels model = new totalOrderedModels();

		Dual dual = new Dual(
				new NegotiatorAgent("Agent1", model.model1()), 
				new NegotiatorAgent("Agent2", model.model3()), 
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
		if ( utterance != null )System.out.println(utterance.format() + "\n");
		if ( utterance == null ) {
			 if (relation >  NegotiationParameters.sigma && negotiation.negotiationFailure(utterance))

				return new Say(disco, false, "Sorry, but I no longer want to do for dinner");
			 else{
			 
			Class<? extends Criterion> opent = getNegotiation().getCriteria().sortValues().get(0);

			if(relation > NegotiationParameters.sigma){

				return new Propose(disco, false, getNegotiation().chooseProposal());

			} else
				return new AskPreference(disco, false, opent, null);	
			 }
			
		}else if(closeNegotiation(utterance)){
			//System.exit(0);
			disco.getInteraction().exit();
		
			return new Say(disco, false, "Okay");
			
		}else if(endDialogue(utterance)) {
			return new Say(disco, false, "Okay");

		}else if (relation >  NegotiationParameters.sigma && negotiation.negotiationFailure(utterance)){


			return new Say(disco, false, "Sorry, but I no longer want to do for dinner");

//		} else if (negotiation.negotiationSuccess(relation, utterance)!= null){
//			
//
//			//Option o = negotiation.negotiationSuccess(relation, utterance);
//			//return new Say(disco, false, "Let's book a table at the " + o.toString() + " " + o.getClass().getSimpleName());

		} else if ( utterance instanceof AskPreference && !takeThelead()) {

			PreferenceMove ask = (PreferenceMove)getNegotiation().getContext().getLastMove(true);
			Statement<Criterion> state = respondToAsk(ask);
			return new StatePreference(disco, false, state.getValue(), state.getStatus());

		} else if(isLastStatement(utterance)){

			List<Proposal> proposals = getNegotiation().remainProposals();
			if(proposals.isEmpty())
				return new Say(disco, false, "Sorry, but I no longer want to do for dinner!");

			else{
				Proposal p = sortProposals(proposals).get(0);
				return new Propose(disco, false, p);

			}


			// DOMINANT case only propose !		
		}else if (relation > NegotiationParameters.sigma && !getNegotiation().remainProposals().isEmpty()){
			//System.out.println("t =" +getNegotiation().computeT() +"  Self(t) =" + getNegotiation().self());
			if(isProposition(utterance)){
				
				// if the proposal is an optionProposal  and its acceptable accept
				// Otherwise 
				Proposal u = ((ProposalUtterance) utterance).getProposal();

				Proposal p = getNegotiation().chooseProposal();

				if(getNegotiation().isAcceptable(u)){
					if(u instanceof OptionProposal)
						return new Accept(disco, false, u);
					
					Option bestOption = getNegotiation().chooseOption(getNegotiation().remainOptions());
					return new AcceptPropose(disco, false, (CriterionProposal)u, createProposal(bestOption, false));

				} else 
					return new RejectPropose(disco, false,u, p);


			}
			else
				return new Propose(disco, false, getNegotiation().chooseProposal());



			//SUBMISSIVE cases
		}else { 
		//	System.out.println(getNegotiation().computeT() +"  Self(t) =" + getNegotiation().self());

			Class<? extends Criterion> c=getNegotiation().getContext().getCurrentDisucussedCriterion();
			
			// REJECT
			if(canReject(utterance)) {
				Proposal p = ((Propose) utterance).getProposal();
				p.setStatus(Status.REJECTED);
				
				return new RejectState(disco, false, p, getNegotiation().justifyReject(p));
				
				//ACCEPT
			}else if(getAcceptableProposal(c)!= null){

				Proposal p = getAcceptableProposal(c);
				p.setStatus(Status.ACCEPTED);
				return new Accept(disco, false, p);

			}else if(canPropose(utterance) != null) {

				// PROPOSE
				return new Propose (disco, false, canPropose(utterance));

				//ASK
			}else if (canAsk()!= null){
				PreferenceMove askValue = ask(canAsk());
				return new AskPreference(disco, false, askValue.getValueType(), askValue.getValue().getValue());

				// STATE
			}else {
				List<Criterion> sts = getPossibleStatements();
				if(!sts.isEmpty()){
					// do a statement from the remain values !

					Criterion  value = sts.get(0);
					Satisfiable status = getNegotiation().getValueNegotiation(value.getClass()).getSelf().isSatisfiable(value, getNegotiation().self());
					return new StatePreference(disco, false, value, status);
				}

					//return new Say(disco, false, "I've told you all I like about "+ getNegotiation().getTopic().getSimpleName() + "s !");
				else {
					Proposal p = getNegotiation().chooseProposal();
					return new Propose (disco, false, p);
					
				}
			}

		}
	}

	// JavasScript helpers from Negotiation.d4g.xml translated to Java


	private boolean isProposition(Utterance utterance) {
		if(utterance instanceof Propose || utterance instanceof RejectPropose || utterance instanceof AcceptPropose)
			return true;
		return false;
	}

	public Utterance finalStatement(Disco disco){
		return new Say(disco, true, "I've told you all I like about "+ getNegotiation().getTopic().getSimpleName() + "s !");
	}


	public Proposal canPropose(Utterance u) {
		if(u instanceof StatePreference ){

			List<Criterion> oStats = getOtherStatements(Satisfiable.TRUE);
			if(!oStats.isEmpty()){
				oStats.sort(new Comparator<Criterion>() {
					@Override
					public int compare(Criterion c1, Criterion c2){
						CriterionNegotiation<Criterion> cn1 = getNegotiation().getValueNegotiation(c1.getClass());
						CriterionNegotiation<Criterion> cn2 = getNegotiation().getValueNegotiation(c2.getClass());
						return Float.compare(cn2.getSelf().satisfaction(c2), cn1.getSelf().satisfaction(c1));
					}
				});
				Criterion first = oStats.get(0);
				CriterionNegotiation<Criterion> cn1 = getNegotiation().getValueNegotiation(first.getClass());
				if(cn1.getSelf().satisfaction(first) >= getNegotiation().self())
					return new CriterionProposal(first);

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

	private boolean canReject(Utterance utterance){
		if(isProposition(utterance)){
			Proposal p = (Proposal) utterance.getSlotValue("proposal");
			if(!getNegotiation().isAcceptable(p) && 
					getNegotiation().computeT()< NegotiationParameters.tau)
				return true;

		}
		return false; 
	}

	private Proposal getAcceptableProposal(Class<? extends Criterion> c) {

		//1. Check acceptable values in the current discussed criterion
		List<Proposal> proposals = new ArrayList<Proposal>();
		proposals.addAll(getNegotiation().getValueNegotiation(c).getProposalsWithStatus(Status.OPEN, false));

		for(Proposal p: sortProposals(proposals)){
			if(getNegotiation().isAcceptable(p))
				return p;

		}
		//2. if 1 is not valid, check acceptable proposals in the previous discussed criteria

		List<Proposal> previousP = new ArrayList<Proposal>();

		for( int i =0; i< getNegotiation().getContext().getRemainDiscussedCrt().size()-1; i++ ){
			Class<? extends Criterion> discussed = getNegotiation().getContext().getRemainDiscussedCrt().get(i);
			previousP.addAll(getNegotiation().getValueNegotiation(discussed).getProposalsWithStatus(Status.OPEN));
		}


		previousP.addAll(getNegotiation().getOptionsProposals(Status.OPEN));

		if(!previousP.isEmpty()){

			Proposal p = sortProposals(previousP).get(0);
			if(getNegotiation().isAcceptable(p))
				return p;
		}

		//3. Otherwise there is nothing to accept
		return null;		
	}

	// JavasScript helpers from Negotiation.d4g.xml translated to Java


	public Statement<Criterion> respondToAsk(PreferenceMove ask){
		if(ask.getValue().getValue() == null){
			Criterion value = getNegotiation().getValueNegotiation(ask.getValueType()).getSelf().sortValues().get(0);
			return new Statement<Criterion>(value, Satisfiable.TRUE);
		}
		else{
			Satisfiable sat = getNegotiation().getValueNegotiation(ask.getValueType()).
					getSelf().isSatisfiable(ask.getValue().getValue(), getNegotiation().self());
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
	public fr.limsi.negotiate.Statement.Satisfiable Satisfiable (Criterion c){
		return getNegotiation().getValueNegotiation(c.getClass()).getSelf().isSatisfiable(c, getNegotiation().self());
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
		if(p instanceof CriterionProposal){
			@SuppressWarnings("unchecked")
			CriterionNegotiation<Criterion> model = this.getNegotiation().getValueNegotiation(
					(Class<? extends Criterion>) p.getValue().getClass());

			return model.tolerable((Criterion)p.getValue(), getNegotiation().self());
		}
		else 
			return getNegotiation().tolerable((Option)p.getValue());
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

	public PreferenceMove ask(Class <? extends Criterion> c){
		Criterion currentAsk = getNegotiation().getValueNegotiation(c).ask();

		return new PreferenceMove(new Statement<Criterion>(currentAsk),c, false, UtType.ASK);
	}


	public Class <? extends Criterion> canAsk(){
		Class<? extends Criterion> c = getNegotiation().getContext().getCurrentDisucussedCriterion();
		if(!getNegotiation().getValueNegotiation(c).getOther().getPreferences(Satisfiable.UNKOWN).isEmpty())
			return c;

		else {
			List<Class<? extends Criterion>> discussions = getNegotiation().getCriteria().getElements();
			for(Class<? extends Criterion> dis: getNegotiation().getContext().getPossibleDiscussions(discussions))
				if(!getNegotiation().getValueNegotiation(dis).getOther().getPreferences(Satisfiable.UNKOWN).isEmpty())
					return dis;

		}

		return null;

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

	public boolean endDialogue (Utterance utterance){
	
		if(utterance instanceof Say){

			Say utt = (Say) utterance;
			System.out.println(utt.getText());

			if(utt.getText().contains("Sorry, but I no longer want to do for dinner"))

				return true;
		}
		return false;
	}
	
	public boolean closeNegotiation(Utterance utterance){
		
		if(utterance instanceof Say){

			Say utt = (Say) utterance;

			return (utt.getText().contains("Okay"));

		}
		
		return (negotiation.negotiationSuccess());
		}
	
	public boolean takeThelead(){
		int nbPreferences =0;
		
		if(getNegotiation().self()<= NegotiationParameters.sigma)
			return false;
		
		for(NegoUtterance utt : getNegotiation().getContext().getHistory()){
			if ( utt instanceof PreferenceMove)
				nbPreferences++;
			else nbPreferences =0; 
			
		}
		
		return  (nbPreferences >= NegotiationParameters.alpha);
	}
}
