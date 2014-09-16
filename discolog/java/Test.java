import java.util.ArrayList;
import java.util.Collections;
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
		// TODO Auto-generated method stub
		Test test = new Test();
		TaskClass
		a11 = test.newTask("a11", true, "p1", "p3",	"p3=true;println('a11')"),
		a12 = test.newTask("a12", true, "p3", "p2", "p2=true;println('a12')"),
		a21 = test.newTask("a21", true, "p1", "p3", "p3=true;println('a21')"), 
		a22 = test.newTask("a22", true, "p3", "p2", "p2=true;println('a22')"),
		// recursive propagation of pre/postconditions up the tree
		a = test.newTask("a", false, a11.getPrecondition().getScript(), a12
				.getPostcondition().getScript(), null);
		
		List<Step> steps = new ArrayList<Step>();
		steps.add(new Step("s3", a11));
		steps.add(new Step("s4", a12));
		
		List<Step> steps1 = new ArrayList<Step>();
		steps.add(new Step("s3", a21));
		steps.add(new Step("s4", a22));
		
		test.newRecipe("r1", a,steps, "V");
		test.newRecipe("r2", a,steps1, "W");
		
		// build the non-recipe part of the tree
		Plan top = newPlan(a); 
		/*
		 * top.add(newPlan(p1)); top.add(newPlan(b));
		 
		 */// needed only for non-recipe nodes
		//top.setPlanned(true);
		test.disco.addTop(top);
		// prevent agent asking about toplevel goal
		test.disco.setProperty("Ask.Should(a)@generate", false);
		// initialize all world state predicates
		test.disco.eval("var p1,p2,p3=false,p4=false,W=true,V=false", "init");
		TaskEngine.VERBOSE = true;
		TaskEngine.DEBUG=true;
		// allow agent to keep executing without talking
		((Agent) test.interaction.getSystem()).setMax(1);
		// agent starts
		test.interaction.start(false);
	}

	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = new Interaction(new Agent("agent"),
			new User("user")) {

		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (getSystem().respond(interaction, false, false, true)) {
			}
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
		return task;
	}

	DecompositionClass newRecipe(String id, TaskClass goal, List<Step> steps,
			String applicable) {
		return new DecompositionClass(model, id, goal, steps,
				new Applicability(applicable, true, disco));
	}

	private static Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}

}
