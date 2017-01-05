package fr.limsi.negotiate.jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.restaurant.*;

public class remainValues {

	@Test
	public void test() {
		Negotiation<Restaurant> m = new GenerateModel().model1();
		Statement<Criterion> s1 = new Statement<Criterion>(Cuisine.CHINESE, Satisfiable.TRUE);
		//Statement<Cuisine> s2 = new Statement<Cuisine>(Cuisine.CHINESE, Satisfiable.FALSE);
		m.getValueNegotiation(Cuisine.class).addStatement(s1, true);
		System.out.println(m.getValueNegotiation(Cuisine.class).getOther().getPreferences(Satisfiable.UNKOWN));
		assertFalse(m.getValueNegotiation(Cuisine.class).getOther().getPreferences(Satisfiable.UNKOWN).contains(Cuisine.CHINESE));
		// TEST FOR OPTIONS
//		CriterionProposal pc = new CriterionProposal(false, Cuisine.FRENCH);
//		pc.setStatus(Status.REJECTED);
//		m.addProposal(pc);
//		
//		OptionProposal po = new OptionProposal(false, Restaurant.ALBACIO);
//		po.setStatus(Status.REJECTED);
//		m.addProposal(po);
//		//assertFalse(m.remainOptions().contains(Restaurant.ALBACIO));
//		//assertTrue(m.getValueNegotiation(Cuisine.class).isRejected(value))
//		assertFalse(m.remainOptions().contains(Restaurant.BEAUREPAIRE));
	}

}
