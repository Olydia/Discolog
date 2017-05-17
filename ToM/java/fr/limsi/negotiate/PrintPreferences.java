package fr.limsi.negotiate;


import fr.limsi.negotiate.restaurant.Restaurant;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class PrintPreferences<O extends Option> {
	
	Negotiation <O> model; 
	
	public PrintPreferences(Negotiation <O> model) {

		this.model = model;
	}

	public void printSatisfiability(){
		for (CriterionNegotiation<Criterion> cr: model.getValuesNegotiation()){
			// add the new commit
			System.out.println("\n" + cr.getType().getSimpleName()+"\n");
			Self_Ci<Criterion> self = cr.getSelf();
			for(Criterion c: self.getElements()){
				System.out.println(c + ", "+ self.satisfaction(c));
			}
		}
//		System.out.println("");
//		for (O o: model.getOptions()){
//			System.out.println(o + " , " + model.satisfiability(o));
//		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		totalOrderedModels m = new totalOrderedModels();
		//GenerateMovieModel m = new GenerateMovieModel();
		PrintPreferences<Restaurant> p = new PrintPreferences<Restaurant>(m.model3());
		PrintPreferences<Restaurant> p1 = new PrintPreferences<Restaurant>(m.model4());

		p.printSatisfiability();
//		System.out.println("============================");
//		p1.printSatisfiability();
//		
	}

}
