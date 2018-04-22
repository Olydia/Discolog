package fr.limsi.negotiate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ListModel;

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

	//----- Additional funtions for the GUI (get user preferences)
	
		public C getName(String name){
			
			//return type.getField(name.toUpperCase());
			for (C elem: type.getEnumConstants()){
				if(((Criterion) elem).afficher().toLowerCase().equals(name.toLowerCase()))
						return elem;
			}
			return null;
		}
		
		public C fromStringToCriterion(String name){
			
			//return type.getField(name.toUpperCase());
			for (C elem: type.getEnumConstants()){
				if(((Criterion) elem).toString().toLowerCase().equals(name.toLowerCase()))
						return elem;
			}
			return null;
		}
		
		/**
		 * 
		 * @param classement ordre décroissant des préférences
		 */
		public void createPreferences(ListModel<String> classement){
			
			for(int i = 0; i< classement.getSize()-1; i++){
				C more = getName(classement.getElementAt(i));
				C less = getName(classement.getElementAt(i+1));
				
				this.addPreference(less, more);
			}
		}
		/**
		 * 
		 * @param Transforme le tosTring de preferences a 
		 * une preference réelle du modele
		 * 
		 */
		public void fromStringToPreferences(String[] values){
			for(String element: values){
				String[] split = element.split("<");
				C less = fromStringToCriterion(split[0]);
				C more = fromStringToCriterion(split[1]);

				
				this.addPreference(less, more);
			}
		}
		
		
	
}