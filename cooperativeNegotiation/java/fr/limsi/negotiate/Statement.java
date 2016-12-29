package fr.limsi.negotiate;

public class Statement <L> {
	private L value;
	private Satisfiable status;
	
	public Statement(L value){
		this.value = value;
		this.status = Satisfiable.UNKOWN;
	}
	
	public Statement(L value, Satisfiable status){
		this.value = value;
		this.status = status;
	}
	public L getValue() {
		return value;
	}

	public void setValue(L value) {
		this.value = value;
	}

	public Satisfiable getStatus() {
		return status;
	}
	
	public static enum Satisfiable { TRUE, FALSE, UNKOWN }

}
