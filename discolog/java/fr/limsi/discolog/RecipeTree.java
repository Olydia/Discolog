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
	List<String> existingRecipeConditions = new ArrayList<String>();
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

//	public static void main(String[] args) {
//		Node A = new Node("a", "P1", "P2");
//		Node A2 = new Node("a", "P1", "P2");
//
//		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
//		HashMap<String, ArrayList<RecipeTree>> CopyChild = new HashMap<String, ArrayList<RecipeTree>>();
//
//		RecipeTree root = new RecipeTree(A, child);
//		int depth = 2;
//		int length = 2;
//		int recipe = 2;
//		int level =50;
//		root.createTree(depth, length, recipe);
//		defineKnowledge(root);
//		//existingCond= root.getKnowledge(existingCond);
//	//	System.out.println(/*Math.round(Math.pow(2,*/ existingCond + "\n" + Math.pow(2, existingCond.size()) );//)*0.1));
//		//printTree(root);
//		//System.out.println(levelOfConditions(depth, length, recipe));
//		//		//System.out.println(CombinaisonOfTrees(depth, length, recipe, level));
//				System.out.println("****************************  clonage ****************************************");
//				RecipeTree copy = new RecipeTree(A2, CopyChild);
//				root.CloneTree(copy);
//				System.out.println(removalcondition);
//				//RecipeCondition=removeRecipesConditions(RecipeCondition, 50); 
//				copy.GeneratePartialTree(100-level);
//			//	System.out.println(RecipeConditions.toString());
//				//printTree(copy);
//		//		//		//test1(root);
//
//	}
//	private static float CombinaisonOfTrees(int depth, int length, int recipe,
//			int level) {
//		int partialconditions =0;
//		int conditions= levelOfConditions(depth, length, recipe);
//		if(level != 100)
//			partialconditions = Math.round(conditions * (100-level)/100); 
//		else 
//			partialconditions = conditions;
//		//		System.out.println( fact(conditions) + "  "+
//		//				fact(partialconditions) + "  "+fact(conditions - partialconditions));
//		float nbDiffrentTrees = Math.round( fact(conditions) /
//				(fact(partialconditions) * fact(conditions - partialconditions)));
//		return nbDiffrentTrees;
//	}

	public  float  fact(int n) {
		float res =0; 
		if(n == 1 || n == 0){
			return 1;
		}
		res = n * (fact(n-1)); 
		return res ;
	}
	void DefinepartialTree (RecipeTree patialtree, int levelOfKnowledge){

		this.CloneTree(patialtree);
		patialtree.GeneratePartialTree(levelOfKnowledge);
	}
	public void DefineCompleteTree(int depth, int length, int recipe/*, int levelOfKnowledge*/){
		this.createTree(depth, length, recipe);
		this.defineKnowledge();
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

	private void createTree(int depth, int length, int rep) {

		if (depth >= 1) {
			for (int j = 0; j < rep; j++) {
				cmpt++;
				String recipe = "R" + cmpt;
				//changer ajouter le rooot.preconditions 
				this.getChildren().put(recipe, new ArrayList<RecipeTree>());
				int conditioncount = cmpt;
				for (int i = 0; i < length; i++) {
					Node node = new Node("a" + conditioncount+ (i + 1));
					RecipeTree newTreeElem = new RecipeTree(node,new HashMap<String, ArrayList<RecipeTree>>());					
					newTreeElem.setParent(this);
					this.getChildren().get(recipe).add(newTreeElem);
					newTreeElem.createTree(depth - 1, length, rep);
				}
			}
		}
	}

	public  void printTree() {
		if (!this.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : this
					.getChildren().entrySet()) {
				System.out.println(NodeEntry.getKey());
				for (RecipeTree i : NodeEntry.getValue()) {
					System.out.println(i.toString() + "    ");
					i.printTree();
				}
			}
		}
	}


	private void CloneTree(RecipeTree clone) {
		if (!this.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : this
					.getChildren().entrySet()) {
				clone.getChildren().put(NodeEntry.getKey(), new ArrayList<RecipeTree>());
				for (RecipeTree i : NodeEntry.getValue()) {
					Node node = new Node(i.getHead().getName(),i.getHead().getPreconditions(),i.getHead().getPostconditions(),i.getHead().getGrounding());
					RecipeTree newTreeElem = new RecipeTree(node,new HashMap<String, ArrayList<RecipeTree>>());
					newTreeElem.setParent(clone);
					clone.getChildren().get(NodeEntry.getKey()).add(newTreeElem);	
					i.CloneTree(newTreeElem);
				}
			}
		}
	}

	public  List<String> removeRecipesConditions(int level){
		HashMap<String,String> recipeCondition = new HashMap<String,String> ();
		this.getRecipeConditions(recipeCondition);
		List<String> ViableConditions = new ArrayList<String>();
		Random rand = new Random();
		for (Map.Entry<String, String> i :recipeCondition.entrySet()) {
			int condition = rand.nextInt(100);
			if(condition >level)
				ViableConditions.add(i.getKey().toString());
		}
		return ViableConditions;
	}


	public void GeneratePartialTree(int level) {
		Random rand = new Random();
		existingRecipeConditions= removeRecipesConditions(level);
		if (!this.isLeaf()) {
			// System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : this.getChildren().entrySet()) {
				for (RecipeTree currentNode : NodeEntry.getValue()) {
					int precondition = rand.nextInt(100);
					if(precondition <level){
						currentNode.head.setPreconditions(null);
					}

					int postcondition = rand.nextInt(100);
					if(postcondition <level){
						currentNode.head.setPostconditions(null);
					}

					currentNode.GeneratePartialTree(level);
				}
			}
		}
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

	public  void propagatePrecondition(RecipeTree root) {

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

	public  void propagatePostcondition(RecipeTree root) {
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

	public  void DefineCondition(RecipeTree root) {

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
	public static int levelOfConditions(int depth, int length, int recipe){
		int knowledge=0;		
		for(int i= 0;i<=depth; i++)
			knowledge+= Math.pow(length*recipe, i);
		knowledge = knowledge*2;
		//for(int i= 0;i<depth; i++)
		//	knowledge+= (recipe*Math.pow(length*recipe, i));
		//	knowledge = (knowledge*percentageKnowledge)/100;
		return knowledge;

	}

	public static List<String> LevelOfKnowledge(RecipeTree root, int level) {
		List<String> conditions =new LinkedList<String>();
		ArrayList<String>  cond = new ArrayList<String>();
		cond.add("P1"); cond.add("P2"); 
		conditions=root.getKnowledge(conditions);
		int level1=Math.round((conditions.size()*(100-level))/100);
		for(int i=0;i<level1 && conditions.size()>0;i++){
			java.util.Collections.shuffle(conditions);
			if(!cond.contains(conditions.get(0)))
				conditions.remove(0);
		}
		return conditions;
	}

	public  List<String> getKnowledge(List<String> conditions) {

		if (!this.isLeaf()) {

			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : this
					.getChildren().entrySet()) {

				for (RecipeTree i : NodeEntry.getValue()) {

					if(!conditions.contains(i.getHead().getPreconditions()))
						conditions.add(i.getHead().getPreconditions());

					if(!conditions.contains(i.getHead().getPostconditions()))
						conditions.add(i.getHead().getPostconditions());

					i.getKnowledge(conditions);
				}
			}
		}
		return conditions;
	}

	public  HashMap<String, String> getRecipeConditions (HashMap<String, String> RecipeConditions) {
	
		if (!this.isLeaf()) {

			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : this
					.getChildren().entrySet()) {

				RecipeConditions.put( NodeEntry.getKey().toString(), this.getHead().getPreconditions());
				for (RecipeTree i : NodeEntry.getValue()) {

					i.getRecipeConditions(RecipeConditions);
				}
			}
		}
		return RecipeConditions;
	}


	public  void defineKnowledge() {

		propagatePrecondition(this);
		propagatePostcondition(this);
		DefineCondition(this);
		generateGrounding(this);

	}

	public  void generateGrounding( RecipeTree root){

		ArrayList<RecipeTree> leaves = root.getLeaves();

		for(RecipeTree leaf: leaves){

			leaf.getHead().defineGrounding();
		}
	}




	// function to update the flag = false in order to cause a breakdown in the "broken" task
	

}