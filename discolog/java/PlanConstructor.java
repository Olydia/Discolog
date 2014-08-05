

import java.util.List;
import edu.wpi.cetask.DecompositionClass;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskClass;
import edu.wpi.cetask.TaskModel;
import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.DecompositionClass.Step;
import edu.wpi.cetask.TaskClass.Grounding;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import fr.limsi.discolog.Discolog;
import fr.limsi.discolog.Node;
import fr.limsi.discolog.Tree;

public class PlanConstructor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlanConstructor test = new PlanConstructor();
		Tree root = new Tree(new Node("a"));
		Tree.createTree(root, 3, 3, "");
		Tree.defineKnowledge(root);
		Plan top = test.FromTreeToPlan(root);
		test.GeneratePlan(root, top);
		//test.getLeaves(top);
		//test.printPlan(top);
		top.setPlanned(true); // needed only for non-recipe nodes
		test.disco.addTop(top);
		// prevent agent asking about toplevel goal
		test.disco.setProperty("Ask.Should(a)@generate", false);
		// initialize all world state predicates
		test.disco.eval(Tree.Init(root).toString(), "init");
		// allow agent to keep executing without talking
		((Discolog) test.interaction.getSystem()).setMax(100);
		// agent starts
		test.interaction.start(false);

	}

	// NB: use instance of Discolog extension instead of Agent below
	private final Interaction interaction = new Interaction(new Discolog("agent"),
			new User("user")) {

		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (getSystem().respond(interaction, false, false)) {
			}
		}
	};

	private final Disco disco = interaction.getDisco();
	private final TaskModel model = new TaskModel(
			"urn:edu.wpi.cetask:models:Test", disco);

	private TaskClass newTask(String id, boolean primitive,
			String precondition, String postcondition, String grounding) {
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

	/*private DecompositionClass newRecipe(String id, TaskClass goal,
			List<Step> steps, String applicable) {
		return new DecompositionClass(model, id, goal, steps,
				new Applicability(applicable, true, disco));
	}*/
	private static Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}

	public Plan FromTreeToPlan(Tree node) {
		if (node.isLeaf())
			return (newPlan(newTask(node.getHead().getName(), true, node.getHead().getPreconditions(),
					node.getHead().getPostconditions(), 
					node.getHead().getPostconditions().toString()+ "=true;println('" + node.getHead().getName() + "')")));
		else
			return (newPlan(newTask(node.getHead().getName(), false, node
					.getHead().getPreconditions(), node.getHead()
					.getPostconditions(), null)));
		
	}

	public void GeneratePlan(Tree root, Plan top) {
		Plan child = null;
		if (!root.isLeaf()) {
			for (Tree i : root.getChildren()) {
				child = FromTreeToPlan(i);
				top.add(child);
				GeneratePlan(i, child);
			}
		}

	}
	//github
	//********************************************************
	public void getLeaves(Plan top) {
		if (top.isPrimitive())
			System.out.println(top.getGoal().getGrounding().getScript());
		else {
			for (Plan child : top.getChildren())
				getLeaves(child);
		}

	}
	//********************************************************
	public void printPlan(Plan top) {
		System.out.print(top.getGoal().getType().getId()+"		");
		System.out.print(top.getGoal().getType().getPrecondition().getScript()+"		");
		System.out.println(top.getGoal().getType().getPostcondition().getScript());
		if (!top.isPrimitive()) {
			for (Plan i : top.getChildren()) {
				printPlan(i);
			}
		
		}
		//else 		System.out.print(top.getGoal().toString()+"		Gronding Script " +top.getGoal().getGrounding().getScript());

	}
}
