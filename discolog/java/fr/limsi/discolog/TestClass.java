package fr.limsi.discolog;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
		int LEVEL = 75; // 50, 75, 100
		int debut = 1;
		int fin = 1;	
		int 	depth = 1, 
				taskBranching = 2, 
				recipeBranching = 1;
		Node A = new Node("a", "P1", "P2"),
				A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		partialroot = new RecipeTree(A2, copyChild);
		// Define the complete domain knowledge 
		RecipeTree.DefineCompleteTree(root, depth, taskBranching, recipeBranching);

		//	RecipeTree.printTree(root);
		conditions = root.getKnowledge(root, conditions);
		for(int i=debut;i<=fin;i++) {
			run(LEVEL,i, root, depth, taskBranching);
		}
	}

	public static void run(int level, int numero, RecipeTree root, int depth, int length) throws IOException {
		String adresse = "test_"+depth+"_"+length+"_"+level+"_"+numero+".txt";
		evaluation = saveSolution(adresse);
		// Remove knowledge from  the HTN 
		RecipeTree.CloneTree(root,  partialroot);
		RecipeTree.PartialTree(partialroot, 100-level);
		//RecipeTree.printTree(partialroot);
		engine = initSTRIPS();
		int z=0;
		PlanConstructor test = new PlanConstructor();
		TaskClass task = test.FromTreeToTask(partialroot);
		//long lStartTheory = System.currentTimeMillis();
		test.CreateBenshmark(partialroot, task);
		test.interaction.start();
		for(int i=0; i<partialroot.getLeaves().size(); i++){
			RecipeTree leaf= partialroot.getLeaves().get(i);
			System.out.println(level + " - " + numero + " - break #" + z++);
			test.childTest(task, conditions, partialroot, leaf);			
			while (test.interaction.getSystem().respond(test.interaction, false, true, false)) {
				try {
					test.disco.wait();
					System.out.println("waiting");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		evaluation.write(level +" " +NbBreakdown + " " + NbRecover + " " + NbCandidates + " " + NbRecoveredCandidates);
		evaluation.flush();
		evaluation.newLine();
		evaluation.flush();
		NbBreakdown = 0; NbRecover = 0; NbCandidates =0; NbRecoveredCandidates =0; 
		
		test.interaction.interrupt();

	}
	
	public static void FromTreeToProlog(RecipeTree root, Prolog output) throws IOException, InvalidTheoryException{
		for(RecipeTree leaf: root.getLeaves()){

			if (leaf.getHead().getPostconditions() != null) {
				// --------------- Prolog writing -----------------------------
				//Preconditions 

				if (leaf.getHead().getPreconditions() != null) {
					output.addTheory(new Theory("strips_preconditions("
							+ leaf.getHead().getName().toLowerCase() + ",["
							+ leaf.getHead().getPreconditions().toLowerCase()+ "])."));

				}
				else {
					output.addTheory(new Theory("strips_preconditions("
							+ leaf.getHead().getName().toLowerCase() + ",[_])."));
				}

				output.addTheory(new Theory("strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")."));


				// --------------- Prolog writing -----------------------------
			}
		}
		for (String i : conditions) {
			output.addTheory(new Theory("strips_primitive(" + i.toLowerCase() + ")."));

		}
		for (String recipe : RecipeTree.RecipeCondition) {
			output.addTheory(new Theory("strips_preconditions(" + recipe.toLowerCase() + ",[_])."));

			output.addTheory(new Theory("strips_achieves(" + recipe.toLowerCase() + ",c"
					+ recipe.toLowerCase() + ")."));

			output.addTheory(new Theory("strips_primitive(c" + recipe.toLowerCase() + ")."));

		}
	}

	static BufferedWriter saveSolution(String adresse){
		String adressedufichier = System.getProperty("user.dir") + "/prolog/test-2p/Test_Results/"+adresse;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public long NumberOfPossibleTree(int depth, int taskBranching, int recipeBranching, int level){
		long mean = 0;
		return mean;
	}
	 public static int  fact(int n) {
	        if(n == 1){
	            return n;
	        }
	        return n * (fact(n-1)); // what happens if you switch the order?
	    }

	public static Prolog initSTRIPS(){
		Prolog engine = new Prolog();
		//long lStartTheory = new Date().getTime();
		InputStream planner = Discolog.class
				.getResourceAsStream("/test-2p/STRIPS_planner.pl");
		Theory theory;
		try {
			theory = new Theory(planner);
			engine.clearTheory();
			engine.setTheory(theory);
			FromTreeToProlog(TestClass.partialroot, engine);
			
		} catch (IOException | InvalidTheoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return engine;
	}


}