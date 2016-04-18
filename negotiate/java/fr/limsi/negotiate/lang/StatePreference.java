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
   
   public StatePreference (Disco disco, Boolean external, Criterion less, Criterion more) { 
      super(StatePreference.class, disco, external);
      if ( less != null ) setSlotValue("less", less);
      if ( more != null ) setSlotValue("more", less);
   }
   
   public Criterion getLess () { return (Criterion) getSlotValue("less"); }
   public Criterion getMore () { return (Criterion) getSlotValue("more"); }

   @Override
   public void interpret () {
      getNegotiation().context.updateDiscussedCriterion(getLess(), getMore());
      if ( getExternal() ) getNegotiation().updateOtherMentalState(getLess(), getMore());
      else getNegotiation().updateOASMentalState(getLess(), getMore());
   }
}
