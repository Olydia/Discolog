package fr.limsi.discolog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

public class PlanConstructor {
	public static TaskClass RECOVERY;

	static List<String> recipecondition = new LinkedList<String>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");
	/*

	public static void main(String[] args) throws IOException {
		PlanConstructor test = new PlanConstructor();
		FileOutputStream output = test.InitSTRIPSPlanner();
		Node A = new Node("a", "P1", "P2"),
				A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child),
				partialroot = new RecipeTree(A2, copyChild);
		int depth = 2, length = 2, recipe = 2;
		RecipeTree.DefineCompleteTree(root, depth, length, recipe);
		int cond = RecipeTree.levelOfConditions(depth, length, recipe, 25);
		recipecondition=RecipeTree.removeRecipesConditions(RecipeTree.RecipeCondition, 25);
		RecipeTree.DefinepartialTree(root, partialroot, cond);
		RecipeTree.printTree(partialroot);
		conditions = root.getKnowledge(root, conditions);
		TaskClass task = test.FromTreeToTask(partialroot,output);
		test.CreateBenshmark(partialroot, task, output, RecipeTree.RecipeCondition);
		output = test.InitSTRIPSPlanner();
		test.LanchTest(task,conditions,root);
	}*/
	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = 
			new Interaction(new Discolog("agent"), new User("user"), null){

		// for debugging with Disco console, comment out this override
//				@Override
//				public void run() {
//					// keep running as long as agent has something to do and then stop
//				while (!Thread.currentThread().isInterrupted()) {}
//				}
	};

	final  Disco disco = interaction.getDisco();
	private final TaskModel model = new TaskModel(
			"urn:edu.wpi.cetask:models:Test", disco);

	TaskClass newTask(String id, boolean primitive, String precondition,
			String postcondition, String grounding) {
		if (!primitive && grounding != null)
			throw new IllegalArgumentException(
					"Non-primitive cannot have grounding script: " + id);
		TaskClass task = new TaskClass(model, id,
				precondition == null ? null : new Precondition(precondition, true, disco),
						postcondition == null ? null :  new Postcondition(postcondition,true, true, disco),
								grounding == null ? null : new Grounding(grounding, disco)
				);

		task.setProperty("@primitive", primitive);
		task.setProperty("@internal", true);
		return task;
	}

	DecompositionClass newRecipe(String id, TaskClass goal, List<Step> steps,
			String applicable) {
		DecompositionClass decomp = new DecompositionClass(model, id, goal, steps,applicable == null?null:
			new Applicability(applicable, true, disco));
		decomp.setProperty("@internal", true);
		decomp.setProperty("@authorized", true);
		return decomp;
	}

	private Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}
	//********************************* diso *********************************************************
	public void LanchTest (TaskClass task, List<String> conditions, RecipeTree root, String broken) throws IOException{
		String initState = RecipeTree.Init(conditions, root, broken);
		disco.eval(initState, "init");
		RECOVERY =newTask("recovery", false, null, null, null);
		Plan top = newPlan(task);
		// add intention
		disco.addTop(top);
		// push top onto stack
		disco.push(top);
		// prevent agent asking about toplevel goal
		top.getGoal().setShould(true);
		TaskEngine.VERBOSE = true;
		((Discolog)interaction.getSystem()).setMax(1000);
		interaction.start(false);
	}

	public void childTest(TaskClass task, List<String> conditions, RecipeTree root, RecipeTree child){
		disco.clear();
		String initState = RecipeTree.Init(conditions, root,child.getHead().getName());
		disco.eval(initState, "init");
		//RECOVERY =newTask("recovery", false, null, null, null);
		Plan top = newPlan(task);
//		// add intention
		disco.addTop(top);
		// push top onto stack
		disco.push(top);
		// prevent agent asking about toplevel goal
		//top.getGoal().setShould(true);
//		TaskEngine.VERBOSE = true;
		((Discolog)interaction.getSystem()).setMax(1000);
		//disco.getFocus().add(top);
		top.setContributes(true); 
	}
	
	public void CreateBenshmark (RecipeTree root, TaskClass task) throws IOException{
		generateTasks(root, task);
		RecipeRecoveryTask(RecipeTree.RecipeCondition);

		//output.close();
	}
	public  TaskClass FromTreeToTask(RecipeTree root) throws IOException {

		if (root.isLeaf()){
			return(newTask(root.getHead().getName(),true,root.getHead().getPreconditions(),	root.getHead().getPostconditions(),
					// preconditions are false
					"if ("+root.getHead().getGrounding().get(0)+ "==false) {"+	
					"$this.success = false;} "+
					"else if ("+root.getHead().getName()+" == false) {"+
					// psotconditions put to false and change the flag to true
					root.getHead().getGrounding().get(1).toString()+ "=false;"/*+" println('"
					+ root.getHead().getName() + "  "+ root.getHead().getGrounding().get(1).toString() +" =false ');"*/ 
					+root.getHead().getName()+ "=true;}"
					// else if not the first run put the postcond to true
					+ "else { "+root.getHead().getGrounding().get(1)+ "=true;"+/*" println('"
					+ root.getHead().getName() + "   "+ root.getHead().getGrounding().get(1) +"');"+*/"}"));

		}
		else
			return (newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null));
	}

	public void generateTasks(RecipeTree root, TaskClass top) throws IOException {
		TaskClass child=null;
		List<Step> step = new LinkedList<Step>();
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {

				for (int i=0; i<NodeEntry.getValue().size(); i++) {
					RecipeTree node = NodeEntry.getValue().get(i);
					child=FromTreeToTask(node);
					generateTasks(node, child);
					//					System.out.print(child.getId() + "[");
					//					System.out.print( child.getPrecondition() == null ? "null, " : child.getPrecondition().getScript() +"," );
					//					System.out.println (child.getPostcondition() == null ? "null]"  : child.getPostcondition().getScript()  +"],"
					//													 + child.getDecompositions().size());
					//					 
					if(i>0){
						step.add(new Step(child.getId(), child, 1, 1, 
								Collections.singletonList(NodeEntry.getValue().get(i-1).getHead().getName().toString())));
						//System.out.println(NodeEntry.getValue().get(i-1).getHead().getName().toString());
					}
					else 
						step.add(new Step(child.getId(), child, 1, 1, null)); 



				}
				if(RecipeTree.RecipeCondition.contains(NodeEntry.getKey().toString())){		
					newRecipe(NodeEntry.getKey().toString(), top,
							step, "C" + NodeEntry.getKey().toString());
				}
				else newRecipe(NodeEntry.getKey().toString(), top,
						step, null);
				step.clear();					
			}
		}		
	}

	public void RecipeRecoveryTask(List<String> conditions) throws IOException{
		for(String recipe: conditions){

			newTask(recipe.toLowerCase(), true, null,"C"+recipe ,"C"+recipe+"=true;println('C"+recipe+"')");
		}
	}


}
