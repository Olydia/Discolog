package fr.limsi.negotiate.movie;

import java.util.Arrays;
import java.util.List;

import fr.limsi.negotiate.*;

public class InitiateMovieMentalState {

	@SuppressWarnings("unchecked")
	public  Negotiation<Movie> P1 () {
		Negotiation<Movie> Movie;
		// 1. Define d1 preference model on each criterion of Movie
				// 1.1. Preference model on Category
				CriterionPrefModel<Category> d1_category = new CriterionPrefModel<Category>();
				d1_category.setType(Category.class);
				d1_category.add(new ValuePreference<Category>(Category.HORROR, Category.COMEDY));
				d1_category.add(new ValuePreference<Category>(Category.COMEDY, Category.ANIMATION));


				// 1.2. Preference model on Year
				CriterionPrefModel<Year> d1_Year = new CriterionPrefModel<Year>();
				d1_Year.setType(Year.class);
				d1_Year.add(new ValuePreference<Year>(Year.THE_EIGHTIES, Year.THE_NINETIES));
				d1_Year.add(new ValuePreference<Year>(Year.THE_NINETIES, Year.RECENT));


				// 1.3 Preference model on Country 
				CriterionPrefModel<Country> d1_Country = new CriterionPrefModel<Country>();
				d1_Country.setType(Country.class);
				d1_Country.add(new ValuePreference<Country>(Country.FRANCE, Country.US));
				d1_Country.add(new ValuePreference<Country>(Country.FRANCE, Country.CANADA));

				
				//1.4. Define the  preferences on Movie criteria 
				CriteriaClassPrefModel<Movie> d1_criteria = new CriteriaClassPrefModel<Movie>(); 
				d1_criteria.setType(Movie.class); // Its is not the idial solution but I have to get the type of an option 
				d1_criteria.add(new CriterionPreference(Year.class, Category.class));
				d1_criteria.add(new CriterionPreference(Country.class, Year.class));

				///2. Define the agent mental state on each criterion (self pref, user pref, proposals) 		
				CriterionNegotiation<Year> Year = new CriterionNegotiation<Year>(Year.class);
				Year.setSelfPreferences(d1_Year);

				CriterionNegotiation<Category> Category = new CriterionNegotiation<Category>(Category.class);
				Category.setSelfPreferences(d1_category);

				CriterionNegotiation<Country> Country = new CriterionNegotiation<Country>(Country.class);
				Country.setSelfPreferences(d1_Country);

				//
				//		/*3. Create a nogotiation on Movie */
				Movie= new Negotiation<Movie>
				(new CriterionNegotiation[] {Year, Category, Country}, d1_criteria);
				return (Movie);
	}
	
	@SuppressWarnings("unchecked")
	public  Negotiation<Movie> P2 () {
		Negotiation<Movie> Movie;
		// 1. Define d1 preference model on each criterion of Movie
				// 1.1. Preference model on Category
				CriterionPrefModel<Category> p2_category = new CriterionPrefModel<Category>();
				p2_category.setType(Category.class);
				p2_category.add(new ValuePreference<Category>(Category.ANIMATION, Category.HORROR));
				p2_category.add(new ValuePreference<Category>(Category.COMEDY, Category.HORROR));


				// 1.2. Preference model on Year
				CriterionPrefModel<Year> p2_Year = new CriterionPrefModel<Year>();
				p2_Year.setType(Year.class);
				p2_Year.add(new ValuePreference<Year>(Year.RECENT, Year.THE_EIGHTIES));
				p2_Year.add(new ValuePreference<Year>(Year.THE_NINETIES, Year.RECENT));


				// 1.3 Preference model on Country 
				CriterionPrefModel<Country> p2_Country = new CriterionPrefModel<Country>();
				p2_Country.setType(Country.class);
				p2_Country.add(new ValuePreference<Country>(Country.US, Country.FRANCE));
				p2_Country.add(new ValuePreference<Country>(Country.US, Country.CANADA));

				
				//1.4. Define the  preferences on Movie criteria 
				CriteriaClassPrefModel<Movie> p2_criteria = new CriteriaClassPrefModel<Movie>(); 
				p2_criteria.setType(Movie.class); // Its is not the idial solution but I have to get the type of an option 
				p2_criteria.add(new CriterionPreference(Country.class, Category.class));
				p2_criteria.add(new CriterionPreference(Country.class, Year.class));

				///2. Define the agent mental state on each criterion (self pref, user pref, proposals) 		
				CriterionNegotiation<Year> Year = new CriterionNegotiation<Year>(Year.class);
				Year.setSelfPreferences(p2_Year);

				CriterionNegotiation<Category> Category = new CriterionNegotiation<Category>(Category.class);
				Category.setSelfPreferences(p2_category);

				CriterionNegotiation<Country> Country = new CriterionNegotiation<Country>(Country.class);
				Country.setSelfPreferences(p2_Country);

				//
				//		/*3. Create a nogotiation on Movie */
				Movie= new Negotiation<Movie>
				(new CriterionNegotiation[] {Year, Category, Country}, p2_criteria);
				return (Movie);
	}
	
	
	public InitiateMovieMentalState() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void printOptions(Negotiation<Movie> n, int relation){
		List<Option> options =	(Arrays.asList(n.getOptions()));
		for(Option o: options)
			System.out.println(n.isAcceptableOption(o, relation));
	}


}
