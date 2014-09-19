package fr.limsi.discolog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

public class Expriments {
	static ArrayList<String> recipecondition = new ArrayList<String>();
	static List<DecompositionClass> recipes = new ArrayList<DecompositionClass>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");
	public static Plan top;
	public static int cpt = 1;
	public static void main(String[] args) throws IOException {
		Expriments test = new Expriments();
		Node A = new Node("a", "P1", "P2");
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		int depth = 2;
		int length = 2;
		int recipe = 2;
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
		conditions = RecipeTree.LevelOfKnowledge(root, 100);
		RecipeTree.DefineLevelOfKnowledge(root, conditions);
		//System.out.println(RecipeTree.Init(conditions));
		//RecipeTree.printTree(root);
		// *************** plan consturction ***********************
		TaskClass top = test.FromTreeToTask(root);
		test.GeneratePlan(root, top);
		Plan pl =test.newPlan(top);
		System.out.println(pl.getType().getId());
	}
	
	private final Discolog agent = new Discolog("agent");
	private final Interaction interaction = new Interaction(agent, new User("user")) {
		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
		   // note retry false for testing experiment
			while (getSystem().respond(interaction, false, false, false)) {
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
		TaskClass task = new TaskClass(model, id, precondition == null ? null : new Precondition(
				precondition, true, disco),postcondition == null ? null : new Postcondition(postcondition,
				true, true, disco), grounding == null ? null : new Grounding(
				grounding, disco));
		task.setProperty("@primitive", primitive);
		return task;
	}

	private DecompositionClass newRecipe(String id, TaskClass goal,
			List<Step> steps, String applicable) {
		return new DecompositionClass(model, id, goal, steps,
				applicable == null ? null : new Applicability(applicable, true,
						disco));
	}

	private static Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}
	
	public TaskClass FromTreeToTask(RecipeTree root) {
		// verifier si les conditions ne sont pas nulls
		if (root.isLeaf()){
			Random rand = new Random();
			int nombreAleatoire = rand.nextInt(2);
			if(nombreAleatoire ==1)
			return ( newTask(root.getHead().getName(),true, 
					root.getHead().getPreconditions(), root.getHead().getPostconditions(),
					root.getHead().getPostconditions() == null ?null
							:root.getHead().getPostconditions()+ "=true;println('"+ root.getHead().getName() + "')"));
					 
			else 
				return(newTask(root.getHead().getName(),true,root.getHead().getPreconditions(),	root.getHead().getPostconditions(),
					root.getHead().getPostconditions() == null ? null 
							: root.getHead().getPostconditions()+ "=true;println('"
											+ root.getHead().getName() + "   "+ root.getHead().getPostconditions() +" =false ')"));
			
		}
		else
			return (newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null));
	}
	
	public void GeneratePlan(RecipeTree root, TaskClass top) {
		TaskClass child=null;
		ArrayList <Plan>children = new ArrayList<Plan>(); 
		children.clear();
		List<Step> step = new ArrayList<Step>();
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree node : NodeEntry.getValue()) {
					//System.out.println(node.getHead().getName());
					child=FromTreeToTask(node);
					step.add(new Step("s" + child, child));
					child.print(System.out);
					GeneratePlan(node, child);
				}
				if(conditions.contains("C" + NodeEntry.getKey())){
					newRecipe(NodeEntry.getKey().toString(), top,
							step, "C" + NodeEntry.getKey());
							//step, null);
					recipecondition.add(NodeEntry.getKey());
				}
				else newRecipe(NodeEntry.getKey().toString(), top,
						step, null);
				step.clear();
				
			}
			//ystem.out.println(root.getHead().getName());

		}
			/*for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree node : NodeEntry.getValue()) {
					child=FromTreeToTask(node);
					step.add(new Step("s" + child, child));
					cpt++;
					//children.add(child);
					//top.add(child);
					this.GeneratePlan(node, child);
				}
				if(conditions.contains("C" + NodeEntry.getKey())){
					newRecipe(NodeEntry.getKey().toString(), top.getType(),
							step, "C" + NodeEntry.getKey());
							//step, null);

					recipecondition.add(NodeEntry.getKey());
				}
				else newRecipe(NodeEntry.getKey().toString(), top.getType(),
						step, null);
				step.clear();
				for(Plan ch: children)
					top.add(ch);
				children.clear();
			}
		}*/
		
	}
	
	//*********************************************************************************************
}
