package fr.limsi.negotiate.ToM;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.NegotiationParameters;

public class OtherCriterionNegotiation<C extends Criterion> extends CriterionNegotiation<C> {
	private OtherSelf<C> self;
	
//	public OtherCriterionNegotiation() {
//		
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//
//	public float tolerable(C value, double self){
//		//return (float) ((self*getSelf().satisfaction(value)) + ((1-self)*getOther().other(value)));
//	}
//	
//	@Override
//	public List<C> getAcceptableValues(List<C> V, final double self){
////		List<C> acc = new ArrayList<C> ();
////		for(C value: V){
////	
////			if(isAcceptable(value, self))
////				acc.add(value);	
////		}
////		
////		return acc;
//	}
//	
	@Override
	public boolean isAcceptable(C c, double self){
		 return getSelf().satisfaction(c)>= NegotiationParameters.beta * self;
	}
}
