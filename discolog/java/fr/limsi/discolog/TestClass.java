package fr.limsi.discolog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.cetask.TaskClass;

public class TestClass{
	static List<String> recipecondition = new LinkedList<String>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");

	public static TaskClass RECOVERY;
	public static BufferedWriter evaluation;
	public static void main(String[] args) throws IOException {
		//PlanConstructor test = new PlanConstructor();
		ArrayList<Integer> levels = new ArrayList<Integer>();
		BufferedWriter output = null;
		evaluation = saveSolution();
		Node A = new Node("a", "P1", "P2"),
			A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>(),
				copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child),
				partialroot = new RecipeTree(A2, copyChild);
		int depth = 3, 
			length = 2, 
			recipe = 2;
		// Define the complete domain knowledge 
		RecipeTree.DefineCompleteTree(root, depth, length, recipe);
		conditions = root.getKnowledge(root, conditions);
			
		levels.add(25);
		//levels.add(50);	
		//levels.add(75);
		//levels.add(100);
		// Remove knowledge from  the HTN 
		for(int level:levels){
			System.out.println(" \n****************  Test in HTN with knwoledge definition  " +level+ "  ****************************** \n " );
			int cond = RecipeTree.levelOfConditions(depth, length, recipe, level);
			recipecondition=RecipeTree.removeRecipesConditions(RecipeTree.RecipeCondition, level);
			RecipeTree.DefinepartialTree(root, partialroot, cond);
			//RecipeTree.printTree(partialroot);
			evaluation.newLine();evaluation.flush();
			for(int i=0; i<1; i++){
				PlanConstructor test = new PlanConstructor();
				output = test.InitSTRIPSPlanner();
				System.out.println(" \n -------------------------------------- Test number  " +i+ "  --------------------------- \n " );
				TaskClass task = test.FromTreeToTask(partialroot,output);
				test.CreateBenshmark(partialroot, task, output, RecipeTree.RecipeCondition);
				test.LanchTest(task,conditions);
				try {
					test.interaction.join();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.getProperty("line.separator");
		}
		output.close();


	}
	static BufferedWriter saveSolution(){
		String adressedufichier = System.getProperty("user.dir") + "/prolog/test-2p/results.txt";
		/*PrintWriter writer;
		try {
			writer = new PrintWriter(adressedufichier);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
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
		
		// le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
	}

}