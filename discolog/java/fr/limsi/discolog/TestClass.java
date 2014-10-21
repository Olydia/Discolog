package fr.limsi.discolog;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	public static void main(String[] args) throws IOException {
		//PlanConstructor test = new PlanConstructor();
		ArrayList<Integer> levels = new ArrayList<Integer>();
		//BufferedWriter output = null;
		evaluation = saveSolution();
		Node A = new Node("a", "P1", "P2"),
				A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		partialroot = new RecipeTree(A2, copyChild);
		int 	depth = 2, 
				length = 2, 
				recipe = 1;
		// Define the complete domain knowledge 
		RecipeTree.DefineCompleteTree(root, depth, length, recipe);
		//	RecipeTree.printTree(root);
		conditions = root.getKnowledge(root, conditions);
		levels.add(25);
		//levels.add(50);
		//levels.add(75);
		//levels.add(100);

		// Remove knowledge from  the HTN 
		for(int level:levels){
			System.out.println(" \n****************  Test in HTN with knwoledge definition  " +level+ "  ****************************** \n " );
			RecipeTree.CloneTree(root,  partialroot);
			RecipeTree.removalcondition = RecipeTree.levelOfConditions(depth, length, recipe, level);
			//System.out.println(removalcondition);
			for(int i =0; i<1; i++){
				RecipeTree.PartialTree(partialroot, RecipeTree.removalcondition);
				
			//	for(RecipeTree leaf: partialroot.getLeaves()){
					
					System.out.println(" \n -------------------------------------- Test primitive "/*+leaf.getHead().getName()*/+"    --------------------------- \n " );
					RecipeTree leaf =  partialroot.getLeaves().get(0);
					RecipeTree.createBreakdown(leaf);
					RecipeTree.printTree(partialroot);
					PlanConstructor test = new PlanConstructor();
					TaskClass task = test.FromTreeToTask(partialroot);
					test.CreateBenshmark(partialroot, task);
					//FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/prolog/test-2p/Domain_knowledge.pl");
					test.LanchTest(task,conditions, partialroot);
					
					try {
						//test.interaction.getDisco().history(System.out);
						test.interaction.join();
						System.out.println("end of execution");
						test.interaction.getDisco().history(System.out);


					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RecipeTree.removeBreakdown(leaf);

				//}
				evaluation.write(level +" " +NbBreakdown + " " + NbRecover + " " + NbCandidates + " " + NbRecoveredCandidates);
				evaluation.flush();
				evaluation.newLine();
				evaluation.flush();
				NbBreakdown = 0; NbRecover = 0; NbCandidates =0; NbRecoveredCandidates =0; 
			}	
		}


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

	static BufferedWriter saveSolution(){
		String adressedufichier = System.getProperty("user.dir") + "/prolog/test-2p/results1.txt";
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

}