package fr.limsi.negotiate.restaurant;
import java.util.Arrays;
import java.util.List;

import fr.limsi.negotiate.*;

public enum Cuisine implements Criterion {

   FRENCH, CHINESE, ITALIAN, TURKISH, JAPANESE;

   
@Override
public List getValues() {
	// TODO Auto-generated method stub
	return (Arrays.asList(Cuisine.values()));
}
   
}
