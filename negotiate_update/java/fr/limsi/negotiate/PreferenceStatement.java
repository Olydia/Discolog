package fr.limsi.negotiate;

public class PreferenceStatement extends Statement {

		private Criterion value;
		private Boolean isLikable;
		

		private Class<? extends Criterion> type;
		
		public Class<? extends Criterion> getType() {
			return type;
		}

		public void setType(Class<? extends Criterion> type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "Statement [preference=" + value + ", external="
					+ external + ", type=" + type + ", utteranceType="
					+ utteranceType + "]";
		}

		public PreferenceStatement(Criterion preference, Boolean isLikable, boolean external, String utteranceType) {
			super(external,utteranceType );
			this.value = preference;
			this.isLikable = isLikable;
		}
	
		public Criterion getStatedValue() {
			return value;
		}
		
			
		public Boolean getIsLikable() {
			return isLikable;
		}

		public void setIsLikable(Boolean isLikable) {
			this.isLikable = isLikable;
		}
		@Override
		public boolean equals (Object obj) {
			
			if (obj == null || this == null)
				return false;
			
			if (this == obj)
				return true;
			
			else{
				PreferenceStatement other = (PreferenceStatement) obj;
				if(this.getStatedValue().equals(other.getStatedValue()))
					return true;
			}
			return false;
				
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			result = prime * result + ((isLikable == null) ? 0 : isLikable.hashCode());
			return result;
		}
}
