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
	
	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = 
			new Interaction(new Discolog("agent"), new User("user"), null){

		// for debugging with Disco console, comment out this override
		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (!Thread.currentThread().isInterrupted()) {}
		}
	}
	;

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
						postcondition == null ? null :  new Postcondition(postcondition,true, false, disco),
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


	public void childTest(TaskClass task, RecipeTree root, RecipeTree child, String initState ){
		disco.clear();
		System.out.println(initState);
		//		TaskEngine.DEBUG = true; 
		//		TaskEngine.VERBOSE = true;
		disco.eval(initState, "init");
		disco.clearLiveAchieved();
		//RECOVERY =newTask("recovery", false, null, null, null);
		Plan top = newPlan(task);
		// add intention
		disco.addTop(top);
		// push top onto stack
		disco.push(top);
		((Discolog)interaction.getSystem()).setMax(1000);
		top.setContributes(true); 
	}

	public void CreateBenshmark (RecipeTree root, TaskClass task) throws IOException{
		generateTasks(root, task);

	}
	public  TaskClass FromTreeToTask(RecipeTree root) throws IOException {

		if (root.isLeaf()){
			return(newTask(root.getHead().getName(),true,root.getHead().getPreconditions(),	root.getHead().getPostconditions(),
					// preconditions are false
					"if ("+root.getHead().getName()+" == false) {"+
					// psotconditions put to false and change the flag to true
					root.getHead().getGrounding().get(1).toString()+ "=false; "
					+root.getHead().getName()+ "=true;}"
					// else if not the first run put the postcond to true
					+ " else { "+root.getHead().getGrounding().get(1)+ "=true;}"));

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
									 
					if(i>0){
						step.add(new Step(child.getId(), child, 1, 1, 
								Collections.singletonList(NodeEntry.getValue().get(i-1).getHead().getName().toString())));
					}
					else 
						step.add(new Step(child.getId(), child, 1, 1, null)); 



				}

				newRecipe(NodeEntry.getKey().toString(), top,
						step, "C" + NodeEntry.getKey().toString());
				newTask(NodeEntry.getKey().toString().toLowerCase(), true, root.getHead().getPreconditions(),
						"C"+NodeEntry.getKey().toString() ,
						"C"+NodeEntry.getKey().toString()+"=true;"
								+ "println('C"+NodeEntry.getKey().toString()+"')");

				step.clear();					
			}
		}		
	}




}