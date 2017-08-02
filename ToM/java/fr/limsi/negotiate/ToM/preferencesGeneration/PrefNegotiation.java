package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.Self_C;
import fr.limsi.negotiate.Self_Ci;

public class PrefNegotiation <A extends Option>{

		private List<Self_Ci<Criterion>> values_prefs;
		private Self_C<A> criteria_prefs;

		 public List<Self_Ci<Criterion>> getValues_prefs() {
			return values_prefs;
		}

		public void setValues_prefs(List<Self_Ci<Criterion>> values_prefs) {
			this.values_prefs = values_prefs;
		}

		public Self_C<A> getCriteria_prefs() {
			return criteria_prefs;
		}

		public void setCriteria_prefs(Self_C<A>criteria_prefs) {
			this.criteria_prefs = criteria_prefs;
		}

		public PrefNegotiation(List<Self_Ci<Criterion>> values_prefs) {
			this.values_prefs = values_prefs;
			//this.criteria_prefs = criteria_prefs;
		}
		
		public PrefNegotiation(List<Self_Ci<Criterion>> values_prefs, Self_C<A> criteria_prefs) {
			this.values_prefs = values_prefs;
			this.criteria_prefs = criteria_prefs;
		}
		
		

	}

