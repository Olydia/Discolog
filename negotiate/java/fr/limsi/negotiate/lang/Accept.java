package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;

public class Accept extends Utterance{
	
	 public static TaskClass CLASS;

	   // for TaskClass.newStep
	   public Accept (Disco disco, Decomposition decomp, String name, boolean repeat) { 
	      super(Accept.class, disco, decomp, name, repeat);
	   }
	   
	   public Accept (Disco disco, Boolean external) { 
	      super(Accept.class, disco, external);
	   }
}
