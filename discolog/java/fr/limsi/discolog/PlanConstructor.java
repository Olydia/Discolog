<<<<<<< HEAD
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
	static ArrayList<String> recipecondition = new ArrayList<String>();
	static List<DecompositionClass> recipes = new ArrayList<DecompositionClass>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");

	
	public static void main(String[] args) throws IOException {
		PlanConstructor test = new PlanConstructor();
		Node A = new Node("a", "P1", "P2");
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		int depth = 1;
		int length = 2;
		int recipe = 2;
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
		conditions = RecipeTree.LevelOfKnowledge(root, 100);
		RecipeTree.DefineLevelOfKnowledge(root, conditions);
		TaskClass task = test.FromTreeToTask(root);
		test.generateTasks(root, task);
		Plan top = test.newPlan(task);
		test.FromTreeToProlog(root, recipecondition, conditions);
		// add intention
		test.disco.addTop(top);
		// push top onto stack
		test.disco.push(top);
		test.newTask("recovery",false, null, null, null);
		// prevent agent asking about toplevel goal
		top.getGoal().setShould(true);
		//test.disco.eval("var p1,p5,p2,p3=false,p4=false,W=true,V=false,Y=true,X=false", "init");
		TaskEngine.DEBUG = true;
		test.disco.eval("var CR6 =true, P1 =true, P3 =true, CR8 =true, P4 =true, "
				+ "CR7 =true, P5 =false, P2 =true, CR9 =true, P6 =true, CR10 =true, "
				+ "P7 =true, CR1 =true, P8 =true, CR2 =true, P9 =true, CR3 =true,"
				+ " P10 =true, CR4 =true, P11 =false, CR5 =false, P12 =true", "init");
		// allow agent to keep executing without talking
		((Discolog) test.interaction.getSystem()).setMax(1000);
		// agent starts
		test.interaction.start(false);

	}
	

	// NB: use instance of Discolog extension instead of Agent below
	final Interaction interaction = 
	      new Interaction(new Discolog("agent"), new User("user"), null) {
	   
	   // for debugging with Disco console, comment out this override
		@Override
		public void run() {
			// keep running as long as agent has something to do and then stop
			while (getSystem().respond(interaction, false, false, false)) {}
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

	private Plan newPlan(TaskClass task) {
		return new Plan(task.newInstance());
	}

	
//********************************* diso *********************************************************

	public void RecipeRecoveryTask(ArrayList<String> recipecondition,Plan top){
		for(String recipe: recipecondition){
			top.add(newPlan(newTask(recipe, true, null, "C"+recipe, conditions.contains(recipe) ?"C"+recipe+"=true;println('C"+recipe+"')" : null)));
		}
	}
	
	public  TaskClass FromTreeToTask(RecipeTree root) {
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
											+ root.getHead().getName() + "   "+ root.getHead().getPostconditions() +" =true ')"));
			
		}
		else
			return (newTask(root.getHead().getName(), false, root.getHead()
					.getPreconditions(), root.getHead().getPostconditions(),
					null));
	}
	
	
	public void generateTasks(RecipeTree root, TaskClass top) {
		TaskClass child=null;
		List<Step> step = new ArrayList<Step>();
		
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				
				for (RecipeTree node : NodeEntry.getValue()) {
					child=FromTreeToTask(node);
					generateTasks(node, child);

					System.out.println(child.getId() + "["+child.getPrecondition().getScript() +","
													 + child.getPostcondition().getScript() +"],"
													 + child.getDecompositions().size());

					step.add(new Step("s" + child, child));
				}
				
				if(conditions.contains("C" + NodeEntry.getKey().toString())){
					
					newRecipe(NodeEntry.getKey().toString(), top,
							step, "C" + NodeEntry.getKey().toString());
					recipecondition.add(NodeEntry.getKey());
				}
				else newRecipe(NodeEntry.getKey().toString(), top,
						step, null);
				step.clear();
						
			}
		}	
	}



//************************************ PROLOG CREATION ***********************************************
	public void FromTreeToProlog (RecipeTree root, ArrayList<String> recipecondition,List<String> conditions ) {
		String adressedufichier = System.getProperty("user.dir") + "/prolog/test-2p/Domain_knowledge.pl";
		PrintWriter writer;
		try {
			writer = new PrintWriter(adressedufichier);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String adresseSource = System.getProperty("user.dir") + "/prolog/test-2p/STRIPS_planner.pl";
		try {
			copyFileUsingStream(new File(adresseSource), new File(adressedufichier));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			FileWriter fw = new FileWriter(adressedufichier, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.newLine();
			output.flush();
			for (RecipeTree leaf : root.getLeaves()) {
				if (leaf.getHead().getPreconditions() != null) {
					output.write("strips_preconditions("
							+ leaf.getHead().getName().toLowerCase() + ",["
							+ leaf.getHead().getPreconditions().toLowerCase()
							+ "]).");
					output.newLine();
					output.flush();
				}
				if (leaf.getHead().getPostconditions() != null) {
					output.write("strips_achieves("
							+ leaf.getHead().getName().toLowerCase() + ","
							+ leaf.getHead().getPostconditions().toLowerCase()
							+ ").");
					output.newLine();
					output.flush();
				}
			}
			for (String recipe : recipecondition) {

				output.write("strips_achieves(" + recipe.toLowerCase() + ",c"
						+ recipe.toLowerCase() + ").");
				output.newLine();
				output.flush();
				
			}
			for (String i : conditions) {
				output.write("strips_primitive(" + i.toLowerCase() + ").");
				output.newLine();
				output.flush();

			}
		output.close();
		}
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
			}
	}
	
	public static List<String> EvalConditions(List<String> conditions, PlanConstructor test){
		 List<String> liveCond = new ArrayList<String>();
		 for (int i = 0; i < conditions.size(); i++){
			 if ((Boolean)test.disco.eval(conditions.get(i),"init"))
				System.out.println(conditions.get(i));
		 }
		return liveCond;
		
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
=======
>>>>>>> origin/master
