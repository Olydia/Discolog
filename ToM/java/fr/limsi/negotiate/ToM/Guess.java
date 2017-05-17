package fr.limsi.negotiate.ToM;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.DC;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.NegotiatorAgent;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.Other;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.Statement;
import fr.limsi.negotiate.lang.*;

public class Guess {
	// other possible models of preferences
	// other possible power 
	// mirror of self in order to create a negotiation
	ArrayList<Self_Ci<Criterion>> otherPref;
	Self_C<Option> otherC;
	
	Negotiation<? extends Option> other;
	ArrayList<Double> hypo_pow;
	
	
	public Guess(Negotiation<? extends Option> self, ArrayList<Self_Ci<Criterion>> otherPref,
			Self_C<Option> otherC) {
		this.other = mirrorNegotiation(otherPref, otherC, self);
		//(ArrayList<Self_Ci<Criterion>> otherPref,	Self_C<Option> otherC, Negotiation<? extends Option> self
		this.otherC = otherC;
		this.otherPref = otherPref;
		hypo_pow = new ArrayList<Double> ();
		double elem = 0;
		for(int i=0; i<10; i++){
			elem=elem+0.1;
			hypo_pow.add(elem);
		}
	}
	
	public ArrayList<Double> guess(Utterance selfUt, Utterance otherUt, Disco disco){
		ArrayList<Double>  hypos = new ArrayList<Double> ();

		for(double pow: hypo_pow){
			NegotiatorAgent current = new NegotiatorAgent("otherTom", this.other);
			current.setRelation(pow);
			Utterance ut = current.respondTo(selfUt, disco);
			if(identicalUtterances(ut, otherUt))
				hypos.add(pow);
		}
		return hypos;
	}

	// method mirrors of proposals (self vs other proposals)
	public ArrayList <? extends Proposal>  mirrorProposals (ArrayList<? extends Proposal> proposals){
		ArrayList<? extends Proposal> p = new ArrayList<Proposal>(proposals);
		for(Proposal prop : p){
			boolean self = prop.isSelf();
			prop.setIsSelf(self);
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
	public DC mirrorContext(DC dc){
		DC mirror = new DC(dc.getTopicValues());
		mirror.setDiscussedCriteria(dc.getDiscussedCriteria());
		mirror.setClosedCriteria(dc.getClosedCriteria());
		
		ArrayList<NegotiationUtterance> history =new ArrayList<NegotiationUtterance>(dc.getHistory());
		for(NegotiationUtterance u : history)
			u.setExternal(!u.getExternal());
		
		mirror.setHistory(history);
		return mirror;
		
	}
	
	// for each criterionNegotiation update mirror
	
	
	// call respond to for each criterion model
	@SuppressWarnings("unchecked")
	public CriterionNegotiation<Criterion> mirrorCriterionNego (CriterionNegotiation<Criterion> selfCn, Self_Ci<Criterion> otherPref){
//		private Self_Ci<C> self; 	private Class<C> type; 
		CriterionNegotiation<Criterion> other = new CriterionNegotiation<Criterion>(otherPref);
//		private Other<C> other;
		other.setOther(selfToOther(new ArrayList<Statement<Criterion>> 
									(selfCn.getSelfStatements()), selfCn.getType()));
		
//		private List<Statement<C>> selfStatements;
		other.setSelfStatements(otherToSelf(selfCn.getOther()));
		
//		private ArrayList<CriterionProposal> proposals;
		other.setProposals((ArrayList<CriterionProposal>) mirrorProposals(selfCn.getProposals()));
		
		return other;
	}
	
	// create the negotiation model
	
	Negotiation<? extends Option> mirrorNegotiation(ArrayList<Self_Ci<Criterion>> otherPref,
	Self_C<Option> otherC, Negotiation<? extends Option> self){
		List<CriterionNegotiation<Criterion>> valueNegotiation = new ArrayList<CriterionNegotiation<Criterion>> ();
		
		for(Self_Ci<Criterion> pref: otherPref){
			CriterionNegotiation<Criterion> cn  = self.getValueNegotiation(pref.getType());
			valueNegotiation.add(mirrorCriterionNego(cn, pref));
		}
				
		DC context = mirrorContext(self.getContext_bis());
		
		return new Negotiation (valueNegotiation, otherC,
				self.getTopic(), context, mirrorProposals(self.getProposals()));
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
	
	
	
	// create a method to compare two utterances
	public static void main (String[] args) {
		//Guess m = new Guess(new totalOrderedModels().model1());
		//System.out.println(m.hypo_pow);
	}

		

	
}
