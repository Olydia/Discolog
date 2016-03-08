package fr.limsi.negotiate;

public class Statement {

		private ValuePreference<Criterion> preference;
		private boolean external;
		
		public Statement(ValuePreference<Criterion> preference, boolean external) {
			this.preference = preference;
			this.external = external;
		}

		public Statement(Criterion more, Criterion less, boolean external){
			ValuePreference<Criterion> preference = new ValuePreference<Criterion>(more, less);
			this.preference = preference;
			this.external = external;
		}
		
		public ValuePreference<Criterion> getStatedPreference() {
			return preference;
		}
		
		public boolean isExternal() {
			return external;
		}		
		
}
