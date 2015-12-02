package fr.limsi.negotiate;

import java.util.*;

import fr.limsi.negotiate.restaurant.Cost;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Class to define the mental state of the agent on preferences about a <b>criterion</b> 
 * (ie. its preferences and user preferences given by the dialogue).
 * 
 * First, you have to define an Enum that implements Criterion. For example:
 * 
 * Enum Cost implements Criterion {
 * 		EXPENSIVE, CHEAP;
 * }
 * 
 * Then, you can instantiate a CriterionNegotiation on this Criterion:
 * 
 * CriterionNegotiation<Cost> cost = new CriterionNegotiation<Cost>(Cost.class);
 * 
 * Note that the type for the generic (Cost in our example) must also be given as parameter (due to Java limitations).
 *
 * This CriterionNegotiation instance will be use as an element of the Negotiation object (@see Negotiation)
 * 
 */

public class CriterionNegotiation<C extends Criterion> {
   
   public CriterionPreferenceModel<C> self,other;       
   public Class<C> criterionType ; 
   
   public CriterionNegotiation (Class<C> type) {
	   
	   criterionType = type;
	   self = new CriterionPreferenceModel<C>();
	   other = new CriterionPreferenceModel<C>();
   }
   
   // does not work...
   @SuppressWarnings("unchecked")
   public Class<C> getGenericTypeClass() {
       try {
    	   Class<?> c1 = getClass();
    	   ParameterizedType c2 = (ParameterizedType) c1.getGenericSuperclass();
    	   Type[] c3 = c2.getActualTypeArguments();
    	   String className = c3[0].getClass().getName();
           //String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass().getName();
           System.out.println(className);
           Class<?> clazz = Class.forName(className);
           return (Class<C>) clazz;
       } catch (Exception e) {
           throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
       }
   } 
   

   public Class<C> getCriterionType() {
	return criterionType;
}


private final List<OptionProposal> proposals = new ArrayList<OptionProposal>();
   
   public void propose (OptionProposal proposal) { proposals.add(proposal); }

   public void setSelfPreferences(CriterionPreferenceModel<C> selfPref) {
	   self = selfPref;
   }
   
}
