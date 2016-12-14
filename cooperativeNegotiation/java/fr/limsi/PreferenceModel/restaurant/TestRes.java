package fr.limsi.PreferenceModel.restaurant;

import fr.limsi.preferenceModel.*;

public class TestRes {

	public static CriteriaPreferences<Cuisine>  initiate(){
		CriteriaPreferences<Cuisine> d1_cuisine = new CriteriaPreferences <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.FRENCH);
	
	return d1_cuisine;

	}
	public static void main (String[] args)  {
		CriteriaPreferences<Cuisine> d1_cuisine = initiate();
		System.out.println(d1_cuisine.Satisfaction(Cuisine.CHINESE));
		System.out.println(d1_cuisine.Satisfaction(Cuisine.KOREAN));
		System.out.println(d1_cuisine.Satisfaction(Cuisine.MIXICAN));

	}

}
