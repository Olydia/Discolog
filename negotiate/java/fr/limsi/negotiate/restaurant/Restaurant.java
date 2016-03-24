package fr.limsi.negotiate.restaurant;
import java.util.ArrayList;
import java.util.List;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;

public enum Restaurant implements Option {

	LE_PARISIEN (Cuisine.FRENCH, Cost.EXPENSIVE, Ambiance.CALM),

	CHEZ_CHUCK (Cuisine.CHINESE, Cost.CHEAP, Ambiance.NOISY),
	
	SAMURA (Cuisine.JAPANESE, Cost.CHEAP, Ambiance.NOISY),
	
	PAPELLI (Cuisine.ITALIAN, Cost.EXPENSIVE, Ambiance.CALM);

	public final Cuisine cuisine;
	public final Cost cost;
	public final Ambiance ambiance;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = new ArrayList<Class<? extends Criterion>> () {{ add(Cost.class); add(Cuisine.class); add(Ambiance.class); }};
	
	Restaurant (Cuisine cuisine, Cost cost, Ambiance ambiance) {
		this.cuisine = cuisine;
		this.cost = cost;
		this.ambiance = ambiance;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Cuisine.class ? cuisine :
			c == Cost.class ? cost : c == Ambiance.class? ambiance : null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}


}
