package fr.limsi.negotiate.restaurant;

import static org.junit.Assert.assertTrue;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionPrefModel;
import fr.limsi.negotiate.CriterionPreference;
import fr.limsi.negotiate.CriteriaClassPrefModel;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Proposal;
import fr.limsi.negotiate.ValuePreference;


public class TestRestaurant {

	public static void main(String[] args) {
		// 1. Define lydia preference model on each criterion of restaurant
		CriterionPrefModel<Cuisine> lydia_cuisine = new CriterionPrefModel<Cuisine>();
		lydia_cuisine.setType(Cuisine.class);
		lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.FRENCH, Cuisine.CHINESE));
		lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.JAPANESE, Cuisine.CHINESE));
		lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.JAPANESE));
		lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.ITALIAN, Cuisine.FRENCH));
		lydia_cuisine.add(new ValuePreference<Cuisine>(Cuisine.TURKISH, Cuisine.JAPANESE));

		// 1.2. Preference model on Cost
		CriterionPrefModel<Cost> lydia_cost = new CriterionPrefModel<Cost>();
		lydia_cost.setType(Cost.class);
		lydia_cost.add(new ValuePreference<Cost>(Cost.EXPENSIVE, Cost.CHEAP));

		// 1.3 Preference model on Ambiance 
		CriterionPrefModel<Ambiance> lydia_ambiance = new CriterionPrefModel<Ambiance>();
		lydia_ambiance.setType(Ambiance.class);
		lydia_ambiance.add(new ValuePreference<Ambiance>(Ambiance.NOISY, Ambiance.CALM));
		/*1. Define the  preferences on Restaurant criteria */	

		CriteriaClassPrefModel<Restaurant> lydia_criteria = new CriteriaClassPrefModel<Restaurant>(); 
		lydia_criteria.setType(Restaurant.class); // Its is not the idial solution but I have to get the type of an option 
		lydia_criteria.add(new CriterionPreference(Cuisine.class,Cost.class));
		lydia_criteria.add(new CriterionPreference(Cost.class,Ambiance.class));


		//		/*2. Define the agent mental state on each criterion (self pref, user pref, proposals */		
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
		cost.setSelfPreferences(lydia_cost);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<Cuisine>(Cuisine.class);
		cuisine.setSelfPreferences(lydia_cuisine);

		CriterionNegotiation<Ambiance> ambiance = new CriterionNegotiation<Ambiance>(Ambiance.class);
		ambiance.setSelfPreferences(lydia_ambiance);

		//
				/*3. Create a nogotiation on restaurant */
		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> restaurants = new Negotiation<Restaurant>
		(new CriterionNegotiation[] {cost, cuisine, ambiance}, lydia_criteria);
		//
		//		// Test the DFS preference method (it should return true 
		//		System.out.println("Chinses score: " + lydia_cuisine.getScore(Cuisine.ITALIAN)+ " Turkich Score: " + lydia_cuisine.getScore(Cuisine.TURKISH));
		//		System.out.println(lydia_cuisine.isPreferred(Cuisine.CHINESE, Cuisine.TURKISH));
		//		System.out.println(" Cost : " + lydia_criteria.getRank(Cost.class) + " Cuisine : " + lydia_criteria.getRank(Cuisine.class) + " Ambiance : " + lydia_criteria.getRank(Ambiance.class));
		//		System.out.println(lydia_criteria.isPreferred(Ambiance.class, Cuisine.class));
		//		// Test of the decision function and it returns CHEZ_CHUCK because cost is cheap and cost is more preferred to cuisine

//		Restaurant res = restaurants.getPreferredOption(Restaurant.CHEZ_CHUCK, Restaurant.LE_PARISIEN);
//		System.out.println(res.name());
	//	System.out.println("le res est " + restaurants.getCriterionNegotiation(Cuisine.class).getSelf().getMostPreferred());
//		System.out.println(lydia_cuisine.getMostPreferred());
//		System.out.println(restaurants.mostPreferredCriterion(Cuisine.class));
//		
			
//		OptionProposal c = new OptionProposal(true, Restaurant.CHEZ_CHUCK);
		CriterionProposal c = new CriterionProposal(true, Cuisine.JAPANESE);
		CriterionProposal c1 = new CriterionProposal(true, Cuisine.CHINESE);
		c1.setStatus(Proposal.Status.REJECTED);

////
		restaurants.addProposal(c);
		System.out.println(restaurants.context.getListStatements());

		restaurants.addProposal(c1);
		System.out.println("le dernier "+ restaurants.context.getLastStatement("REJECTED", true));

//		//cuisine.propose(c);
//		System.out.println(restaurants.context.getLastProposal("REJECTED"));
////		restaurants.updateProposalStatus(c, Proposal.Status.REJECTED);
////		System.out.println(restaurants.mostPreferredCriterion(Cuisine.class));
////
////		System.out.println(restaurants.getProposals());
////		System.out.println(restaurants.checkProposalStatus(c));
//		

	}
}
