package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.NegoUtterance.UtType;
import fr.limsi.negotiate.Statement.Satisfiable;

public class AskPreference extends PreferenceUtterance {

	 public static TaskClass CLASS;
	   
	   // for TaskClass.newStep
	   public AskPreference (Disco disco, Decomposition decomp, String name, boolean repeat) { 
	      super(AskPreference.class, disco, decomp, name, repeat);
	   }
	   
	   public AskPreference (Disco disco, Boolean external, Class<? extends Criterion> criterion,
	         Criterion value) { 
	      super(AskPreference.class, disco, external, value);
	      if ( criterion != null ) setSlotValue("criterion", criterion);

	   }
	   
	   @SuppressWarnings("unchecked")
	public Class<? extends Criterion> getCriterion () { 
		   Class<? extends Criterion> type = (Class<? extends Criterion>) getSlotValue("criterion");
		   if(type == null)
			   return ((Criterion) getSlotValue("value")).getClass();
		   return type;
		   }

	   
	   @Override
	   protected void interpret () { 
	  	// -----------------
			getNegotiation().getContext().addUtt(this);
			// -----------------
	   }
}
