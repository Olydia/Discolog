package fr.limsi.negotiate;

import java.util.List;

public interface Option {
   
    Criterion getValue (Class<? extends Criterion> c);
    // return the values of 
    List<Class<? extends Criterion>> getCriteria();
    String getFrVersion();

}
