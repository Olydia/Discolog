package fr.limsi.negotiate.restaurant;


import fr.limsi.negotiate.*;

public enum Cost implements Criterion {
   
   EXPENSIVE, CHEAP;

public Criterion[] getValues() {
	// TODO Auto-generated method stub
	return (Cost.values());
}

}
