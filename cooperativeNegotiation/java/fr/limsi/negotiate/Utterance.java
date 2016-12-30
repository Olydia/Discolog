package fr.limsi.negotiate;

public abstract class Utterance {
	private boolean isExtrenal;
	private UtType type;
	
	public Utterance(boolean external, UtType type) {
		this.type = type;
		this.isExtrenal= external;
	}
	
	public boolean isExtrenal() {
		return isExtrenal;
	}
	public void setExtrenal(boolean isExtrenal) {
		this.isExtrenal = isExtrenal;
	}
	public UtType getType() {
		return type;
	}
	public void setType(UtType type) {
		this.type = type;
	}
	
	public static enum UtType { STATE, ASK, PROPOSE, ACCEPT, REJECT}

}
