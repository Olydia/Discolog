package fr.limsi.preferenceModel;

import java.util.List;

public interface Option {
   
    Criterion getValue (Class<? extends Criterion> c);
    // return the values of 
    List<Class<? extends Criterion>> getCriteria();
    String getFrVersion();
    String print();

}
