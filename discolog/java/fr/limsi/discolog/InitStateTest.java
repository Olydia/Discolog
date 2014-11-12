package fr.limsi.discolog;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;
import edu.wpi.cetask.TaskClass;

public class InitStateTest {
	static List<String> recipecondition = new LinkedList<String>();
	public static List<String> HTNconditions = new LinkedList<String>();
	static int removalcondition = 0;
	public static int NbBreakdown = 0; 
	public static int NbRecover = 0; 
	public static int NbCandidates = 0; 
	public static int NbRecoveredCandidates = 0; 
	public static TaskClass RECOVERY;
	public static BufferedWriter evaluation;
	public static RecipeTree partialroot = null;
	public static Prolog PrologEngine = null;
	public static void main(String[] args) throws IOException {
		int LEVEL = 100
				; // 50, 75, 100

		int 	depth =2, 
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
		// to print the tree 
		RecipeTree.printTree(root);

		// Create a new instance of Planconstructor 
		PlanConstructor test = new PlanConstructor();

		// Transform the root of the tree to a compound task 
		TaskClass task = test.FromTreeToTask(root);
		// Create the HTN from the tree
		test.CreateBenshmark(root, task);

		// Strat thr interaction 
		test.interaction.start(false);

		// Get all the defined conditions in the tree for the initial state and the current state in case of breakdown
		HTNconditions = RecipeTree.getKnowledge(root, HTNconditions);

		run(LEVEL,1, root, depth, taskBranching, test, task);
		test.interaction.interrupt();
	}

	public static void run(int level, int numero, RecipeTree root, int depth, int length, PlanConstructor test, TaskClass task) throws IOException {

		// Remove knowledge from  the tree 
		RecipeTree.CloneTree(root,  partialroot);
		RecipeTree.PartialTree(partialroot, 100-level);

		//RecipeTree.printTree(partialroot);
		//  Define the declarative domain knowledge 
		PrologEngine = initSTRIPS();

		// Define the initial state 
		// To put all the conditions to false define value = false
		// [ p1 is always set to true and P2 always to false]
		// to put all the conditions to false define value = true 
		String value = "true";
		String initState = TestClass.Init(HTNconditions, root, value);

		// to test all the leaves in the HTN uncomment the loop 

		//for(int i=0; i<partialroot.getLeaves().size(); i++){

		RecipeTree leaf= partialroot.getLeaves().get(partialroot.getLeaves().size()-2);

		String init = RecipeTree.BreakInit(root, leaf.getHead().getName(), initState);
		// This method create a new instance of the root task to be planned
		test.childTest(task, partialroot, leaf, init);			

		while (test.interaction.getSystem().respond(test.interaction, false, true, false)) {
			//test.disco.wait();
			//System.out.println("waiting");
		}

	}

	public static void FromTreeToProlog(RecipeTree root, Prolog output) throws IOException, InvalidTheoryException{
		for(RecipeTree leaf: root.getLeaves()){

			if (leaf.getHead().getPostconditions() != null && leaf.getHead().getPreconditions() != null) {
				// --------------- Prolog writing -----------------------------

				output.addTheory(new Theory("strips_preconditions("
						+ leaf.getHead().getName().toLowerCase() + ",["
						+ leaf.getHead().getPreconditions().toLowerCase()+ "])."));

				output.addTheory(new Theory("strips_achieves("
						+ leaf.getHead().getName().toLowerCase() + ","
						+ leaf.getHead().getPostconditions().toLowerCase()
						+ ")."));

			}
		}
		for (String i : HTNconditions) {
			output.addTheory(new Theory("strips_primitive(" + i.toLowerCase() + ")."));

		}
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
			FromTreeToProlog(partialroot, engine);
		} catch (IOException | InvalidTheoryException e) {
			e.printStackTrace();
		}
		return engine;
	}




}
