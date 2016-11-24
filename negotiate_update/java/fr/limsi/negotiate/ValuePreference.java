package fr.limsi.negotiate;


/**
 * Defines a preference between two criteria (e.g. cuisine is more important than cost)
 * for a negotiation.
 * @param <C>
 */
public class ValuePreference<C extends Criterion>  extends Preference<C>{
   
	public ValuePreference(C less, C more) {
		super(less, more);
		// TODO Auto-generated constructor stub
	}
	
	public Class<? extends Criterion> getType(){
		
		if (getMore() == null){
			if (getLess() == null) 
				return null ;
			else 
				return getLess().getClass();
		}	
		else 
			return getMore().getClass();
	}
}
