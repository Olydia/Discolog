package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.PreferenceStatement.Acceptable;

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
		   PreferenceStatement statement = new PreferenceStatement(getValue(),Acceptable.UNKNOWN , getExternal(),"Ask");
		   if (getCriterion() == null) 
	    	  statement.setType(getValue().getClass());
		  else 
            statement.setType(getCriterion());    
	      
	      getNegotiation().getContext().addStatement(statement);
	   }
}
