package fr.limsi.negotiate;
/**
 * 
 * @author Charles rich
 * 	Option proposal
 */

public class OptionProposal extends Proposal {

	@Override
	public String toString() {
		return option.toString().toLowerCase();
	}

	public final Option option;

	public OptionProposal (boolean isSelf, Option option) {
		super(isSelf);
		this.option = option;
	}
	
	public OptionProposal( Option option) {
		super();
		this.option = option;
	}

	@Override
	public Option getValue() {
		return option;
	}
	
	@Override
	public boolean equals(Object obj){
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		OptionProposal other = (OptionProposal) obj;
		if ( option == null ) {
			if ( other.getValue() != null )
				return false;
		} else if ( !option.equals(other.getValue()) )
			return false;

		return true;
	}
	
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		return result;
	}

}

