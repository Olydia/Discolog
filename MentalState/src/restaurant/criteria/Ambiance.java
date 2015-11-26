package restaurant.criteria;

import java.util.Arrays;
import java.util.List;

import model.*;

public enum Ambiance implements Criterion {

	CALM, NOISY;

	@Override
	public  List<Ambiance> getValues() {
		// TODO Auto-generated method stub
		return Arrays.asList(Ambiance.values());
	}
	
}
