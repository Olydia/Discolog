package fr.limsi.negotiate;

public class PreferenceStatement extends Statement {

		private ValuePreference<Criterion> preference;
		private Class<? extends Criterion> type;
		
		public Class<? extends Criterion> getType() {
			return type;
		}

		public void setType(Class<? extends Criterion> type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "Statement [preference=" + preference + ", external="
					+ external + ", type=" + type + ", utteranceType="
					+ utteranceType + "]";
		}

		public PreferenceStatement(ValuePreference<Criterion> preference, boolean external, String utteranceType) {
			super(external,utteranceType );
			this.preference = preference;
		}

		public PreferenceStatement(Criterion less, Criterion more, boolean external, String utteranceType){
			super(external,utteranceType );
			ValuePreference<Criterion> preference = new ValuePreference<Criterion>(less, more);
			this.preference = preference;
			

		}
		
	

		public ValuePreference<Criterion> getStatedPreference() {
			return preference;
		}
		
				
		
		@Override
		public boolean equals (Object obj) {
			
			if (obj == null || this == null)
				return false;
			
			if (this == obj)
				return true;
			
			else{
				PreferenceStatement other = (PreferenceStatement) obj;
				ValuePreference<Criterion> inverse = new ValuePreference<Criterion>
						(other.getStatedPreference().getMore(), other.getStatedPreference().getLess());
				if (this.preference.equals(other.getStatedPreference()) || this.preference.equals(inverse))
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
