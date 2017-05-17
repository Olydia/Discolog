package fr.limsi.negotiate;
import fr.limsi.negotiate.Statement.Satisfiable;

public class PreferenceMove extends NegoUtterance {
	 private Statement<Criterion> value;
	 // only for ask
	 
	
	 public PreferenceMove(Statement<Criterion> value, boolean external, UtType type) {
			super(external, type);
			this.value = value;
			this.valueType= value.getValue().getClass();
			} 
	 
	public PreferenceMove(Criterion value, Satisfiable sat, boolean external, UtType type) {
		super(external, type);
		this.value = new Statement<Criterion>(value, sat);
		this.valueType= value.getClass();

		}
	// Ask Criterion
	public PreferenceMove(Statement<Criterion> stat,Class<? extends Criterion> valueType, boolean external, UtType type){
		super(external, type);
		this.value = stat;
		this.valueType = valueType;

	}

	public Statement<Criterion> getValue() {
		return value;
	}

	public void setValue(Statement<Criterion> value) {
		this.value = value;
	}

}
