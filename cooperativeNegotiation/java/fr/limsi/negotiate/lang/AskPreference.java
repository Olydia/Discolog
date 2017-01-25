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
	      super(AskPreference.class, disco, external, criterion, value);
	   }
	   
	   @Override
	   protected void interpret () { 
		   PreferenceMove ask;
		   if (getCriterion() == null) 
			   ask = new PreferenceMove(getValue(), Satisfiable.UNKOWN , getExternal(),UtType.ASK);

		  else 
            ask = new PreferenceMove(new Statement<Criterion>(getValue(), Satisfiable.UNKOWN),
            							getCriterion(), getExternal(),UtType.ASK);     
	      
	      getNegotiation().getContext().addUtt(ask);
	   }
}
