package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class Guess {
	// other possible models of preferences
	// other possible power 
	// mirror of self in order to create a negotiation
	ArrayList<Self_Ci<? extends Criterion>> otherPref;
	Self_C<Option> otherC;
	
	Negotiation<? extends Option> other;
	ArrayList<Double> hypo_pow;
	
	public Guess(Negotiation<? extends Option> other, ArrayList<Self_Ci<? extends Criterion>> otherPref,
			Self_C<Option> otherC) {
		this.other = other;
		this.otherC = otherC;
		this.otherPref = otherPref;
		hypo_pow = new ArrayList<Double> ();
		double elem = 0;
		for(int i=0; i<10; i++){
			elem=elem+0.1;
			hypo_pow.add(elem);
		}
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
	public DialogueContext mirrorContext(DialogueContext dc){
		DialogueContext mirror = new DialogueContext(dc.getTopicValues());
		mirror.setDiscussedCriteria(dc.getDiscussedCriteria());
		mirror.setClosedCriteria(dc.getClosedCriteria());
		
		ArrayList<NegoUtterance> history =new ArrayList<NegoUtterance>(dc.getHistory());
		for(NegoUtterance u : history)
			u.setExtrenal(!u.isExtrenal());
		
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
	Self_C<Option> otherC,	double pow, Negotiation<? extends Option> self){
		List<CriterionNegotiation<Criterion>> valueNegotiation = new ArrayList<CriterionNegotiation<Criterion>> ();
		ArrayList<OptionProposal> proposals =new ArrayList<OptionProposal>();
		
		for(Self_Ci<Criterion> pref: otherPref){
			CriterionNegotiation<Criterion> cn  = self.getValueNegotiation(pref.getType());
			valueNegotiation.add(mirrorCriterionNego(cn, pref));
		}
				
		DialogueContext context = mirrorContext(self.getContext());
		
		return new Negotiation (valueNegotiation, pow,
			otherC, self.getTopic(), context, mirrorProposals(self.getProposals()));
	}
	
	// method to select the preference model 
	
	Self_Ci<? extends Criterion> getPrefModel(Class<? extends Criterion> type,ArrayList<Self_Ci<? extends Criterion>> otherPref){
		for(Self_Ci<? extends Criterion> elem: otherPref){
			if(elem.getType().equals(type))
				return elem;
		}
		return null;
	}
	
	// create a method to compare two utterances
	public static void main (String[] args) {
		//Guess m = new Guess(new totalOrderedModels().model1());
		//System.out.println(m.hypo_pow);
	}

		

	
}
