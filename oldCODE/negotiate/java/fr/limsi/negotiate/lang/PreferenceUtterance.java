/* Copyright (c) 2009 Charles Rich and Worcester Polytechnic Institute.
 * All Rights Reserved.  Use is subject to license terms.  See the file
 * "license.terms" for information on usage and redistribution of this
 * file and for a DISCLAIMER OF ALL WARRANTIES.
 */
package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;

abstract class PreferenceUtterance extends NegotiationUtterance {

   // for extensions
   protected PreferenceUtterance (Class<? extends PreferenceUtterance> cls, Disco disco, Decomposition decomp, 
         String step, boolean repeat) { 
      super(cls, disco, decomp, step, repeat);
   }

   // for extensions
   protected PreferenceUtterance (Class<? extends PreferenceUtterance> cls, Disco disco, Boolean external, 
         Class<? extends Criterion> criterion, Criterion less, Criterion more) { 
      super(cls, disco, external);
      if ( criterion != null ) setSlotValue("criterion", criterion);
      if ( less != null ) setSlotValue("less", less);
      if ( more != null ) setSlotValue("more", less);
   }

   @SuppressWarnings("unchecked")
   public Class<? extends Criterion> getCriterion () { return (Class<? extends Criterion>) getSlotValue("criterion"); }
   public Criterion getLess () { return (Criterion) getSlotValue("less"); }
   public Criterion getMore () { return (Criterion) getSlotValue("more"); }

}
