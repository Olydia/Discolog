package fr.limsi.negotiate.movie;



import java.util.ArrayList;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.Restaurant;


public class GenerateMovieModel {
	

	public   Negotiation<Movie>  model1(){

		Self_C<Movie>  d1_criteria = new Self_C<Movie> (Movie.class);
		d1_criteria.addPreference(Country.class, Year.class);
		d1_criteria.addPreference(Year.class, Category.class);

		Self_Ci<Category> d1_category = new Self_Ci <Category>(Category.class);
		d1_category.addPreference(Category.HORROR, Category.THRILLER);
		d1_category.addPreference(Category.HORROR, Category.DRAMA);
		d1_category.addPreference(Category.DRAMA, Category.THRILLER);
		d1_category.addPreference(Category.THRILLER, Category.ADVENTURE);
		d1_category.addPreference(Category.ADVENTURE, Category.COMEDY);
		d1_category.addPreference(Category.COMEDY, Category.ANIMATION);
		CriterionNegotiation<Category> category = new CriterionNegotiation<>(d1_category);

		Self_Ci<Country> d1_country = new Self_Ci<Country>(Country.class);
		d1_country.addPreference(Country.SPAIN, Country.CANADA);
		d1_country.addPreference(Country.CANADA, Country.FRANCE);
		d1_country.addPreference(Country.FRANCE, Country.BRITAIN);
		d1_country.addPreference(Country.BRITAIN, Country.US);

		CriterionNegotiation<Country> country = new CriterionNegotiation<>(d1_country);


		Self_Ci<Year> d1_year = new Self_Ci<Year>(Year.class);
		d1_year.addPreference(Year.THE_SEXTIES, Year.THE_SEVENTIES);
		d1_year.addPreference(Year.THE_SEVENTIES, Year.THE_EIGHTIES);
		d1_year.addPreference(Year.THE_EIGHTIES, Year.THE_NINETIES);
		d1_year.addPreference(Year.THE_NINETIES, Year.TWENTY);

		CriterionNegotiation<Year> year = new CriterionNegotiation<>(d1_year);

		@SuppressWarnings("unchecked")
		Negotiation<Movie> model1 = new Negotiation<Movie> 
		(new CriterionNegotiation[] {year, category, country}, d1_criteria, Movie.class);
		return model1;

	}
	
	public   Negotiation<Movie>  model2(){

		Self_C<Movie>  d1_criteria = new Self_C<Movie> (Movie.class);
		d1_criteria.addPreference(Category.class, Country.class);
		d1_criteria.addPreference(Country.class, Year.class);

		Self_Ci<Category> d1_category = new Self_Ci <Category>(Category.class);
		d1_category.addPreference(Category.ANIMATION, Category.COMEDY);
		d1_category.addPreference(Category.COMEDY, Category.ADVENTURE);
		d1_category.addPreference(Category.ADVENTURE, Category.HORROR);
		d1_category.addPreference(Category.HORROR, Category.DRAMA);
		d1_category.addPreference(Category.HORROR, Category.THRILLER);
		d1_category.addPreference(Category.THRILLER, Category.DRAMA);

		CriterionNegotiation<Category> category = new CriterionNegotiation<>(d1_category);

		Self_Ci<Country> d1_country = new Self_Ci<Country>(Country.class);
		d1_country.addPreference(Country.US, Country.BRITAIN);
		d1_country.addPreference(Country.BRITAIN, Country.SPAIN);
		d1_country.addPreference(Country.US, Country.SPAIN);
		d1_country.addPreference(Country.SPAIN, Country.CANADA);
		d1_country.addPreference(Country.CANADA, Country.FRANCE);

		CriterionNegotiation<Country> country = new CriterionNegotiation<>(d1_country);


		Self_Ci<Year> d1_year = new Self_Ci<Year>(Year.class);
		d1_year.addPreference(Year.TWENTY, Year.THE_NINETIES);
		d1_year.addPreference(Year.THE_NINETIES, Year.THE_EIGHTIES);
		d1_year.addPreference(Year.THE_EIGHTIES, Year.THE_SEVENTIES);
		d1_year.addPreference(Year.THE_SEVENTIES, Year.THE_SEXTIES);

		CriterionNegotiation<Year> year = new CriterionNegotiation<>(d1_year);

		@SuppressWarnings("unchecked")
		Negotiation<Movie> model1 = new Negotiation<Movie> 
		(new CriterionNegotiation[] {year, category, country}, d1_criteria, Movie.class);
		return model1;

	}
	public ArrayList<Negotiation<Movie>> getModels(){
		ArrayList<Negotiation<Movie>> models = new ArrayList<Negotiation<Movie>>();
		models.add(this.model1());
		models.add(this.model2());

		
		return models;
	}
}
