package fr.limsi.negotiate;

import fr.limsi.negotiate.restaurant.Cost;

public class Statement {

		private ValuePreference<Criterion> preference;
		private boolean external;
		private Class<? extends Criterion> type;
		public  String utteranceType;
		
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

		public Statement(ValuePreference<Criterion> preference, boolean external, String utteranceType) {
			this.preference = preference;
			this.external = external;
			this.utteranceType = utteranceType;
		}

		public Statement(Criterion less, Criterion more, boolean external, String utteranceType){
			ValuePreference<Criterion> preference = new ValuePreference<Criterion>(less, more);
			this.preference = preference;
			this.external = external;
			this.utteranceType = utteranceType;

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
