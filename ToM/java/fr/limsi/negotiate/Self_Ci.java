package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Self_Ci <C> extends Self<C> {

	private ArrayList<Preference<C>> selfPreferences; 
	private Class<C> type;
	

	public Self_Ci(Class<C> type) {
		setSelfPreferences(new ArrayList<Preference<C>>());
		this.type = type;
		List<C> values = Arrays.asList(type.getEnumConstants());
		matrix = new MatrixOfPref<>(values);
	}

	public ArrayList<Preference<C>> getSelfPreferences() {
		return selfPreferences;
	}

	public void setSelfPreferences(ArrayList<Preference<C>> selfPreferences) {
		this.selfPreferences = selfPreferences;
	}

	public Class<C> getType() {
		return type;
	}

	public void setType(Class<C> type) {
		this.type = type;
	}

	
	public void addPreference (C less, C more){
		Preference<C> v = new Preference<C> (more, less);
		if(selfPreferences.contains(v))
			throw new RuntimeException("Cannot add P ("+v.getLess()+", " + v.getMore()+") "
					+ " because P ("+v.getMore()+", " + v.getLess()+") exists in the preferences list");
		else {
			selfPreferences.add(new Preference<C>(less, more));
			matrix.addPreference(less, more);

		}
	}
	

	public List<C> getElements (){
		return Arrays.asList(this.type.getEnumConstants());
	}
	@Override
	public String toString(){
		String s = "[";
		for(Preference<C> p : selfPreferences)
			s += p + " , ";
		s+= "]";
		return s;
	}

}
