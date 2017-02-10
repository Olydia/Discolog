package fr.limsi.negotiate;

import java.util.List;

public interface Option {
   
    Criterion getValue (Class<? extends Criterion> c);
    List<Class<? extends Criterion>> getCriteria();
    String getFrVersion();
    String print();

}
