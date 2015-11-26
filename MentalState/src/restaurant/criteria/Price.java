package restaurant.criteria;

import java.util.Arrays;
import java.util.List;

import model.*;

public enum Price implements Criterion {

	CHER, PASCHER;

	// @Override
	public List<Price> getValues() {
		return Arrays.asList(Price.values());
	}
	
	
	
}
