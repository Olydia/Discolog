package fr.limsi.negotiate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Self_C  <O extends Option> extends Self<Class<? extends Criterion>>{
		public  Class <O> type; 
		
		private ArrayList<Preference<Class<? extends Criterion>>> preferences = new ArrayList<Preference<Class<? extends Criterion>>>();

		public Self_C(Class<O> type) {
			preferences = new ArrayList<Preference<Class<? extends Criterion>>>() ;
			this.type = type;
			matrix = new MatrixOfPref<>(getElements());
			// TODO Auto-generated constructor stub
		}


		public ArrayList<Preference<Class<? extends Criterion>>> getPreferences() {
			return preferences;
		}

		public void setPreferences(ArrayList<Preference<Class<? extends Criterion>>> preferences) {
			this.preferences = preferences;
		}

		@Override
		public List<Class<? extends Criterion>> getElements() {
			try {
				Method m = type.getDeclaredMethod("getCriteria");
				Object[] v = type.getEnumConstants();
				m.setAccessible(true);
				@SuppressWarnings("unchecked")
				List<Class<? extends Criterion>> value = (List<Class<? extends Criterion>>)m.invoke(v[0]);

				return (value);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		
		}

		@Override
		public void addPreference(Class<? extends Criterion> less, Class<? extends Criterion> more) {
			Preference<Class<? extends Criterion>> v = new Preference<Class<? extends Criterion>> (more, less);
			if(this.preferences.contains(v))
				throw new RuntimeException("Cannot add P ("+v.getLess()+", " + v.getMore()+") "
						+ " because P ("+v.getMore()+", " + v.getLess()+") exists in the preferences list");
			else {
				preferences.add(new Preference<Class<? extends Criterion>>(less, more));
				matrix.addPreference(less, more);

			}
			
		}
}
