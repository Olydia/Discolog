package fr.limsi.discolog;

import java.util.*;
import edu.wpi.cetask.Plan;
import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;

/**
 * New main class for Discolog that extends default Disco agent to add
 * breakdown detection and recovery.
 */
public class Discolog extends Agent {

   /**
    * Use this main method instead of {@link Disco#main(String[])} to start 
    * system with console.
    */
   public static void main (String[] args) {
      Agent agent = new Discolog("agent");  
      // restrict to performing only a single primitive action on each turn
      // so we have more control over example
      agent.setMax(1);
      Interaction interaction = new Interaction(agent,
            new User("user"),
            args.length > 0 && args[0].length() > 0 ? args[0] : null);
      interaction.start(true);
   }
   
   // TODO add private fields here to hold Prolog engine, etc. 
   
   public Discolog (String name) { super(name); } 
   
   private void recover (Interaction interaction) {
      candidates.clear();
      findCandidates(interaction.getDisco().getTops());
      if ( candidates.isEmpty() )
         System.out.println("No recovery candidates!");
      else { 
         for (Plan candidate : candidates) { 
            Plan recovery = invokePlanner(candidate);
            if ( recovery != null ) {
               System.out.println("Found recovery plan for "+candidate);
               Disco disco = interaction.getDisco();
               // splice in recovery plan
               disco.getFocus().getParent().add(recovery);
               disco.push(recovery); // do it next
               return;
            }
         }
         System.out.println("No recovery plans found!");
      }
   }
  
   private Plan invokePlanner (Plan candidate) {
      // this should invoke Prolog planner
      // for now it always returns the answer to make our
      // example work, namely executing "b"
      return new Plan(candidate.getGoal().getType().getEngine().getTaskClass("b").newInstance());
   }
      
   private final List<Plan> candidates = new ArrayList<Plan>();
   
   /**
    * @return candidates for recovery planning target.
    * 
    * First cut approach: plans in given tree that are neither done nor live
    * nor blocked (which means that their precondition must be false)
    */
   private void findCandidates (List<Plan> children) {
      for (Plan plan : children) {
         if ( !(plan.isDone() || plan.isLive() || plan.isBlocked()) ) candidates.add(plan);
         findCandidates(plan.getChildren());
      }
   }
   
   @Override
   public Plugin.Item respondIf (Interaction interaction, boolean guess) {
      Plugin.Item item = super.respondIf(interaction, guess);
      // if nothing to do, then we have a breakdown to recover from
      if ( item == null ) recover(interaction); 
      return item;
   }
}
