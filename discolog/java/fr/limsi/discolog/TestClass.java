package fr.limsi.discolog;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;
import edu.wpi.cetask.TaskClass;
public class TestClass{

	public static List<String> conditions = new LinkedList<String>();
	public static List<String> STRIPSconditions = new ArrayList<String>();
	static List<String> viableRecipeConditions = new ArrayList<String>();
	private static List<RecipeTree> primitiveTasks = new ArrayList<RecipeTree>();
	private static List<RecipeTree> prolog_actions = new ArrayList<RecipeTree>();

	public static int NbBreakdown = 0; 
	public static int NbRecover = 0; 
	public static int NbCandidates = 0; 
	public static int NbRecoveredCandidates = 0; 
	public static TaskClass RECOVERY;
	public static BufferedWriter evaluation;
	public static BufferedWriter strips;
	public static RecipeTree partialroot = null;
	public static Prolog engine = null;
	public static HashMap<String,String> recipeConditions = new HashMap<String,String> ();
	public static BufferedWriter time_execution;

	public static void main(String[] args) throws IOException {
		int LEVEL = 50

				; // 50, 75, 100
		int debut = 1;
		int fin = 1;	
		int depth = 4, 
				taskBranching = 4, 
				recipeBranching = 1;
		Node A = new Node("a", "P1", "P2"),
				A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());

		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>()
				;

		RecipeTree root = new RecipeTree(A, child);

		partialroot = new RecipeTree(A2, copyChild);
		String time_execution_file = LEVEL +"/"+ depth+"_"+taskBranching+"_"+recipeBranching+"_"+"time_execution_file.txt";
		long begin = System.currentTimeMillis();

		// Define the complete domain knowledge 
		root.DefineCompleteTree(depth, taskBranching, recipeBranching);
		PlanConstructor test = new PlanConstructor();
		TaskClass task = test.FromTreeToTask(root);
		test.CreateBenshmark(root, task);

		long endHTNConstruction = System.currentTimeMillis();

		time_execution = saveSolution(time_execution_file, false);
		time_execution.write("Complete HTN construction : "+(endHTNConstruction - begin)+"");
		time_execution.flush();
		time_execution.newLine();
		long disco_call = System.currentTimeMillis();

		test.interaction.start(false);

		long end_disco_call = System.currentTimeMillis();
		time_execution.write("Disco call: "+(end_disco_call - disco_call)+"");
		time_execution.flush();
		time_execution.newLine();

		conditions = root.getKnowledge(conditions);
		for(int i=debut;i<=fin;i++) {
			run(LEVEL,i, root, depth, taskBranching, recipeBranching, test, task);
		}
		//	long HTN = endHTNConstruction-beginHTNConstruction;
		//System.out.println("HTN Construction: "+ HTN);

		test.interaction.interrupt();

		long end = System.currentTimeMillis();
		time_execution.write("Complete time execution : "+(end - begin)+"");
		time_execution.flush();
		time_execution.newLine();

	}
	public static void run(int level, int numero, RecipeTree root, int depth, int length, int recipe, PlanConstructor test, TaskClass task) throws IOException {
		long startRun = System.currentTimeMillis();

		String adresse = level +"/"+"test_"+depth+"_"+length+"_"+recipe +"_"+level+"_"+numero+".txt";
		evaluation = saveSolution(adresse, true);
		// Remove knowledge from  the HTN 
		//long startRun = System.currentTimeMillis();
		root.DefinepartialTree(partialroot, 100-level);
		String adresse_strips_file = level +"/"+"STRIPS_Action"+"_"+numero+".txt";
		strips = saveSolution(adresse_strips_file, false);
		long endRun = System.currentTimeMillis();
		time_execution.write("Partial Tree generation : "+(endRun - startRun)+"");
		time_execution.flush();
		time_execution.newLine();

		long start_strip_leaf = System.currentTimeMillis();
		engine = initSTRIPS();
		//System.out.println(engine.getTheory());
		long strip_leaf = System.currentTimeMillis();

		time_execution.write(" STRIPS knowledge: "+(strip_leaf - start_strip_leaf)+"");
		time_execution.flush();
		time_execution.newLine();

		int Dinit= 1;
		for(int j=0; j< Dinit; j++){
			int z=0;
			String initState = Init(conditions, root);
			System.out.println(initState);
			// Get the HTN path 
			//getHTNPath(root);
			for(int i=0; i<root.getLeaves().size()-1; i++){
				//for(RecipeTree leaf: primitiveTasks){
				RecipeTree leaf= partialroot.getLeaves().get(i);
				time_execution.write(" ######################################################## primitive task :  "+leaf.getHead().getName()
						+"   ########################################################");
				time_execution.flush();
				time_execution.newLine();
				String init = BreakInit(root, leaf.getHead().getName(), initState);
				System.out.println(level + " - " + numero  + " -  init # "+j + " - break # " + z++);
				test.childTest(task, partialroot, leaf, init);
				while (test.interaction.getSystem().respond(test.interaction, false, true, false)) {
				}
				//System.out.println(engine.getTheory());
				long update = System.currentTimeMillis();

				try {
					updateTheory(leaf,true);
					updateTheory(partialroot.getLeaves().get(i+1), false);
				} catch (MalformedGoalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				}
				long end_update = System.currentTimeMillis();
				time_execution.write("Theory update : "+ (end_update - update));
				time_execution.flush();
				time_execution.newLine();
			}

			evaluation.write(level +" " +NbBreakdown + " " + NbRecover + " " + NbCandidates + " " + NbRecoveredCandidates);
			evaluation.flush();
			evaluation.newLine();
			evaluation.flush();
			NbBreakdown = 0; NbRecover = 0; NbCandidates =0; NbRecoveredCandidates =0;
		}
	}
	private static void updateTheory(RecipeTree leaf, boolean update) throws MalformedGoalException{
		if(update){
			if(prolog_actions.contains(leaf)){
				engine.solve("assert(strips_preconditions("
						+ leaf.getHead().getName().toLowerCase() + ",["
						+ leaf.getHead().getPreconditions().toLowerCase()+ "])).");
				engine.solve("assert(strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")).");
			}
		}
		else {
			if(prolog_actions.contains(leaf)){
				engine.solve("retract(strips_preconditions("
						+ leaf.getHead().getName().toLowerCase() + ",["
						+ leaf.getHead().getPreconditions().toLowerCase()+ "])).");
				engine.solve("retract(strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")).");
			}
		}
	}
	public static ArrayList<RecipeTree> FromTreeToProlog(RecipeTree root, Prolog output) throws IOException, InvalidTheoryException{
		int actionsNb =0;
		ArrayList<RecipeTree> prolog_actions = new ArrayList<RecipeTree>();
		for(int i=1; i<root.getLeaves().size(); i++){
			//for(RecipeTree leaf: primitiveTasks){
			RecipeTree leaf= partialroot.getLeaves().get(i);
			if (leaf.getHead().getPostconditions() != null && leaf.getHead().getPreconditions() != null) {
				// --------------- Prolog writing -----------------------------
				//Preconditions 
				actionsNb ++;
				output.addTheory(new Theory("strips_preconditions("
						+ leaf.getHead().getName().toLowerCase() + ",["
						+ leaf.getHead().getPreconditions().toLowerCase()+ "])."));
				output.addTheory(new Theory("strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")."));
				prolog_actions.add(leaf);
				if(!STRIPSconditions.contains(leaf.getHead().getPreconditions()))
					STRIPSconditions.add( leaf.getHead().getPreconditions());
				if(!STRIPSconditions.contains(leaf.getHead().getPostconditions()))
					STRIPSconditions.add( leaf.getHead().getPostconditions());
			}
		}
		for(String recipe : partialroot.existingRecipeConditions){
			output.addTheory(new Theory("strips_preconditions("
					+ recipe.toLowerCase() + ",[])."));
			output.addTheory(new Theory("strips_achieves("
					+ recipe.toLowerCase() + ","
					+ "c"+recipe.toLowerCase()
					+ ")."));

			if(!STRIPSconditions.contains("C"+recipe))
				STRIPSconditions.add("C"+ recipe);
		}
		for (String i : conditions) {
			output.addTheory(new Theory("strips_primitive(" + i.toLowerCase() + ")."));
		}
		strips.write(""+actionsNb +"");
		strips.flush();
		strips.newLine();
		return prolog_actions;

	}
	static BufferedWriter saveSolution(String adresse, boolean write){
		String adressedufichier = System.getProperty("user.dir") + "/prolog/test-2p/recipes/"+adresse;
		PrintWriter writer;
		if(write){
			try {
				writer = new PrintWriter(adressedufichier);
				writer.write("Level NbBreakdwon NbRecover NbCandidates NbRecoveredCandidates");
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw;
		try {
			fw = new FileWriter(adressedufichier, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.newLine();output.flush();
			return output;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public long NumberOfPossibleTree(int depth, int taskBranching, int recipeBranching, int level){
		long mean = 0;
		return mean;
	}
	public static Prolog initSTRIPS(){
		Prolog engine = new Prolog();
		InputStream planner = Discolog.class
				.getResourceAsStream("/test-2p/STRIPS_planner.pl");
		Theory theory;
		try {
			theory = new Theory(planner);
			engine.clearTheory();
			engine.setTheory(theory);
			prolog_actions= FromTreeToProlog(TestClass.partialroot, engine);
		} catch (IOException | InvalidTheoryException e) {
			e.printStackTrace();
		}
		return engine;
	}

	public static  String BreakInit(RecipeTree root, String broken, String init) {
		for(RecipeTree leaf : root.getLeaves()){
			if(leaf.getHead().getName() == broken)
				init += ", "+leaf.getHead().getName() + "=false";	
			else 
				init += ", "+leaf.getHead().getName() + "=true";
			//init += ", exec"+name + "=false";
		}
		return init;
	}

	public static String Init(List<String> coditions, RecipeTree root){

		String init = null;
		Random rand = new Random();
		if(coditions.get(0) =="P1")
			init = "var " + coditions.get(0) +" =true";
		else if(coditions.get(0) =="P2")
			init =  "var "+coditions.get(0)+"=false" ;
		else
			init =  "var "+coditions.get(0)+"=false" ;
		for(int i=1; i<coditions.size() ; i++){
			if(coditions.get(i) =="P1")
				init += ", " + coditions.get(i) +" =true";
			else if(coditions.get(i) =="P2")
				init += ", " + coditions.get(i) +" =false";
			else{
				int cond = rand.nextInt(2);
				if (cond ==1 )
					init += ", " + coditions.get(i) +"= true" ;
				else 
					init += ", " + coditions.get(i) +"= false"  ;
			}
		}
		for(Map.Entry<String, String> recipe :root.getRecipeConditions(recipeConditions).entrySet()){
			int cond= /*rand.nextInt(2)*/ 1;
			if (cond== 1){
				init += ", C" + recipe.getKey() +" =true";
				viableRecipeConditions.add(recipe.getKey());
			}
			else
				init+= ", C"+ recipe.getKey() + "=false";
		}
		return init;
	}
	private static Entry<String, ArrayList<RecipeTree>> GetFirstVialbeRecipe(RecipeTree root){
		for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
				.getChildren().entrySet()) {
			if(viableRecipeConditions.contains(NodeEntry.getKey()))
				return NodeEntry;
		}
		return null;
	}


	public static void getHTNPath (RecipeTree root) {
		if (!root.isLeaf()) {
			// System.out.println(root.toString());
			Map.Entry<String, ArrayList<RecipeTree>> NodeEntry = GetFirstVialbeRecipe(root);
			if(NodeEntry == null){
				Iterable<ArrayList<RecipeTree>> values=root.getChildren().values();
				primitiveTasks.add(values.iterator().next().get(0));}
			else {
				for (RecipeTree child : NodeEntry.getValue()) {
					if(child.isLeaf())
						primitiveTasks.add(child);
					else getHTNPath(child);
				}
			}
		}
	}
}
