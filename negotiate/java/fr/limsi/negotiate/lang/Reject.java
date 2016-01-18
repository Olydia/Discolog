package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;

public class Reject extends Utterance{
	 
	 public static TaskClass CLASS;

	// for TaskClass.newStep
	   public Reject (Disco disco, Decomposition decomp, String name, boolean repeat) { 
	      super(Reject.class, disco, decomp, name, repeat);
	   }
	   
	   public Reject (Disco disco, Boolean external) { 
	      super(Reject.class, disco, external);
	   }

}
