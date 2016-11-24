package fr.limsi.negotiate;

public class AskStatement extends PreferenceStatement{

	Criterion value2;
	public AskStatement(Criterion value1, Criterion value2, Boolean isLikable, 
			boolean external, String utteranceType) {
		super(value1, isLikable, external, utteranceType);
		this.value2 = value2;
	}

	public Criterion getOtherStatedValue() {
		return value2;
	}
}
