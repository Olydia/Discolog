package fr.limsi.negotiate;


/**
 * Defines a preference between two criteria (e.g. cuisine is more important than cost)
 * for a negotiation.
 * @param <C>
 */
public class ValuePreference<C extends Criterion>  extends Preference<C>{
   
	public ValuePreference(C more, C less) {
		super(more, less);
		// TODO Auto-generated constructor stub
	}
}
