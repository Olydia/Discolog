package fr.limsi.negotiate;

import fr.limsi.negotiate.movie.Category;
import fr.limsi.negotiate.movie.Country;
import fr.limsi.negotiate.movie.GenerateMovieModel;
import fr.limsi.negotiate.movie.Movie;
import fr.limsi.negotiate.movie.Year;

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
			for(Criterion c: self.sortValues()){
				System.out.println(c + ", "+ self.satisfaction(c));
			}
		}
		System.out.println("");
		for (O o: model.getOptions()){
			System.out.println(o + " , " + model.satisfiability(o));
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//totalOrderedModels m = new totalOrderedModels();
		GenerateMovieModel m = new GenerateMovieModel();
		//PrintPreferences<Movie> p = new PrintPreferences<Movie>(m.model2());
		//p.printSatisfiability();
		int i = 0;
		for(Category c: Category.values()){
			for(Year y: Year.values()){
				for(Country cr: Country.values()){
					i++;
					System.out.println(	"MOVIE"+i +"(Category."+ c.name()+", Year."+y.name()+", Country."+cr.name()+"),");
				}
			}
		}
		
	}

}
