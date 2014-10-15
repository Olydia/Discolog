package fr.limsi.discolog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

public class PlanConstructor {
	public static TaskClass RECOVERY;

	static List<String> recipecondition = new LinkedList<String>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");


	public static void main(String[] args) throws IOException {
		PlanConstructor test = new PlanConstructor();
		BufferedWriter output = test.InitSTRIPSPlanner();
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
		test.LanchTest(task,conditions);
	}
	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = 
	      new Interaction(new Discolog("agent"), new User("user"), null){
	   
	   // for debugging with Disco console, comment out this override
		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (getSystem().respond(interaction, false, true, false)) {}
		}
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
	public void LanchTest (TaskClass task, List<String> conditions) throws IOException{
		
		RECOVERY =newTask("recovery", false, null, null, null);
		Plan top = newPlan(task);
		// add intention
		disco.addTop(top);
		// push top onto stack
		disco.push(top);
		// prevent agent asking about toplevel goal
		top.getGoal().setShould(true);
		TaskEngine.VERBOSE = true;
		String initState = RecipeTree.Init(conditions);
		disco.eval(initState, "init");
		System.out.println(initState);
		((Discolog)interaction.getSystem()).setMax(1000);
		interaction.start(false);
	}
	public void CreateBenshmark (RecipeTree root, TaskClass task, BufferedWriter output, List<String> conditions) throws IOException{
		generateTasks(root, task,output, RecipeTree.RecipeCondition);
		RecipeRecoveryTask(output,RecipeTree.RecipeCondition);
		for (String i : conditions) {
			output.write("strips_primitive(" + i.toLowerCase() + ").");
			output.newLine();
			output.flush();
		}
		//output.close();
	}
	public  TaskClass FromTreeToTask(RecipeTree root, BufferedWriter output) throws IOException {
		
		if (root.isLeaf()){
			if (root.getHead().getPostconditions() != null) {
				//Preconditions 
				if (root.getHead().getPreconditions() != null) {
					output.write("strips_preconditions("
							+ root.getHead().getName().toLowerCase() + ",["
							+ root.getHead().getPreconditions().toLowerCase()
							+ "]).");
					output.newLine();
					output.flush();
				}
				else {
					output.write("strips_preconditions("
							+ root.getHead().getName().toLowerCase() + ",[_]).");
					output.newLine();
					output.flush();
				}	
				//Create breakdown 
				if(root.getHead().getGrounding().get(2) == "true"){
					output.write("strips_achieves("
							+ root.getHead().getName().toLowerCase() + ","
							+ root.getHead().getPostconditions().toLowerCase()
							+ ").");
					output.newLine();
					output.flush();
					return ( newTask(root.getHead().getName(),true, 
							root.getHead().getPreconditions(), root.getHead().getPostconditions(),
							root.getHead().getGrounding().get(1)+ "=true;"+root.getHead().getGrounding().get(0) +" !=false;println('"+ root.getHead().getName() + "')"));

				}
				else {
					output.write("strips_achieves("
							+ root.getHead().getName().toLowerCase() + ",\\+"
							+ root.getHead().getPostconditions().toLowerCase()
							+ ").");
					output.newLine();
					output.flush();
					return(newTask(root.getHead().getName(),true,root.getHead().getPreconditions(),	root.getHead().getPostconditions(),
							root.getHead().getGrounding().get(1)+ "=false;"+root.getHead().getGrounding().get(0) +" !=false;println('"
									+ root.getHead().getName() + "   "+ root.getHead().getPostconditions() +" =false ')"));

				}
			}
			else{
				/*output.write("strips_achieves("
						+ root.getHead().getName().toLowerCase() + ",_).");
				output.newLine();
				output.flush();**/
				return(newTask(root.getHead().getName(),true,root.getHead().getPreconditions(),	null,null));

			}
		}
		else
			return (newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null));
	}
	

	public void generateTasks(RecipeTree root, TaskClass top,BufferedWriter output,  List<String> conditions) throws IOException {
		TaskClass child=null;
		List<Step> step = new ArrayList<Step>();
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				
				for (RecipeTree node : NodeEntry.getValue()) {
					child=FromTreeToTask(node,output);
					generateTasks(node, child,output,conditions);
					/*System.out.print(child.getId() + "[");
					System.out.print( child.getPrecondition() == null ? "null, " : child.getPrecondition().getScript() +"," );
					System.out.println (child.getPostcondition() == null ? "null]"  : child.getPostcondition().getScript()  +"],"
													 + child.getDecompositions().size());*/
					step.add(new Step("s" + child, child));
				}
				if(conditions.contains(NodeEntry.getKey().toString())){		
					newRecipe(NodeEntry.getKey().toString(), top,
							step, "C" + NodeEntry.getKey().toString());
					//recipecondition.add(NodeEntry.getKey());
				}
				else newRecipe(NodeEntry.getKey().toString(), top,
						step, null);
				/*if(conditions.contains(NodeEntry.getKey().toString())){		
					newRecipe(NodeEntry.getKey().toString(), top, step,
							NodeEntry.getValue().get(0).getHead().getGrounding().get(0).toString());
					RecipeRecoveryTask(output, NodeEntry.getKey().toString(),
							NodeEntry.getValue().get(0).getHead().getGrounding().get(0).toString() );
				}
				else { 
					newRecipe(NodeEntry.getKey().toString(), top,
						step, null);
				}*/
				step.clear();					
			}
		}		
	}
	
	/*public void RecipeRecoveryTask( BufferedWriter output , String Id, String condition) throws IOException{
			output.write("strips_preconditions(" + Id.toLowerCase() + ",[_]).");
			output.newLine();
			output.flush();
			output.write("strips_achieves(" + Id.toLowerCase() + ","
					+ condition.toLowerCase() + ").");
			output.newLine();
			output.flush();
			//System.out.println(recipe.toLowerCase()+"[null, C"+recipe+"]");
			newTask(Id.toLowerCase(), true, null, condition.toLowerCase(), condition+"=true;println('"+condition+"')");
		
	}*/
	
	public void RecipeRecoveryTask( BufferedWriter output , List<String> conditions) throws IOException{
		for(String recipe: conditions){
			output.write("strips_preconditions(" + recipe.toLowerCase() + ",[_]).");
			output.newLine();
			output.flush();
			output.write("strips_achieves(" + recipe.toLowerCase() + ",c"
					+ recipe.toLowerCase() + ").");
			output.newLine();
			output.flush();
			//System.out.println(recipe.toLowerCase()+"[null, C"+recipe+"]");
			newTask(recipe.toLowerCase(), true, null,"C"+recipe ,"C"+recipe+"=true;println('C"+recipe+"')");
		}
	}
	
	public BufferedWriter InitSTRIPSPlanner() throws IOException{
		String adressesource = System.getProperty("user.dir") + "/prolog/test-2p/Domain_knowledge.pl";
		PrintWriter writer;
		try {
			writer = new PrintWriter(adressesource);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String adresseSource = System.getProperty("user.dir") + "/prolog/test-2p/STRIPS_planner.pl";
		try {
			copyFileUsingStream(new File(adresseSource), new File(adressesource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileWriter fw = new FileWriter(adressesource, true);
		BufferedWriter output = new BufferedWriter(fw);
		output.newLine();
		output.flush();
		return output;
	}
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}

