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
import java.util.Random;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;
import edu.wpi.cetask.TaskClass;

public class TestClass{
	static List<String> recipecondition = new LinkedList<String>();
	public static List<String> conditions = new LinkedList<String>();
	static int removalcondition = 0;
	public static int NbBreakdown = 0; 
	public static int NbRecover = 0; 
	public static int NbCandidates = 0; 
	public static int NbRecoveredCandidates = 0; 
	public static TaskClass RECOVERY;
	public static BufferedWriter evaluation;
	public static RecipeTree partialroot = null;
	public static Prolog engine = null;
	public static void main(String[] args) throws IOException {
		int LEVEL = 75
				; // 50, 75, 100
		int debut = 1;
		int fin = 50;	

		int 	depth =4, 
				taskBranching = 4, 
				recipeBranching = 1;
		Node A = new Node("a", "P1", "P2"),
				A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		partialroot = new RecipeTree(A2, copyChild);
		// Define the complete domain knowledge 
		RecipeTree.DefineCompleteTree(root, depth, taskBranching, recipeBranching);
		PlanConstructor test = new PlanConstructor();
		TaskClass task = test.FromTreeToTask(root);
		test.CreateBenshmark(root, task);
		test.interaction.start(false);
		//RecipeTree.printTree(root);
		conditions = RecipeTree.getKnowledge(root, conditions);

		for(int i=debut;i<=fin;i++) {
			run(LEVEL,i, root, depth, taskBranching, test, task);
		}
		test.interaction.interrupt();
	}

	public static void run(int level, int numero, RecipeTree root, int depth, int length, PlanConstructor test, TaskClass task) throws IOException {
		String adresse = level +"/"+"test_"+depth+"_"+length+"_"+level+"_"+numero+".txt";
		evaluation = saveSolution(adresse);

		// Remove knowledge from  the HTN 
		RecipeTree.CloneTree(root,  partialroot);
		RecipeTree.PartialTree(partialroot, 100-level);

		//RecipeTree.printTree(partialroot);
		engine = initSTRIPS();

		int Dinit= 100;

		for(int j=0; j< Dinit; j++){
			int z=0;
			String initState = Init(conditions, root);

			for(int i=0; i<partialroot.getLeaves().size(); i++){
				RecipeTree leaf= partialroot.getLeaves().get(i);
				String init = RecipeTree.BreakInit(root, leaf.getHead().getName(), initState);
				System.out.println(level + " - " + numero  + " -  init # "+j + " - break # " + z++);
				test.childTest(task, partialroot, leaf, init);			

				while (test.interaction.getSystem().respond(test.interaction, false, true, false)) {

				}
			 }
		}
		evaluation.write(level +" " +NbBreakdown + " " + NbRecover + " " + NbCandidates + " " + NbRecoveredCandidates);
		evaluation.flush();
		evaluation.newLine();
		evaluation.flush();
		NbBreakdown = 0; NbRecover = 0; NbCandidates =0; NbRecoveredCandidates =0; 

	}

	public static void FromTreeToProlog(RecipeTree root, Prolog output) throws IOException, InvalidTheoryException{
		for(RecipeTree leaf: root.getLeaves()){

			if (leaf.getHead().getPostconditions() != null && leaf.getHead().getPreconditions() != null) {
				// --------------- Prolog writing -----------------------------
				//Preconditions 

				output.addTheory(new Theory("strips_preconditions("
						+ leaf.getHead().getName().toLowerCase() + ",["
						+ leaf.getHead().getPreconditions().toLowerCase()+ "])."));


				output.addTheory(new Theory("strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")."));

			}
		}
		for (String i : conditions) {
			output.addTheory(new Theory("strips_primitive(" + i.toLowerCase() + ")."));

		}
	}

	static BufferedWriter saveSolution(String adresse){
		String adressedufichier = System.getProperty("user.dir") + "/prolog/"+adresse;
		PrintWriter writer;

		try {

			writer = new PrintWriter(adressedufichier);
			writer.write("Level NbBreakdwon NbRecover NbCandidates NbRecoveredCandidates");
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			FromTreeToProlog(TestClass.partialroot, engine);
		} catch (IOException | InvalidTheoryException e) {
			e.printStackTrace();
		}
		return engine;
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
		for(Map.Entry<String, String> recipe :RecipeTree.RecipeCondition.entrySet()){
			int cond = rand.nextInt(2);
			if (cond== 1)
				init += ", " + recipe.getKey() +" =true";
			else
				init+= ","+ recipe.getKey() + "=true";
		}
		return init;
	}

}