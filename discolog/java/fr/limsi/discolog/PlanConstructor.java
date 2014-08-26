package fr.limsi.discolog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

public class PlanConstructor {
	static ArrayList<String> recipecondition = new ArrayList<String>();
	static List<DecompositionClass> recipes = new ArrayList<DecompositionClass>();
	
	public static Plan top;
	public static int cpt = 1;
	public static List<String> conditions = new LinkedList<String>();

	public static void main(String[] args) throws IOException {
		PlanConstructor test = new PlanConstructor();
		/****************************
		 */
		// TODO Auto-generated method stub
		Node A = new Node("a", "P1", "P2");
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		int depth = 2;
		int length = 2;
		int recipe = 2;
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
		conditions = RecipeTree.LevelOfKnowledge(root, 75);
		RecipeTree.DefineLevelOfKnowledge(root, conditions);
		//System.out.println(root.toString());
		//RecipeTree.printTree(root);
		System.out.println(RecipeTree.Init(conditions));
		// *************** plan consturction ***********************
		Plan top = test.FromTreeToPlan(root);
		test.GeneratePlan(root, top, test);
		test.RecipeRecoveryTask(recipecondition, top);
		//test.printPlan(top);
		test.FromTreeToProlog(root, recipecondition, conditions);
		System.out.println(" ******************* *******************");
		// ******************* Disco plan ******************
/*
		top.setPlanned(true); // needed only for non-recipe nodes
		test.disco.addTop(top); // prevent agent asking about toplevel goal
		test.disco.setProperty("Ask.Should(a)@generate", false); //
		// initialize all world state predicates
		test.disco.eval(RecipeTree.Init(conditions),"init"); // allow agent
		// to keep executing without talking
		((Discolog) test.interaction.getSystem()).setMax(100); // agent starts
		test.interaction.start(false);
		System.out.println(test.disco.getTop(top).getGoal().getType().getId());*/
	}
	
	
	// NB: use instance of Discolog extension instead of Agent below
	private final Agent agent = new Discolog("agent");
	private final Interaction interaction = new Interaction(agent, new User("user")) {
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

	public Plan FromTreeToPlan(RecipeTree root) {
		// verifier si les conditions ne sont pas nulls
		if (root.isLeaf())
			return (newPlan(newTask(root.getHead().getName(), true, root
					.getHead().getPreconditions(), root.getHead()
					.getPostconditions(),
					root.getHead().getPostconditions() == null ? null : root
							.getHead().getPostconditions()
							+ "=true;println('"
							+ root.getHead().getName() + "')")));
		else
			return (newPlan(newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null)));

	}


	public void RecipeRecoveryTask(ArrayList<String> recipecondition,Plan top){
		for(String recipe: recipecondition){
			top.add(newPlan(newTask(recipe, true, null, recipe, conditions.contains(recipe) ?recipe+"=true;println('"+recipe+"')" : null)));
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
				if(conditions.contains("C" + NodeEntry.getKey())){
					test.newRecipe(NodeEntry.getKey().toString(), top.getType(),
							step, "C" + NodeEntry.getKey());
					recipecondition.add(NodeEntry.getKey());
				}
				else test.newRecipe(NodeEntry.getKey().toString(), top.getType(),
						step, null);
				step.clear();
				
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
		System.out.print (top.getGoal().getType().getId());
		System.out
				.print(top.getGoal().getType().getPrecondition() == null ? "	[null "
						: "	[" +top.getGoal().getType().getPrecondition().getScript()
								+ "	,");
		System.out
				.println(top.getGoal().getType().getPostcondition() == null ? "null "
						: top.getGoal().getType().getPostcondition()
								.getScript() +" ]");
		if (!top.isPrimitive()) {
			for (Plan i : top.getChildren()) {
				printPlan(i);
			}

		}
	}
	public void FromTreeToProlog (RecipeTree root, ArrayList<String> recipecondition,List<String> conditions ) throws IOException{
		String prolog = "";
		File file =new File("C:/Users/Lydia/Documents/GitHub/Discolog/discolog/prolog/test-2p/testp.pl");
		FileWriter fileWritter = new FileWriter(file.getName(),true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        
		for(RecipeTree leaf:root.getLeaves()){
			if(leaf.getHead().getPreconditions()!=null)
				 bufferWritter.write( "strips_preconditions("+leaf.getHead().getName().toLowerCase()+",["+leaf.getHead().getPreconditions().toLowerCase()+"]).");
			if(leaf.getHead().getPostconditions()!=null)
				 bufferWritter.write("strips_achieves("+leaf.getHead().getName().toLowerCase()+","+leaf.getHead().getPostconditions().toLowerCase()+").");
			 bufferWritter.newLine();
		}
		for(String recipe:recipecondition){
			//prolog+= "strips_preconditions("+recipe.toLowerCase()+"p,[p1]).\n";
			 bufferWritter.write("strips_achieves("+recipe.toLowerCase()+",c" +recipe.toLowerCase()+").");
			 bufferWritter.newLine();
			 bufferWritter.write("strips_primitive("+recipe.toLowerCase()+").");
			 bufferWritter.newLine();
			}
		for (String i: conditions) {
			bufferWritter.write("strips_primitive("+i.toLowerCase()+").");
			bufferWritter.newLine();

		}
		
      
        bufferWritter.close();
        System.out.println("Done");

	}
	
	public static List<String> EvalConditions(List<String> conditions, PlanConstructor test){
		 List<String> liveCond = new ArrayList<String>();
		 for (int i = 0; i < conditions.size(); i++){
			 if ((Boolean)test.disco.eval(conditions.get(i),"init"))
				System.out.println(conditions.get(i));
		 }
		return liveCond;
		
	}
}
