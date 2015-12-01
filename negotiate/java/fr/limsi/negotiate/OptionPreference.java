package fr.limsi.negotiate;
/**
 * Defines a preference between two criteria (e.g. cuisine is more important than cost)
 * for a negotiation.
 */
public class OptionPreference {
   
	private final Class<? extends Criterion> less, more;
	   
	public Class<? extends Criterion> getLess() {
		return less;
	}

	public Class<? extends Criterion> getMore() {
		return more;
	}

   public OptionPreference (Class<? extends Criterion> less, Class<? extends Criterion> more) {
      this.less = less;
      this.more = more;
   }
}
