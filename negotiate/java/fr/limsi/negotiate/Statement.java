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
		
		@Override
		public boolean equals (Object obj) {
			
			if (obj == null || this == null)
				return false;
			
			if (this == obj)
				return true;
			
			else{
				Statement other = (Statement) obj;
				if (this.preference.equals(other.getStatedPreference()))
					return true;
			}
			return false;
				
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((preference.getLess() == null) ? 0 : preference.getLess().hashCode());
			result = prime * result + ((preference.getMore() == null) ? 0 : preference.getMore().hashCode());
			return result;
		}
}
