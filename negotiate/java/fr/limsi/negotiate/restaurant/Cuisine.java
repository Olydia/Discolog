package fr.limsi.negotiate.restaurant;
import fr.limsi.negotiate.Criterion;

public enum Cuisine implements Criterion {

   FRENCH, CHINESE, ITALIAN, TURKISH, JAPANESE;

   
@Override
public Criterion[] getValues() {
	// TODO Auto-generated method stub
	return (Cuisine.values());
}
   
}
