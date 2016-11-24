/* Copyright (c) 2009 Charles Rich and Worcester Polytechnic Institute.
 * All Rights Reserved.  Use is subject to license terms.  See the file
 * "license.terms" for information on usage and redistribution of this
 * file and for a DISCLAIMER OF ALL WARRANTIES.
 */
package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;

// this boilerplate definition is required to make this extend Utterance

public class StatePreference extends NegotiationUtterance {

   public static TaskClass CLASS;
   
   // for TaskClass.newStep
   public StatePreference (Disco disco, Decomposition decomp, String name, boolean repeat) { 
      super(StatePreference.class, disco, decomp, name, repeat);
   }
   
   public StatePreference (Disco disco, Boolean external, Criterion value, Boolean likable) { 
      super(StatePreference.class, disco, external);
      if ( value != null ) setSlotValue("value", value);
      if ( likable != null ) setSlotValue("likable", value);
   }
   
   public Criterion getValue () { return (Criterion) getSlotValue("value"); }
   public Boolean isLikable () { return (Boolean) getSlotValue("likable"); }

   @Override
   public void interpret () {
	  PreferenceStatement statement = new PreferenceStatement
			  							(getValue(),isLikable() , getExternal(),"State");
	  
	  statement.setType(getValue().getClass());
      getNegotiation().getContext().addStatement(statement);

      if (getExternal()) 
    	  getNegotiation().updateOtherMentalState(getValue(), isLikable());
      else 
    	  getNegotiation().updateOASMentalState(getValue(), isLikable());
   }
}
