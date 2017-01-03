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
	
	
	@Override
	public boolean equals(Object obj){
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		@SuppressWarnings("unchecked")
		Statement<L> other = (Statement<L>) obj;
		if ( value == null ) {
			if ( other.getValue() != null )
				return false;
		} else if ( !value.equals(other.getValue()) )
			return false;

		return true;
	}
	
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

}
