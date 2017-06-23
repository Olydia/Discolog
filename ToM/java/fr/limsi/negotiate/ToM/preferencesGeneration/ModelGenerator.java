package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.lang.*;
import fr.limsi.negotiate.toyExample.*;

public class ModelGenerator {
	// other possible models of preferences
	// other possible power 
	// mirror of self in order to create a negotiation
//	List<Self_Ci<Criterion>> otherPref;
//	Negotiation<? extends Option> other;
//	
//	
//	public Guess(Negotiation<? extends Option> self, List<Self_Ci<Criterion>> otherPref) {
//		//instance of negotiation
//		this.other = mirrorNegotiation(otherPref, self);
//		this.otherPref = otherPref;
//	}


	// method mirrors of proposals (self vs other proposals)
	public ArrayList <CriterionProposal>  mirrorCriterionProposals (ArrayList<CriterionProposal> proposals){
		ArrayList<CriterionProposal> p = new ArrayList<CriterionProposal>();
		for(CriterionProposal prop : proposals){
			p.add(prop.mirrorProposal());
		}
		
		return p;
			
	}
	
	public ArrayList <OptionProposal>  mirrorOptionProposals (ArrayList<OptionProposal> proposals){
		ArrayList<OptionProposal> p = new ArrayList<OptionProposal>();
		for(OptionProposal prop : proposals){
			p.add( prop.mirrorProposal());
		}
		
		return p;
			
	}
	
	// method change self statements to Other
	public Other <Criterion> selfToOther( ArrayList<Statement<Criterion>> selfStatements, Class<Criterion> type ){
		
		Other<Criterion> other = new Other<Criterion> (type);
		other.setPreferences(selfStatements);
		return other;
		
	}
	
	// method to change other Statements to Self
	
	public List<Statement<Criterion>> otherToSelf(Other<Criterion> other){
		List<Statement<Criterion>> selfStatements = new ArrayList<Statement<Criterion>>(other.getPreferences());
		return selfStatements;
		
	}
	
	// mirror of the dialogue context
	public DialogueContext mirrorContext(DialogueContext dc){
		DialogueContext mirror = new DialogueContext(dc.getTopicValues());
		mirror.setDiscussedCriteria(dc.getDiscussedCriteria());
		mirror.setClosedCriteria(dc.getClosedCriteria());
		
		ArrayList<NegotiationUtterance> history =new ArrayList<NegotiationUtterance>();
		for(NegotiationUtterance u : dc.getHistory())
			history.add(u.mirrorCopy());
		
		mirror.setHistory(history);
		return mirror;
		
	}
	
	// for each criterionNegotiation update mirror
	
	
	// call respond to for each criterion model
	public CriterionNegotiation<Criterion> mirrorCriterionNego (CriterionNegotiation<Criterion> selfCn, Self_Ci<Criterion> otherPref){
//		private Self_Ci<C> self; 	private Class<C> type; 
		CriterionNegotiation<Criterion> other = new CriterionNegotiation<Criterion>(otherPref);
//		private Other<C> other;
		other.setOther(selfToOther(new ArrayList<Statement<Criterion>> 
									(selfCn.getSelfStatements()), selfCn.getType()));
		
//		private List<Statement<C>> selfStatements;
		other.setSelfStatements(otherToSelf(selfCn.getOther()));
		
//		private ArrayList<CriterionProposal> proposals;
		other.setProposals(mirrorCriterionProposals(selfCn.getProposals()));
		
		return other;
	}
	
	
	// create the negotiation model
	@SuppressWarnings("unchecked")
	public	Negotiation<? extends Option> mirrorNegotiation(PrefNegotiation<Option> otherPref,
	
		// Create the preferences of the model	
		Negotiation<? extends Option> self){
		List<CriterionNegotiation<Criterion>> valueNegotiation = new ArrayList<CriterionNegotiation<Criterion>> ();
		
		
		for(Self_Ci<Criterion> pref: otherPref.getValues_prefs()){
			CriterionNegotiation<Criterion> cn  = self.getValueNegotiation(pref.getType());
			valueNegotiation.add(mirrorCriterionNego(cn, pref));
		}
				
	// -----
		DialogueContext context = mirrorContext(self.getContext());
		
		return new Negotiation (valueNegotiation, otherPref.getCriteria_prefs(),
				self.getTopic(), context, mirrorOptionProposals(self.getProposals()));
	}
	
	// Create the method that guesses the model (or the list of models)
	
	
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
	
	
	
//	// create a method to compare two utterances
//	public static void main (String[] args) {
//		Negotiation<ToyRestaurant> model = new totalOrderedModels().model1();
//		model.addProposal(new CriterionProposal(true,ToyCuisine.FRENCH));
//		model.addProposal(new CriterionProposal(false, ToyAtmosphere.QUIET));
//		model.addStatement(new Statement<Criterion>(ToyCuisine.FRENCH, true), false);
//		model.addStatement(new Statement<Criterion>(ToyAtmosphere.QUIET, true), true);
//
//		ToMNegotiator tom = new ToMNegotiator("TOM", new totalOrderedModels().model1());
//		List<Self_Ci<Criterion>> other = tom.otherModel.get(0.9).get(0);
//
//	}

		

	
}
