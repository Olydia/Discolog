package fr.limsi.negotiate;

/**
 * Defines a preference between two values of a given criterion (e.g. chinese cuisine is preferred to japanese cuisine)
 * for a negotiation.
 *  C  = {Criterion, Class<Criterion>}
 */

public abstract class Preference<C> {

	@Override
	public String toString() {
		return "(" + less+ "<" + more+ ")";
	}

	private final C less, more;

	public C getLess() {
		return less;
	}

	public C getMore() {
		return more;
	}


	public Preference (C class1, C class2) {
//		if(class1==class2)
//			try {
//				throw new Exception("Less value has to be different to more");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		this.less = class1;
		this.more = class2;
	}

	@Override
	public boolean equals (Object obj) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		@SuppressWarnings("unchecked")
		Preference<C> other = (Preference<C>) obj;
		if ( less == null ) {
			if ( other.less != null )
				return false;
		} else if ( !less.equals(other.less) )
			return false;
		if ( more == null ) {
			if ( other.more != null )
				return false;
		} else if ( !more.equals(other.more) )
			return false;
		return true;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((less == null) ? 0 : less.hashCode());
		result = prime * result + ((more == null) ? 0 : more.hashCode());
		return result;
	}


}
