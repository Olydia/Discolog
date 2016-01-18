package fr.limsi.negotiate.lang;

import edu.wpi.cetask.Decomposition;
import edu.wpi.cetask.TaskClass;
import edu.wpi.disco.Disco;
import edu.wpi.disco.lang.Utterance;

public class Propose extends Utterance{


	 public static TaskClass CLASS;
	   
	   // for TaskClass.newStep
	   public Propose (Disco disco, Decomposition decomp, String name, boolean repeat) { 
	      super(Propose.class, disco, decomp, name, repeat);
	   }
	   
	   public Propose (Disco disco, Boolean external) { 
	      super(Propose.class, disco, external);
	   }
}
