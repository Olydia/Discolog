package restaurant.criteria;

import java.util.Arrays;
import java.util.List;

import model.*;

public enum Location implements Criterion {

	DOUXIEME, MONTPARNASSE, VERSAILLES;

	@Override
	public List<Location> getValues() {
		// TODO Auto-generated method stub
		return Arrays.asList(Location.values());
	}
	
}
