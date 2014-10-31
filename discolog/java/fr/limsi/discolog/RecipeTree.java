package fr.limsi.discolog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RecipeTree {

	static int cmpt = 0;
	static int cmp = 3;
	private Node head;
	static List<String> existingCond = new LinkedList<String>();
	static List<String> RecipeCondition = new LinkedList<String>();
	static int removalcondition = 0;
	// private ArrayList<Tree_Recipes> children;
	private Map<String, ArrayList<RecipeTree>> children;
	private RecipeTree Parent;
	public RecipeTree(){

	}
	public RecipeTree(Node head, HashMap<String, ArrayList<RecipeTree>> children) {
		super();
		this.children = children;
		this.head = head;

	}

	@Override
	public String toString() {
		return "tree [head=" + head + "]";
	}

	public static void main(String[] args) {
		Node A = new Node("a", "P1", "P2");
		Node A2 = new Node("a", "P1", "P2");

		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		HashMap<String, ArrayList<RecipeTree>> CopyChild = new HashMap<String, ArrayList<RecipeTree>>();

		RecipeTree root = new RecipeTree(A, child);
		int depth = 3;
		int length = 5;
		int recipe = 2;
		createTree(root, depth, length, recipe);
		defineKnowledge(root);
		System.out.println(root.toString());
		//printTree(root);
		System.out.println("****************************  clonage ****************************************");
		RecipeTree copy = new RecipeTree(A2, CopyChild);
		CloneTree(root,  copy);
		System.out.println(removalcondition);
		//RecipeCondition=removeRecipesConditions(RecipeCondition, 50); 
		PartialTree(copy, 75);
		printTree(copy);
		//test1(root);

	}
	public static void DefinepartialTree (RecipeTree root, RecipeTree patialtree, int levelOfKnowledge){
		//RecipeTree copy = new RecipeTree(A2, CopyChild);
		//cmpt =0; 
		//cmp =3; 
		RecipeTree.CloneTree(root,  patialtree);
		RecipeTree.PartialTree(patialtree, levelOfKnowledge);
	}
	public static void DefineCompleteTree(RecipeTree root, int depth, int length, int recipe/*, int levelOfKnowledge*/){
		RecipeTree.createTree(root, depth, length, recipe);
		RecipeTree.defineKnowledge(root);
	}


	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Map<String, ArrayList<RecipeTree>> getChildren() {
		return children;
	}

	public void setChildren(Map<String, ArrayList<RecipeTree>> child_recipe) {
		this.children = child_recipe;
	}

	public RecipeTree getParent() {
		return Parent;
	}

	public void setParent(RecipeTree parent) {
		Parent = parent;
	}

	public static void createTree(RecipeTree root, int depth, int length,
			int rep) {

		if (depth >= 1) {
			for (int j = 0; j < rep; j++) {
				cmpt++;
				String recipe = "R" + cmpt;
				RecipeCondition.add(recipe);
				root.getChildren().put(recipe, new ArrayList<RecipeTree>());

				int cc = cmpt;
				for (int i = 0; i < length; i++) {
					Node node = new Node("a" + cc+ (i + 1));
					RecipeTree newTreeElem = new RecipeTree(node,new HashMap<String, ArrayList<RecipeTree>>());					
					newTreeElem.setParent(root);
					root.getChildren().get(recipe).add(newTreeElem);
					createTree(newTreeElem,  depth - 1, length, rep);
				}
			}
		}
	}

	public static void printTree(RecipeTree root) {
		if (!root.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				System.out.println(NodeEntry.getKey());
				for (RecipeTree i : NodeEntry.getValue()) {
					System.out.println(i.toString() + "    ");
					printTree(i);
				}
			}
		}
	}


	public static void CloneTree(RecipeTree root, RecipeTree clone) {
		if (!root.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				clone.getChildren().put(NodeEntry.getKey(), new ArrayList<RecipeTree>());
				for (RecipeTree i : NodeEntry.getValue()) {
					Node node = new Node(i.getHead().getName(),i.getHead().getPreconditions(),i.getHead().getPostconditions(),i.getHead().getGrounding());
					RecipeTree newTreeElem = new RecipeTree(node,new HashMap<String, ArrayList<RecipeTree>>());
					newTreeElem.setParent(clone);
					clone.getChildren().get(NodeEntry.getKey()).add(newTreeElem);	
					CloneTree(i, newTreeElem);
				}
			}
		}
	}

	public static List<String> removeRecipesConditions(List<String> recipeCondition, int level){
		int level1=Math.round((recipeCondition.size()*(100-level))/100);
		for(int i=0;i<level1 && recipeCondition.size()>0;i++){
			java.util.Collections.shuffle(recipeCondition);
			recipeCondition.remove(0);
		}
		return recipeCondition;
	}
	public static void PartialTree(RecipeTree root, int level) {
		Random rand = new Random();

		if (!root.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root.getChildren().entrySet()) {
				for (RecipeTree currentNode : NodeEntry.getValue()) {
					int precondition = rand.nextInt(100);
					if(precondition <level){
						currentNode.head.setPreconditions(null);
					}

					int postcondition = rand.nextInt(100);
					if(postcondition <level){
						currentNode.head.setPostconditions(null);
					}

					PartialTree(currentNode, level);
				}
			}

		}
	}

	//	public static void PartialTree(RecipeTree root, int removalcondition) {
	//		int removal = removalcondition;
	//		 while (removal > 0) 
	//			removal = RemoveConditions(root);
	//	}
	//	
	public static int RemoveConditions(RecipeTree root){
		Random rand = new Random();

		if (!root.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root.getChildren().entrySet()) {
				for (RecipeTree currentNode : NodeEntry.getValue()) {
					if (removalcondition == 0)
						return 0;
					int precondition = rand.nextInt(2);
					if(precondition == 1 && currentNode.head.getPreconditions()!=null){
						currentNode.head.setPreconditions(null);
						removalcondition --;
					}
					if (removalcondition == 0)
						return 0;
					int postcondition = rand.nextInt(2);
					if(postcondition == 1 && currentNode.head.getPostconditions()!=null){
						currentNode.head.setPostconditions(null);
						removalcondition --;	
					}

					// System.out.println(currentNode.toString());
					RemoveConditions(currentNode);
				}

			}
		}
		return removalcondition;
	}

	boolean isLeaf() {
		boolean value = false;
		if (this.getChildren().isEmpty())
			value = true;
		return value;
	}

	// returns all the leaves in the tree by only giving as input a node
	public ArrayList<RecipeTree> getLeaves() {
		ArrayList<RecipeTree> leaves = new ArrayList<RecipeTree>();

		if (this.isLeaf())
			leaves.add(this);
		else {
			for (Map.Entry<String, ArrayList<RecipeTree>> child : this
					.getChildren().entrySet()) {
				for (RecipeTree i : child.getValue()) {
					leaves.addAll(i.getLeaves());
				}
			}
		}
		return leaves;
	}

	public static void propagatePrecondition(RecipeTree root) {

		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree i : NodeEntry.getValue()) {
					if (NodeEntry.getValue().indexOf(i) == 0) {
						i.getHead().setPreconditions(
								root.getHead().getPreconditions());

						propagatePrecondition(i);
					}
				}
			}
		}
	}

	public boolean IsFirstChild(String key){
		if(this.getParent().getChildren().get(key).get(0).equals(this))
			return true;	
		return false;
	}

	public static void propagatePostcondition(RecipeTree root) {
		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree i : NodeEntry.getValue()) {
					if (NodeEntry.getValue().indexOf(i) == NodeEntry.getValue()
							.size() - 1) {
						i.getHead().setPostconditions(
								root.getHead().getPostconditions());
						propagatePostcondition(i);
					}
				}
			}
		}
	}

	public static void DefineCondition(RecipeTree root) {

		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {

				for (int i = 0; i < NodeEntry.getValue().size(); i++) {
					if (NodeEntry.getValue().get(i).getHead().getPostconditions() == null) {
						NodeEntry.getValue().get(i).getHead().setPostconditions("P" + cmp);
						cmp++;
						propagatePostcondition(NodeEntry.getValue().get(i));
						if (NodeEntry.getValue().get(i + 1).getHead().getPreconditions() == null) {
							NodeEntry.getValue().get(i + 1).getHead().setPreconditions(
									NodeEntry.getValue().get(i).getHead().getPostconditions());
							propagatePrecondition(NodeEntry.getValue().get(i + 1));
						}
					}
					if (NodeEntry.getValue().get(i).getHead().getPreconditions() == null) {
						NodeEntry.getValue().get(i).getHead().setPreconditions("P" + cmp);
						cmp++;
						propagatePrecondition(NodeEntry.getValue().get(i));
						if (NodeEntry.getValue().get(i - 1).getHead().getPostconditions() == null) {
							NodeEntry.getValue().get(i - 1).getHead().setPostconditions(
									NodeEntry.getValue().get(i).getHead().getPreconditions());
							propagatePostcondition(NodeEntry.getValue().get(i - 1));
						}
					}
					DefineCondition(NodeEntry.getValue().get(i));

				}
			}
		}
	}
	public static int levelOfConditions(int depth, int length, int recipe, int percentageKnowledge){
		int knowledge=0;
		percentageKnowledge = 100 - percentageKnowledge;

		for(int i= 0;i<=depth; i++)

			knowledge+= Math.pow(length*recipe, i);
		knowledge = knowledge*2;
		//for(int i= 0;i<depth; i++)
		//	knowledge+= (recipe*Math.pow(length*recipe, i));
		knowledge = (knowledge*percentageKnowledge)/100;
		return knowledge;

	}

	public static List<String> LevelOfKnowledge(RecipeTree root, int level) {
		List<String> conditions =new LinkedList<String>();
		ArrayList<String>  cond = new ArrayList<String>();
		cond.add("P1"); cond.add("P2"); 
		conditions=root.getKnowledge(root, conditions);
		int level1=Math.round((conditions.size()*(100-level))/100);
		for(int i=0;i<level1 && conditions.size()>0;i++){
			java.util.Collections.shuffle(conditions);
			if(!cond.contains(conditions.get(0)))
				conditions.remove(0);
		}
		return conditions;
	}

	public List<String> getKnowledge(RecipeTree root,List<String> conditions) {
		if (!root.isLeaf()) {

			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {

				for (RecipeTree i : NodeEntry.getValue()) {

					if(!conditions.contains(i.getHead().getPreconditions()))
						conditions.add(i.getHead().getPreconditions());

					if(!conditions.contains(i.getHead().getPostconditions()))
						conditions.add(i.getHead().getPostconditions());

					getKnowledge(i,conditions);
				}
			}
		}
		return conditions;
	}

	public static void DefineLevelOfKnowledge(RecipeTree root,List<String> existingCond) {

		if(!existingCond.contains(root.getHead().getPreconditions()))
			root.getHead().setPreconditions(null);

		if(!existingCond.contains(root.getHead().getPostconditions()))
			root.getHead().setPostconditions(null);

		if (!root.isLeaf()) {

			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {

				for (RecipeTree i : NodeEntry.getValue()) {

					DefineLevelOfKnowledge(i,existingCond);
				}
			}
		}
	}

	public static void defineKnowledge(RecipeTree root) {

		propagatePrecondition(root);
		propagatePostcondition(root);
		DefineCondition(root);
		generateGrounding(root);

	}

	public static void generateGrounding( RecipeTree root){

		ArrayList<RecipeTree> leaves = root.getLeaves();

		for(RecipeTree leaf: leaves){

			leaf.getHead().defineGrounding();
		}
	}
	/*public List<String> getRecipeConditions(RecipeTree root, List<String> RecipeConditions){
		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				RecipeConditions.add("C"+NodeEntry.getKey());
				for (RecipeTree i : NodeEntry.getValue()) 
					getRecipeConditions(i, RecipeConditions);
			}
		}
		return RecipeConditions;
	}*/

	public static String Init(List<String> coditions, RecipeTree root, String broken){
		Random rand = new Random();
		String init = null;
		int random = rand.nextInt(2);
		if(coditions.get(0) =="P1")
			init = "var " + coditions.get(0) +" =true";
		else
			init =  random ==1?  "var "+coditions.get(0)+"" : "var "+coditions.get(0)+"=false" ;
		for(int i=1; i<coditions.size() ; i++){
			if(coditions.get(i) =="P1")
				init += ", " + coditions.get(i) +" =true";

			else{
				int nombreAleatoire = rand.nextInt(2);
				init += nombreAleatoire==1? ", " + coditions.get(i) +"":", " + coditions.get(i) +"=false";
			}
		}
		for(String recipe : RecipeTree.RecipeCondition)
			init += ", C" + recipe +" =true";

		for(RecipeTree leaf : root.getLeaves()){
			if(leaf.getHead().getName() == broken)
				init += ", "+leaf.getHead().getName() + "=false";	
			else 
				init += ", "+leaf.getHead().getName() + "=true";
			//init += ", exec"+name + "=false";
		}

		return init;
	}
//	public static void createBreakdown(RecipeTree node){
//		node.getHead().getGrounding().set(2, "false");
//	}
//
//
//	public static void removeBreakdown(RecipeTree node){
//		node.getHead().getGrounding().set(2, "true");
//	}

	public static void test1 (RecipeTree root) {
		if(!root.isLeaf()){
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree node : NodeEntry.getValue()) {
					String test = test(node);
					if(test !=null)
						System.out.println(test);
					test1(node);
				}
			}
		}
	}

	public static String test(RecipeTree root){
		String returne= null;
		if (root.isLeaf()){
			//if (root.getHead().getPostconditions() != null) {
			returne = "	\n "+root.getHead().getName()+"  Grounding Script : \n     ";

			if(root.getHead().getGrounding().get(2) == "true"){
				returne += "if ("+root.getHead().getGrounding().get(0)+ "!=false) {"
						+root.getHead().getGrounding().get(1) +" =true; "
						+ "println('"+ root.getHead().getName() + "');}"
						+ " else {$this.sucess = false;} \n";
			}else {
				//Create breakdown 

				// preconditions are not false
				returne += "if ("+root.getHead().getGrounding().get(0)+ "==false) {"+	
						"$this.sucess = false;} "+
						"else if ("+root.getHead().getName()+" == false) {"+
						// psotconditions put to false and change the flag to true
						root.getHead().getGrounding().get(1).toString()+ "=false; println('"
						+ root.getHead().getName() + "  "+ root.getHead().getGrounding().get(1).toString() +" =false '); "
						+root.getHead().getName()+ "=true;}"
						// else if not the first run put the postcond to true
						+ "else { "+root.getHead().getGrounding().get(1)+ "=true; println('"
						+ root.getHead().getName() + "   "+ root.getHead().getGrounding().get(1) +"');}\n";
			}
		}

		return returne;
	}
}