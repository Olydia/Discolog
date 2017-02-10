package JunitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.restaurant.Atmosphere;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.InitiaterestauMentalState;
import fr.limsi.negotiate.restaurant.Restaurant;

public class ProposalUpdate {

	@Test
	public void test() {
		InitiaterestauMentalState mv = new InitiaterestauMentalState();
		Negotiation<Restaurant> nmv= mv.D1();
		CriterionProposal p1 = new CriterionProposal(false, Cuisine.CHINESE);
		p1.setStatus(Status.OPEN);
		CriterionProposal p2 = new CriterionProposal(true, Cuisine.CHINESE);
		p2.setStatus(Status.REJECTED);
		CriterionProposal p3 = new CriterionProposal(false, Cuisine.ITALIAN);
		p3.setStatus(Status.OPEN);
		CriterionProposal p4 = new CriterionProposal(true, Atmosphere.FAMILY);
		p4.setStatus(Status.OPEN);
		ArrayList<Proposal> props = new ArrayList<Proposal>() {{add(p1); add(p2); add(p3);
		}};
	
		nmv.getContext().setProposals(props);
		nmv.getContext().updateProposals(p4);
		System.out.println(props);
	}

}
