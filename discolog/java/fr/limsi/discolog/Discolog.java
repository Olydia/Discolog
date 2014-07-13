package fr.limsi.discolog;

import java.util.*;
import alice.tuprolog.Struct;
import edu.wpi.cetask.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.*;
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
      TaskEngine.DEBUG = true;
      Interaction interaction = new Interaction(agent,
            new User("user"),
            args.length > 0 && args[0].length() > 0 ? args[0] : null);
      interaction.start(true);
     
   }
   
   // TODO add private fields here to hold Prolog engine, etc. 
   
   public Discolog (String name) { super(name); } 
   
   /**
    * Return true iff recovery found
    */
   private boolean recover (Interaction interaction) {
      candidates.clear();
      findCandidates(interaction.getDisco().getTops());
      if ( candidates.isEmpty() ) {
         System.out.println("No recovery candidates!");
         return false;
      }
      // TODO: If more than one way to recover, how to choose?
      for (Plan candidate : candidates) { 
         Plan recovery = invokePlanner(candidate);
         if ( recovery != null ) {
            System.out.println("Found recovery plan for "+candidate);
            // splice in recovery plan
            interaction.getFocus().add(recovery);
            recovery.setContributes(true); // so not interruption
            return true;
         }
      }
      System.out.println("No recovery plans found!");
      return false;
   }
  
   private Plan invokePlanner (Plan candidate) {
      // this should invoke Prolog planner
      // for now it always returns the answer to make our
      // example work, namely executing "Open"
      //return new Plan(candidate.getGoal().getType().getEngine().getTaskClass("Open").newInstance());
	   TaskEngine d = candidate.getGoal().getType().getEngine();
	   
	   Plan p = newPlan(d,"recovery");
	   // add a loop to insert the plan repair 

	   /* hard-coded plan repair */
	   Struct unlock = new Struct("unlock");
	   Struct open = new Struct("open");
	   Struct list = new Struct(unlock, open);
	   
	   /* soft-coded */
	   // 1. inject current state into prolog
	   // 2. inject goal
	   // 3. call the planner
	   // 4. decompose the output into actions
	   // 5. transform this into Java 
	  
	   if ( candidate.isFailed() ) {
	      // TODO: recovery plan needs to be inserted differently
	      //       (more like retry)
	   } else {
	      /* inject the plan into Disco */
	      for(int i=0;i<list.listSize(); i++) {
	         p.add(newPlan(d,list.getArg(i).toString()));
	         // later, if we work at order 1, we will need to look in the term for the arguments...
	      }
	   }
	   return p;
   }
  
   private static Plan newPlan (TaskEngine disco, String name) {
	   return new Plan(disco.getTaskClass(name).newInstance());
   }
   private final List<Plan> candidates = new ArrayList<Plan>();
   
   /**
    * @return candidates for recovery planning target in given tree
    */
   private void findCandidates (List<Plan> children) {
      for (Plan plan : children) {
         // if plan is neither done nor live nor blocked
         // then its precondition must be false
         if ( !(plan.isDone() || plan.isLive() || plan.isBlocked()) ) 
            candidates.add(plan);
         // if plan failed and it has a postcondition then that
         // postcondition could be a planning target
         if ( plan.isFailed() && plan.getGoal().getType().getPostcondition() != null )
            candidates.add(plan);
         findCandidates(plan.getChildren());
      }
   }
   
   @Override
   public Plugin.Item respondIf (Interaction interaction, boolean guess) {
      Plugin.Item item = super.respondIf(interaction, guess);
      // if current toplevel goal is not done and we have
      // nothing to do, then we have a breakdown to recover from
      Plan focus = interaction.getFocus();
      if ( focus != null && !interaction.getDisco().getTop(focus).isDone() && item == null ) {
         if ( recover(interaction) ) 
            return super.respondIf(interaction, guess);  // new response with new plan
         else return null;
      } else return item;
   }
}
