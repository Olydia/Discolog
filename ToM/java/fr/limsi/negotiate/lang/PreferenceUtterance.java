/* Copyright (c) 2009 Charles Rich and Worcester Polytechnic Institute.
 * All Rights Reserved.  Use is subject to license terms.  See the file
 * "license.terms" for information on usage and redistribution of this
 * file and for a DISCLAIMER OF ALL WARRANTIES.
 */
package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.Criterion;

public abstract class PreferenceUtterance extends NegotiationUtterance {

   // for extensions
   protected PreferenceUtterance (Class<? extends PreferenceUtterance> cls, Disco disco, Decomposition decomp, 
         String step, boolean repeat) { 
      super(cls, disco, decomp, step, repeat);
   }

   // for extensions
   protected PreferenceUtterance (Class<? extends PreferenceUtterance> cls, Disco disco, Boolean external, 
          Criterion value) { 
      super(cls, disco, external);
      if ( value != null ) setSlotValue("value", value);
   }

   public Criterion getValue () { return (Criterion) getSlotValue("value"); }
   public abstract Class<? extends Criterion> getCriterion ();

}
