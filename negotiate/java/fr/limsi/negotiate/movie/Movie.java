package fr.limsi.negotiate.movie;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;



public enum Movie implements Option{
	A (Category.ANIMATION, Year.RECENT, Country.CANADA),
	B (Category.ANIMATION, Year.RECENT, Country.FRANCE),
	C (Category.ANIMATION, Year.RECENT, Country.US),
	D (Category.ANIMATION, Year.THE_EIGHTIES, Country.CANADA),
	E (Category.ANIMATION, Year.THE_EIGHTIES, Country.FRANCE),
	F (Category.ANIMATION, Year.THE_EIGHTIES, Country.US),
	G (Category.ANIMATION, Year.THE_NINETIES, Country.CANADA),
	H (Category.ANIMATION, Year.THE_NINETIES, Country.FRANCE),
	I (Category.ANIMATION, Year.THE_NINETIES, Country.US),
	K (Category.COMEDY, Year.RECENT, Country.CANADA),
	L (Category.COMEDY, Year.RECENT, Country.FRANCE),
	M (Category.COMEDY, Year.RECENT, Country.US),
	N (Category.COMEDY, Year.THE_EIGHTIES, Country.CANADA),
	O (Category.COMEDY, Year.THE_EIGHTIES, Country.FRANCE),
	P (Category.COMEDY, Year.THE_EIGHTIES, Country.US),
	Q (Category.COMEDY, Year.THE_NINETIES, Country.CANADA),
	R (Category.COMEDY, Year.THE_NINETIES, Country.FRANCE),
	S (Category.COMEDY, Year.THE_NINETIES, Country.US),
	K1 (Category.HORROR, Year.RECENT, Country.CANADA),
	L1 (Category.HORROR, Year.RECENT, Country.FRANCE),
	M1 (Category.HORROR, Year.RECENT, Country.US),
	N1 (Category.HORROR, Year.THE_EIGHTIES, Country.CANADA),
	O1 (Category.HORROR, Year.THE_EIGHTIES, Country.FRANCE),
	P1 (Category.HORROR, Year.THE_EIGHTIES, Country.US),
	Q1 (Category.HORROR, Year.THE_NINETIES, Country.CANADA),
	R1 (Category.HORROR, Year.THE_NINETIES, Country.FRANCE),
	S1 (Category.HORROR, Year.THE_NINETIES, Country.US);
	
	public final Category category;
	public final Year year;
	public final Country country;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = 
			new ArrayList<Class<? extends Criterion>> () {{ 
						add(Category.class); add(Year.class); add(Country.class); }};
	
	Movie (Category category, Year year, Country country) {
		this.category = category;
		this.year = year;
		this.country = country;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Category.class ? category :
			c == Year.class ? year : c == Country.class? country : null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	
}


