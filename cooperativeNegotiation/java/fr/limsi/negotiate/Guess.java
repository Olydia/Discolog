package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class Guess {
	// other possible models of preferences
	// other possible power 
	// mirror of self in order to create a negotiation
	Negotiation<? extends Option> other;
	ArrayList<Double> hypo_pow;
	
	public Guess(Negotiation<? extends Option> other) {
		this.other = other;
		hypo_pow = new ArrayList<Double> ();
		double elem = 0;
		for(int i=0; i<10; i++){
			elem=elem+0.1;
			hypo_pow.add(elem);
		}
	}

	// method mirrors of proposals (self vs other proposals)
	public List<Proposal>  mirrorProposals (List<Proposal> proposals){
		List<Proposal> p = new ArrayList<Proposal>(proposals);
		for(Proposal prop : p){
			boolean self = prop.isSelf();
			prop.setIsSelf(self);
		}
		
		return p;
			
	}
	// method change other to oas
	public Other <Criterion> mirrosOther( ArrayList<Statement<Criterion>> oas, Class<Criterion> type ){
		
		Other<Criterion> other = new Other<Criterion> (type);
		other.setPreferences(oas);
		return other;
		
	}
	
	
	// mirror of the dialogue context
	
	
	// for each criterionNegotiation update mirror
	
	// call respond to for each criterion model
	
	// create a method to compare two utterances
	public static void main (String[] args) {
		Guess m = new Guess(new totalOrderedModels().model1());
		System.out.println(m.hypo_pow);
	}

		

	
}
