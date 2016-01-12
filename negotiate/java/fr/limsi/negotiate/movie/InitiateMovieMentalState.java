package fr.limsi.negotiate.movie;

import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionPrefModel;
import fr.limsi.negotiate.CriterionPreference;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.CriteriaClassPrefModel;
import fr.limsi.negotiate.ValuePreference;


public class InitiateMovieMentalState {
	private  static Negotiation<Movie> movieNegotiation;

	@SuppressWarnings("unchecked")
	public static  Negotiation<Movie> Initialise () {
		// 1. Define lydia preference model on each criterion of restaurant
				// 1.1. Preference model on cuisine
				CriterionPrefModel<Category> category = new CriterionPrefModel<Category>();
				category.setType(Category.class);
				category.add(new ValuePreference<Category>(Category.ACTION, Category.HORROR));
				category.add(new ValuePreference<Category>(Category.ANIMATION, Category.COMEDY));
				category.add(new ValuePreference<Category>(Category.HORROR,Category.ANIMATION));
				

				// 2.2. Preference model on Cost
				CriterionPrefModel<Time> time = new CriterionPrefModel<Time>();
				time.setType(Time.class);
				time.add(new ValuePreference<Time>(Time.H1MIN30, Time.H2MIN15));

				/*2. Define the  preferences on Restaurant criteria */	

				CriteriaClassPrefModel<Movie> movie = new CriteriaClassPrefModel<Movie>(); 
				movie.setType(Movie.class); // Its is not the idial solution but I have to get the type of an option 
				movie.add(new CriterionPreference(Category.class,Time.class));

				/*3. Define the agent mental state on each criterion (self pref, user pref, proposals */		
				CriterionNegotiation<Time> timeNegotiation = new CriterionNegotiation<Time>(Time.class);
				timeNegotiation.setSelfPreferences(time);

				CriterionNegotiation<Category> categoryNegotiation = new CriterionNegotiation<Category>(Category.class);
				categoryNegotiation.setSelfPreferences(category);

				
				//
				//		/*3. Create a nogotiation on restaurant */
				movieNegotiation = new Negotiation<Movie>
				(new CriterionNegotiation[] {timeNegotiation, categoryNegotiation}, movie);
				return (movieNegotiation);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
