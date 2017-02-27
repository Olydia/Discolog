package fr.limsi.negotiate;

import java.util.ArrayList;

import edu.wpi.disco.Dual;
import fr.limsi.negotiate.movie.GenerateMovieModel;
import fr.limsi.negotiate.movie.Movie;
import fr.limsi.negotiate.restaurant.Restaurant;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class TestDialogues {

	public static void main(String[] args) {

		
		totalOrderedModels models = new totalOrderedModels();
		ArrayList<Negotiation<Restaurant>> negotiation = models.getModels();


		for(Negotiation<? extends Option> modelA1:negotiation ){
			for(Negotiation<? extends Option> modelA2: negotiation){
				// Initiate the model of preferences 
				int indexA =  negotiation.indexOf(modelA1);
				int indexB =  negotiation.indexOf(modelA2);
				//initiate the relation of dominance
				double relationAgent1 =0.9;
				double relationAgent2 ;

				for(int i= 0; i<4; i++){
					relationAgent2 = 0.2;
					for(int j= 0; j<3; j++){

						if(indexA != indexB){
							System.out.println("-------------------------------------Dominant-model" + (indexA+1)+ 
									"___Submissive-model"+  (indexB+1)+ "   " +relationAgent1 + "__" + relationAgent2 +" ------------------------------------");

							Dual dual = new Dual(
									new NegotiatorAgent("Agent1", modelA1), 
									new NegotiatorAgent("Agent2", modelA2), 
									false);

							// note not loading Negotiotion.xml!
							dual.interaction1.load("models/Negotiate.xml");
							dual.interaction2.load("models/Negotiate.xml");
							((NegotiatorAgent) dual.interaction1.getSystem()).setRelation(relationAgent1);
							((NegotiatorAgent) dual.interaction2.getSystem()).setRelation(relationAgent2);
							((NegotiatorAgent) dual.interaction1.getSystem()).getNegotiation().setDominance(relationAgent1);
							((NegotiatorAgent) dual.interaction2.getSystem()).getNegotiation().setDominance(relationAgent2);

							dual.start();
							try {
								dual.interaction1.join();
								dual.interaction2.join();



							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						modelA1.clearNegotiation();
						modelA2.clearNegotiation();
						//increase the submissive relation
						relationAgent2 = relationAgent2 +0.1;
					}
					//decrease the dominant relation
					relationAgent1 = relationAgent1 -0.1;
				}
			}
		}
	}
}
