package fr.limsi.discolog;


import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import edu.wpi.cetask.*;

/**
 * New main class for Discolog that extends default Disco agent to add
 * breakdown detection and recovery.
 */
public class DiscoShell extends Agent {

	 public DiscoShell (String name) { super(name); } 
   /**
    * Use this main method instead of {@link Disco#main(String[])} to start 
    * system with console.
    */
   public static void main (String[] args) {
      Agent agent = new Agent("agent");  
      //agent.setMax(1);
      Interaction interaction = new Interaction(agent,
            new User("user"),
            args.length > 0 && args[0].length() > 0 ? args[0] : null);
      interaction.start(true);
   }
   
}
