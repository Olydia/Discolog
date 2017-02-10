package fr.limsi.negotiate;

public class Statement {
	protected boolean external;
	protected  String utteranceType;
	
	public Statement(boolean external, String utteranceType) {
		this.external = external;
		this.utteranceType = utteranceType;
	}

	public boolean isExternal() {
		return external;
	}

	public String getUtteranceType() {
		return utteranceType;
	}

	public void setUtteranceType(String utteranceType) {
		this.utteranceType = utteranceType;
	}
}
