package fr.limsi.negotiate.movie;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;


public enum Movie implements Option{
	STAR_WARS (Category.ACTION, Time.H2MIN15),
	SPY (Category.COMEDY, Time.H1MIN30);

	public final Category category;
	public final Time time;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(Time.class); add(Category.class);  }};

	Movie (Category category, Time time) {
		this.category = category;
		this.time = time;
	}
	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Category.class ? category :
			c == Time.class ? time : null; // throw error? 
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


