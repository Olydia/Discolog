package fr.limsi.negotiate.restaurant;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;

public enum Restaurant implements Option {

	LE_PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE),

	CHEZ_CHUCK (Cuisine.CHINESE, Cost.CHEAP);

	public final Cuisine cuisine;
	public final Cost cost;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(Cost.class); add(Cuisine.class); }};
	
	Restaurant (Cuisine cuisine, Cost cost) {
		this.cuisine = cuisine;
		this.cost = cost;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Cuisine.class ? cuisine :
			c == Cost.class ? cost : null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}


}
