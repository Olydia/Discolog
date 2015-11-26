package restaurant.criteria;

import java.util.Arrays;
import java.util.List;

import model.*;

public enum Cuisine implements Criterion {

	CHINOIS,
	JAPONAIS, 
	BRETON, 
	ITALIEN, 
	AVEYRONNAIS;
	
	public final static List<Cuisine> values = Arrays.asList(Cuisine.values());
	
//	private Class values;
//
//
//	private Cuisine(Class values) {
//		this.values = values;
//	}


	public List<Cuisine> getValues() {
		return Arrays.asList(Cuisine.values());
	}
}

