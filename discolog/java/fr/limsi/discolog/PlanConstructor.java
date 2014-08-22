package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class PlanConstructor {
	static ArrayList<String> recipecondition = new ArrayList<String>();
	static List<DecompositionClass> recipes = new ArrayList<DecompositionClass>();
	
	public static Plan top;
	public static int cpt = 1;
	public static void main(String[] args) {
		PlanConstructor test = new PlanConstructor();
		// TODO Auto-generated method stub
		Node A = new Node("a", "P1", "P2");
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		int depth = 2;
		int length = 2;
		int recipe = 2;
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
		Plan top = test.FromTreeToPlan(root);
		// *************** plan consturction
		test.GeneratePlan(root, top, test);
		test.RecipeRecoveryTask(recipecondition,top);
		 test.getLeaves(top);
		//test.printPlan(top);
		 System.out.println(test.FromTreeToProlog(root,recipecondition));
		 for(DecompositionClass a : top.getType().getDecompositions())
				 System.out.println(a.getApplicable().getScript());
/*
		top.setPlanned(true); // needed only for non-recipe nodes
		test.disco.addTop(top); // prevent agent asking about toplevel goal
		test.disco.setProperty("Ask.Should(a)@generate", false); //
		// initialize all world state predicates
		test.disco.eval(RecipeTree.Init(RecipeTree.cmp, recipecondition),"init"); // allow agent to keep executing without talking
							// ((Discolog)
		test.disco.eval("CR8,CR9=false","init1");
		((Discolog) test.interaction.getSystem()).setMax(100); // agent starts
		test.interaction.start(false);
//*/
	}
	
	// NB: use instance of Discolog extension instead of Agent below
	private final Interaction interaction = new Interaction(new Discolog(
			"agent"), new User("user")) {

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

	private DecompositionClass newRecipe(String id, TaskClass goal,
			List<Step> steps, String applicable) {
		return new DecompositionClass(model, id, goal, steps,
				new Applicability(applicable, true, disco));
	}

	private static Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}

	public Plan FromTreeToPlan(RecipeTree root) {
		if (root.isLeaf())
			return (newPlan(newTask(root.getHead().getName(), true, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					root.getHead().getPostconditions().toString()
							+ "=true;println('" + root.getHead().getName()
							+ "')")));
		else
			return (newPlan(newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null)));

	}

	/*
	 * public void GeneratePlan(Tree root, Plan top) { Plan child = null; if
	 * (!root.isLeaf()) { for (Tree i : root.getChildren()) { child =
	 * FromTreeToPlan(i); top.add(child); GeneratePlan(i, child); } }
	 * 
	 * }
	 */
	public void RecipeRecoveryTask(ArrayList<String> recipecondition,Plan top){
		for(String recipe: recipecondition){
			top.add(newPlan(newTask(recipe, true, "P1", recipe, recipe+"=true;println('"+recipe+"')")));
		}
	}
	public void GeneratePlan(RecipeTree root, Plan top, PlanConstructor test) {
		Plan child=null;
		
		List<Step> step = new ArrayList<Step>();
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree node : NodeEntry.getValue()) {
					child=FromTreeToPlan(node);
					step.add(new Step("s" + cpt, child.getType()));
					cpt++;
					top.add(child);
					this.GeneratePlan(node, child, test);
				}
				test.newRecipe(NodeEntry.getKey().toString(), top.getType(),
						step, "C" + NodeEntry.getKey());
				step.clear();
				recipecondition.add("C" + NodeEntry.getKey());
			}
		}
		
	}

	// github
	// ********************************************************
	public void getLeaves(Plan top) {
		if (top.isPrimitive())
			System.out.println(top.getGoal().getType().getId());
		else {
			for (Plan child : top.getChildren())
				getLeaves(child);
		}

	}

	// ********************************************************
	public void printPlan(Plan top) {
		System.out.print(top.getGoal().getType().getId()+"		");
		/*for(DecompositionClass recipe: top.getGoal().getType().getDecompositions()){
			System.out.print(recipe.getId()+ "	"+recipe.getApplicable().getScript()+ "		");
			for(String name:recipe.getStepNames()){
				System.out.print(recipe.getStepType(name)+ "	");
			}
			System.out.println("");
		}*/
		System.out.print(top.getGoal().getType().getPrecondition().getScript()+"		");
		System.out.println(top.getGoal().getType().getPostcondition().getScript());
		if (!top.isPrimitive()) {
			for (Plan i : top.getChildren()) {
				printPlan(i);
			}
		
		}
		//else 		System.out.print(top.getGoal().toString()+"		Gronding Script " +top.getGoal().getGrounding().getScript());

	}
	public String FromTreeToProlog (RecipeTree root, ArrayList<String> recipecondition ){
		String prolog = "";
		for(RecipeTree leaf:root.getLeaves()){
			if(leaf.getHead().getPreconditions()!=null)
				prolog+= "strips_preconditions("+leaf.getHead().getName().toLowerCase()+",["+leaf.getHead().getPreconditions().toLowerCase()+"]).\n";
			if(leaf.getHead().getPostconditions()!=null)
				prolog+= "strips_achieves("+leaf.getHead().getName().toLowerCase()+","+leaf.getHead().getPostconditions().toLowerCase()+").\n";
		}
		for(String recipe:recipecondition){
			prolog+= "strips_preconditions("+recipe.toLowerCase()+"p,[p1]).\n";
			prolog+= "strips_achieves("+recipe.toLowerCase()+"p,"+recipe.toLowerCase()+").\n";
			prolog+="strips_primitive("+recipe.toLowerCase()+").\n";
			}
		for (int i = 1; i < root.cmp; i++) {
			prolog +="strips_primitive(p"+i+").\n";
		}
		return prolog;
	}
}
