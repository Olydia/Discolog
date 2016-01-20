package fr.limsi.negotiate;
/**
 * 
 * @author Charles rich
 * 	Option proposal
 */

public class OptionProposal extends Proposal {

	@Override
	public String toString() {
		return option.toString();
	}

	public final Option option;

	public OptionProposal (boolean isSelf, Option option) {
		super(isSelf);
		this.option = option;
	}

	@Override
	Option getValue() {

		return option;
	}
}
