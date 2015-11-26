package fr.limsi.negotiate;

public interface Option {
   
    Criterion getValue (Class<? extends Criterion> c);

}
