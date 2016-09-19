package fr.limsi.negotiate.lang;

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;

public class AskPreference extends PreferenceUtterance {

	 public static TaskClass CLASS;
	   
	   // for TaskClass.newStep
	   public AskPreference (Disco disco, Decomposition decomp, String name, boolean repeat) { 
	      super(AskPreference.class, disco, decomp, name, repeat);
	   }
	   
	   public AskPreference (Disco disco, Boolean external, Class<? extends Criterion> criterion,
	         Criterion less, Criterion more) { 
	      super(AskPreference.class, disco, external, criterion, less, more);
	   }
	   
	   @Override
	   protected void interpret () {
	      Statement statement = getNegotiation().getContext().createStatement(getLess(), getMore(), getExternal(),"Ask");
	      if (getCriterion() == null) 
            getNegotiation().getContext().updateDiscussedCriterion(getLess(), getMore());
	      else {
            getNegotiation().getContext().updateDiscussedCriterion(getCriterion());
            statement.setType(getCriterion());    
	      }
	      getNegotiation().getContext().getListStatements().add(statement);
	   }
}
