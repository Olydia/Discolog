package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.sun.org.apache.xpath.internal.operations.Neg;
import edu.wpi.disco.Dual;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import fr.limsi.negotiate.ToM.expirement.FileAppend;
import fr.limsi.negotiate.ToM.preferencesGeneration.Models;
import fr.limsi.negotiate.restaurant.Restaurant;
import fr.limsi.negotiate.toyExample.*;

public class TestDialogues {
	// creer les models de pref
	public static int ID = 0;
	public static void excuteTests(Negotiation<? extends Option> negotiation, 
		Negotiation<? extends Option> negotiation2){
		String local=System.getProperty("user.dir");

		String path = local+"/Exp_Tom/" ;
		String csvPath = path+"/CSV/results.csv";
		FileAppend csvFile = new FileAppend(csvPath);
		csvFile.write("ID;ToMAgent;NegotiatorAgent;FinalGuess;GuessedValues \n");
		//initiate the relation of dominance
		double relationAgent1 =0.9;
		double relationAgent2 ;

		for(int i= 0; i<4; i++){
			relationAgent2 = 0.3;
			for(int j= 0; j<3; j++){

				//					System.out.println("-------------------------------------Dominant-model" + (indexA+1)+ 
				//							"___Submissive-model"+  (indexB+1)+ "   " +relationAgent1 + "__" + relationAgent2 +" ------------------------------------");
				ID ++;
			//	String fileName = path+"ID "+ID+"_Nego_" + relationAgent2 +"_"+ "ToM-"+ relationAgent1;
				String fileName = path+"ID "+ID+"_Nego_" + relationAgent1 +"_"+ "ToM-"+ relationAgent2;
				FileAppend file = new FileAppend(fileName);

				Dual dual = new Dual(
						new ToMNegotiatorProba("Agent1", negotiation, ADAPT.COMPLEMENT),
						new NegotiatorAgent("Agent2", negotiation2),
						false);

				// note not loading Negotiotion.xml!
				dual.interaction1.load("models/Negotiate.xml");
				dual.interaction2.load("models/Negotiate.xml");
				((NegotiatorAgent) dual.interaction2.getSystem()).setRelation(relationAgent1);
				((ToMNegotiatorProba) dual.interaction1.getSystem()).setRelation(relationAgent2);
				((NegotiatorAgent) dual.interaction2.getSystem()).getNegotiation().setDominance(relationAgent1);
				((ToMNegotiatorProba) dual.interaction1.getSystem()).getNegotiation().setDominance(relationAgent2);
				long startTime = System.currentTimeMillis();
				dual.start();
				try {
					
					dual.interaction1.join();
					dual.interaction2.join();

					long endTime = System.currentTimeMillis();


					List<Double> pow =((ToMNegotiatorProba) dual.interaction1.getSystem()).getGuessed();
					double finalOther = ((ToMNegotiatorProba) dual.interaction1.getSystem()).getOther();
					String guessedPow = pow.toString();
					//csvFile.write(ID+";"+relationAgent2 + ";"+relationAgent1 +";"+finalOther+";"+guessedPow+"\n");
					csvFile.write(ID+";"+relationAgent1 + ";"+relationAgent2 +";"+finalOther+";"+guessedPow+"\n");
					file.write(guessedPow);
					file.write("Time execution : "+ (endTime - startTime));



				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				negotiation.clearNegotiation();
				negotiation2.clearNegotiation();
				//increase the submissive relation
				relationAgent2 = relationAgent2 +0.1;
			}
			//decrease the dominant relation
			relationAgent1 = relationAgent1 -0.1;
		}

	}

	public static void main(String[] args) {

		Models<ToyRestaurant> m = new Models<ToyRestaurant>();
		m.generateSelfs(ToyRestaurant.A_CITADELLA.getCriteria(), ToyRestaurant.class);
		for (Models<ToyRestaurant>.Tuple<List<Self_Ci<Criterion>>,
				List<Self_Ci<Criterion>> > e:m.getFinalList()){
			excuteTests(m.createNegotiation(e.a, ToyRestaurant.class),
					m.createNegotiation(e.b, ToyRestaurant.class));
		}

		//		
		//		totalOrderedModels models = new totalOrderedModels();
		//		ArrayList<Negotiation<Restaurant>> negotiation = models.getModels();
		//
		//
		//		for(Negotiation<? extends Option> modelA1:negotiation ){
		//			for(Negotiation<? extends Option> modelA2: negotiation){
		//				// Initiate the model of preferences 
		//				int indexA =  negotiation.indexOf(modelA1);
		//				int indexB =  negotiation.indexOf(modelA2);
		//				//initiate the relation of dominance
		//				double relationAgent1 =0.9;
		//				double relationAgent2 ;
		//
		//				for(int i= 0; i<4; i++){
		//					relationAgent2 = 0.2;
		//					for(int j= 0; j<3; j++){
		//
		//						if(indexA != indexB){
		//							System.out.println("-------------------------------------Dominant-model" + (indexA+1)+ 
		//									"___Submissive-model"+  (indexB+1)+ "   " +relationAgent1 + "__" + relationAgent2 +" ------------------------------------");
		//
		//							Dual dual = new Dual(
		//									new NegotiatorAgent("Agent1", modelA1), 
		//									new NegotiatorAgent("Agent2", modelA2), 
		//									false);
		//
		//							// note not loading Negotiotion.xml!
		//							dual.interaction1.load("models/Negotiate.xml");
		//							dual.interaction2.load("models/Negotiate.xml");
		//							((NegotiatorAgent) dual.interaction1.getSystem()).setRelation(relationAgent1);
		//							((NegotiatorAgent) dual.interaction2.getSystem()).setRelation(relationAgent2);
		//							((NegotiatorAgent) dual.interaction1.getSystem()).getNegotiation().setDominance(relationAgent1);
		//							((NegotiatorAgent) dual.interaction2.getSystem()).getNegotiation().setDominance(relationAgent2);
		//
		//							dual.start();
		//							try {
		//								dual.interaction1.join();
		//								dual.interaction2.join();
		//
		//
		//
		//							} catch (InterruptedException e) {
		//								// TODO Auto-generated catch block
		//								e.printStackTrace();
		//							}
		//
		//						}
		//
		//						modelA1.clearNegotiation();
		//						modelA2.clearNegotiation();
		//						//increase the submissive relation
		//						relationAgent2 = relationAgent2 +0.1;
		//					}
		//					//decrease the dominant relation
		//					relationAgent1 = relationAgent1 -0.1;
		//				}
		//			}
		//		}
	}
}
