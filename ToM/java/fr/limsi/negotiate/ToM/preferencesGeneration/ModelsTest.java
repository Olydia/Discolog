package fr.limsi.negotiate.ToM.preferencesGeneration;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.toyExample.ToyRestaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by PC on 11/11/2017.
 */
public class ModelsTest {
    public void getCombinationTest() throws Exception {
        Models<ToyRestaurant> m = new Models<ToyRestaurant>();
        List<List<Self_Ci<Criterion>>> preferences = new ArrayList<List<Self_Ci<Criterion>>>();
        for(Class<? extends Criterion> type: ToyRestaurant.A_CITADELLA.getCriteria()){
            preferences.add(m.createValuesModel(Arrays.asList(type.getEnumConstants())));
        }
        m.getCombinationAmine(preferences);
        System.out.println(m.getFinalList());
    }

}