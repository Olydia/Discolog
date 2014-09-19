import java.util.ArrayList;
import java.util.List;
import edu.wpi.cetask.DecompositionClass;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskClass;
import edu.wpi.cetask.TaskEngine;
import edu.wpi.cetask.TaskModel;
import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.DecompositionClass.Step;
import edu.wpi.cetask.TaskClass.Grounding;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		TaskClass
		//a11 = test.newTask("a11", true, "p1", "p3",	"p3=true;println('a11')"),
		a12 = test.newTask("a12", true, "p3", "p2", "p2=true;println('a12')"),
		a21 = test.newTask("a21", true, "p1", "p3", "p3=true;println('a21')"), 
		a22 = test.newTask("a22", true, "p3", "p2", "p2=true;println('a22')"),
		a111 = test.newTask("a111", true, "p1", "p5","p5=true;println('a111')"),
		a112 = test.newTask("a112", true, "p5", "p3","p3=true;println('a112')"),
		a121 = test.newTask("a121", true, "p1", "p5","p5=true;println('a121')"),
		a122 = test.newTask("a122", true, "p5", "p3","p3=true;println('a122')"),

		// recursive propagation of pre/postconditions up the tree	
		a11 = test.newTask("a11", false, a111.getPrecondition().getScript(), a122.getPostcondition().getScript(), null),

		a = test.newTask("a", false, a21.getPrecondition().getScript(), a22
				.getPostcondition().getScript(), null);
		List<Step> stepsr1 = new ArrayList<Step>();
		stepsr1.add(new Step("s1", a11));
		stepsr1.add(new Step("s2", a12));
		
		List<Step> stepsr2 = new ArrayList<Step>();
		stepsr2.add(new Step("s3", a21));
		stepsr2.add(new Step("s4", a22));
		
		List<Step> stepsr3 = new ArrayList<Step>();
		stepsr3.add(new Step("s5", a111));
		stepsr3.add(new Step("s6", a112));
		
		List<Step> stepsr4 = new ArrayList<Step>();
		stepsr4.add(new Step("s7", a121));
		stepsr4.add(new Step("s8", a122));
		
		test.newRecipe("r3",a11,stepsr3,"X");
		test.newRecipe("r4",a11,stepsr4,"Y");
		
		test.newRecipe("r1",a,stepsr1,"V");
		test.newRecipe("r2",a,stepsr2,"W");
		
		// build the non-recipe part of the tree
		Plan top = newPlan(a); 
		// add intention
		test.disco.addTop(top);
		// push top onto stack
		test.disco.push(top);
		// prevent agent asking about toplevel goal
		top.getGoal().setShould(true);
		// initialize all world state predicates
		test.disco.eval("var p1,p5,p2,p3=false,p4=false,W=false,V=true,Y=true,X=false", "init");
		//TaskEngine.VERBOSE = true;
		TaskEngine.DEBUG=true;
		// allow agent to keep executing without talking
		((Agent) test.interaction.getSystem()).setMax(1000);
		// agent starts
		test.interaction.start(false);
	}

	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = 
	      new Interaction(new Agent("agent"), new User("user"), null) {
	   
	   // for debugging with Disco console, comment out this override
		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (getSystem().respond(interaction, false, false, false)) {}
		}

	};

	final Disco disco = interaction.getDisco();
	private final TaskModel model = new TaskModel(
			"urn:edu.wpi.cetask:models:Test", disco);

	TaskClass newTask(String id, boolean primitive, String precondition,
			String postcondition, String grounding) {
		if (!primitive && grounding != null)
			throw new IllegalArgumentException(
					"Non-primitive cannot have grounding script: " + id);
		TaskClass task = new TaskClass(model, id, new Precondition(
				precondition, true, disco), new Postcondition(postcondition,
				true, true, disco), grounding == null ? null : new Grounding(
				grounding, disco));
		task.setProperty("@primitive", primitive);
		task.setProperty("@internal", true);
		return task;
	}

	DecompositionClass newRecipe(String id, TaskClass goal, List<Step> steps,
			String applicable) {
	   DecompositionClass decomp = new DecompositionClass(model, id, goal, steps,
				new Applicability(applicable, true, disco));
	   decomp.setProperty("@internal", true);
	   return decomp;
	}

	private static Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}

}
