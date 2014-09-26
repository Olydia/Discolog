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

import edu.wpi.cetask.TaskClass;

public class TestClass{
	static ArrayList<String> recipecondition = new ArrayList<String>();
	public static List<String> conditions = new LinkedList<String>();
	//public static List<String> conditions = Arrays.asList("P1","CR1","CR2","P3","P2","P4");

	public static TaskClass RECOVERY;
	public static BufferedWriter evaluation;
	public static void main(String[] args) throws IOException {
		//PlanConstructor test = new PlanConstructor();
		ArrayList<Integer> levels = new ArrayList<Integer>();
		BufferedWriter output = null;
	
		evaluation = saveSolution();

		levels.add(0);
	 	//levels.add(50);
		//levels.add(75);
		//levels.add(100);
		for(int level:levels){
		System.out.println(" \n***************************  Test in HTN with knwoledge definition  " +level+ "  ****************************** \n " );
		RecipeTree root = DefineTree(2, 4, 3,level);
		for(int i=0; i< 100; i++){
			System.out.println(" \n***************************  Test number  " +i+ "  ****************************** \n " );
			PlanConstructor test = new PlanConstructor();
			output = test.InitSTRIPSPlanner();
			test.LanchTest(root, output,recipecondition,conditions);
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
	static RecipeTree DefineTree(int depth, int length, int recipe, int levelOfKnowledge){
		Node A = new Node("a", "P1", "P2");
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
		conditions = RecipeTree.LevelOfKnowledge(root, levelOfKnowledge);
		RecipeTree.DefineLevelOfKnowledge(root, conditions);
		return root;
	}
}