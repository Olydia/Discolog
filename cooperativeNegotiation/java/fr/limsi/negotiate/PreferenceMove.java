package fr.limsi.negotiate;
import fr.limsi.negotiate.Statement.Satisfiable;

public class PreferenceMove extends NegoUtterance {
	 private Statement<Criterion> value;
	 Class<? extends Criterion> statementType; // only for ask
	 
	
	 public PreferenceMove(Statement<Criterion> value, boolean external, UtType type) {
			super(external, type);
			this.value = value;
			this.statementType= value.getValue().getClass();
			} 
	 
	public PreferenceMove(Criterion value, Satisfiable sat, boolean external, UtType type) {
		super(external, type);
		this.value = new Statement<Criterion>(value, sat);
		this.statementType= value.getClass();

		}
	// Ask Criterion
	public PreferenceMove(Statement<Criterion> stat,Class<? extends Criterion> statementType, boolean external, UtType type){
		super(external, type);
		this.value = stat;
		this.statementType = statementType;

	}

	public Class<? extends Criterion> getStatementType() {
		return statementType;
	}

	public void setStatementType(Class<? extends Criterion> statementType) {
		this.statementType = statementType;
	}

	public Statement<Criterion> getValue() {
		return value;
	}

	public void setValue(Statement<Criterion> value) {
		this.value = value;
	}

}
