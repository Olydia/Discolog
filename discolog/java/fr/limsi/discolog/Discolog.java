package fr.limsi.discolog;

import java.util.*;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskEngine;
import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;
import alice.tuprolog.Struct;
import alice.tuprolog.lib.*;
import alice.tuprolog.event.*;
import alice.tuprologx.pj.model.Var;
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
               disco.getFocus().add(recovery);
               recovery.setContributes(true); // so not interruption
               return;
            }
         }
         System.out.println("No recovery plans found!");
      }
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
	  
	   /* inject the plan into Disco */
	   for(int i=0;i<list.listSize(); i++) {
		   p.add(newPlan(d,list.getArg(i).toString()));
		   // later, if we work at order 1, we will need to look in the term for the arguments...
	   }
	   
	   return p;
	   }
  
   private static Plan newPlan (TaskEngine disco, String name) {
	   return new Plan(disco.getTaskClass(name).newInstance());
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
