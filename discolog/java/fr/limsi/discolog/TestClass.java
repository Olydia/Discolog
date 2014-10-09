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
		Node A = new Node("a", "P1", "P2");
		Node A2 = new Node(A.getName(), A.getPreconditions(), A.getPostconditions());
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		HashMap<String, ArrayList<RecipeTree>> copyChild = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		
		DefineCompleteTree(root,2, 2, 2);
		
		//levels.add(0);
		levels.add(50);
		//levels.add(75);
		//levels.add(100);
		for(int level:levels){
		System.out.println(" \n****************  Test in HTN with knwoledge definition  " +level+ "  ****************************** \n " );
		RecipeTree partialroot = new RecipeTree(A2, copyChild);
		DefinepartialTree(root,partialroot, level);
		PlanConstructor test = new PlanConstructor();
		TaskClass task = test.FromTreeToTask(root,output);
		conditions = root.getKnowledge(root, conditions);
		test.CreateBenshmark(root, task, output, RecipeTree.RecipeCondition);
		output = test.InitSTRIPSPlanner();
		for(int i=0; i<1; i++){
			System.out.println(" \n***************************  Test number  " +i+ "  ****************************** \n " );
			test.LanchTest(task,conditions);
			try {
				test.interaction.join();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		output.close();
		}

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
	
	public static void DefinepartialTree (RecipeTree root, RecipeTree patialtree, int levelOfKnowledge){
		//RecipeTree copy = new RecipeTree(A2, CopyChild);
		RecipeTree.CloneTree(root,  patialtree);
		RecipeTree.PartialTree(patialtree, levelOfKnowledge);
	}
	static void DefineCompleteTree(RecipeTree root, int depth, int length, int recipe/*, int levelOfKnowledge*/){
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
	}
}