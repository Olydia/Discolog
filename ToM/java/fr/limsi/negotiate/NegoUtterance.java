
/** this class is used to captue the utterances exchanged between the agents during the negotiation
 * Used to keep the history of the negotiation
 * 
 */

package fr.limsi.negotiate;

public abstract class NegoUtterance {
	private boolean isExtrenal;
	private UtType type;
	protected Class<? extends Criterion> valueType; 
	
	public void setValueType(Class<? extends Criterion> valueType) {
		this.valueType = valueType;
	}

	public NegoUtterance(boolean external, UtType type) {
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
	
	public Class<? extends Criterion> getValueType(){
		return valueType;
	}
	
	public abstract Object getValue();
	
	public static enum UtType { STATE, ASK, PROPOSE, ACCEPT, REJECT, REJECTPROPOSE,ACCEPTPROPOSE, REJECTSTATE}

}
