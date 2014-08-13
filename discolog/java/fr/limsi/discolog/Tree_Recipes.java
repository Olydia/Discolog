package fr.limsi.discolog;


import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tree_Recipes {

	/**
	 * @param args
	 */
	static int cmpt = 0;

	private Node head;
	//private ArrayList<Tree_Recipes> children;
	private Map<String, ArrayList<Tree_Recipes>> children;
	private Parent Parent;

	public Tree_Recipes(Node head) {
		this.head = head;
	}


	public Tree_Recipes getParent() {
		return Parent.parent;
	}


	public void setParent(Parent parent) {
		Parent = parent;
	}


	@Override
	public String toString() {
		return "Tree_Recipes [head=" + head + "]";
	}


	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		head = head;
	}
/*
	public ArrayList<Tree_Recipes> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Tree_Recipes> sibling) {
		children = sibling;
	}
*/
	// add a child to the current node
/*
	public void addSibling(Tree_Recipes child, String recipe) {
		// checks if it does already exist
		if (this.getChildren() == null) {
			this.setChildren(new HashMap<String, ArrayList<Tree_Recipes>>());
			child.setParent(new Parent(recipe,this));
			//this.getChildren().
			//.add(child);
		} else if (!this.getChildren().contains(child)) {
			child.setParent(new Parent(recipe,this));
			this.getChildren().add(child);
		} else
			System.out.println("The node" + child.getHead().getName()
					+ "is already a sibling of the task"
					+ this.getHead().getName());
	}
*/
	// check if the current node is a leaf (doesn't have children)
	public boolean isLeaf() {
		if (this.getChildren()==null)
			return true;
		else
			return false;
	}

	// return the level or depth of the current node in the tree
	public int getLevel() {
		int level = 0;
		Tree_Recipes p = getParent();
		while (p != null) {
			++level;
			p = p.getParent();
		}
		return level;
	}

	// Function to create a tree

	/*public static void createTree(Tree_Recipes root, int depth, int length,
			int rep, String name) {
		if (depth >= 1) {

			for (int j = 0; j < rep; j++) {
				Map<String, ArrayList<Tree_Recipes>> child_recipe = new HashMap<String, ArrayList<Tree_Recipes>>();
				Tree_Recipes newTreeElem;
				String recipe1 = "C" + cmpt;

				child_recipe.put(recipe1, new ArrayList<Tree_Recipes>());
				root.setChildren(child_recipe);
				// ArrayList<Tree_Recipes> children_root = new
				// ArrayList<Tree_Recipes> ();
				for (int i = 0; i < length; i++) {
					newTreeElem = new Tree_Recipes(new Node("a" + name
							+ (i + 1) + cmpt));
					newTreeElem.setParent(new Parent(recipe1, root));
					root.getChildren().get(recipe1).add(newTreeElem);
					// children_root.add(newTreeElem);
					// root.addSibling(newTreeElem);
					createTree(newTreeElem, depth - 1, length, rep - 1, name
							+ (i + 1));

				}
				cmpt++;
			}
		}
	}*/
	
	public static void createTree(Tree_Recipes root, int depth, int length,
			int rep) {
		if (depth >= 1) {

			for (int j = 0; j < rep; j++) {
				Map<String, ArrayList<Tree_Recipes>> child_recipe = new HashMap<String, ArrayList<Tree_Recipes>>();
				Tree_Recipes newTreeElem;
				String recipe1 = "C" + cmpt;
				child_recipe.put(recipe1,new ArrayList<Tree_Recipes>() );
				
				// ArrayList<Tree_Recipes> children_root = new
				// ArrayList<Tree_Recipes> ();
				for (int i = 0; i < length; i++) {
					newTreeElem = new Tree_Recipes(new Node("a" 
							+ (i + 1) + cmpt));
					newTreeElem.setParent(new Parent(recipe1, root));
					root.getChildren().get(recipe1).add(newTreeElem);
					// children_root.add(newTreeElem);
					// root.addSibling(newTreeElem);
					

				}
				root.setChildren(child_recipe);
				cmpt++;
				createTree(newTreeElem, depth - 1, length, rep - 1);
			}
		}
	}


	public Map<String, ArrayList<Tree_Recipes>> getChildren() {
		return children;
	}

	public void setChildren(Map<String, ArrayList<Tree_Recipes>> children) {
		this.children = children;
	}

	// Printing the tree
	public static void printTree(Tree_Recipes root) {
		System.out.println(root.toString());
		if (!root.isLeaf()) {
			for (String mapKey : root.getChildren().keySet()) {
				for (Tree_Recipes i : root.getChildren().get(mapKey)) {
					System.out.println(mapKey);
					printTree(i);
			}
		}
		}
	}

	// returns all the leaves in the tree by only giving as input a node
/*	public ArrayList<Tree_Recipes> getLeaves() {
		ArrayList<Tree_Recipes> leaves = new ArrayList<Tree_Recipes>();

		if (isLeaf())
			leaves.add(this);
		else {
			for (Tree_Recipes child : this.getChildren())
				leaves.addAll(child.getLeaves());
		}

		return leaves;
	}

	// checks if the current node is the first child of its father (useful for
	// the propagation of the precondition)
	boolean IsFistChild() {
		if (this.getParent().getChildren().indexOf(this) == 0)
			return true;

		return false;
	}

	// checks if the current node is the last child of its father (useful for
	// the propagation of the postcondition)
	boolean IsLastChild() {
		int index = this.getParent().getChildren().size() - 1;
		if ((this.getParent().getChildren().indexOf(this)) == index) {
			return true;
		}
		return false;
	}

	public static void propagatePrecondition(Tree_Recipes root) {
		Tree_Recipes elem = root;
		Tree_Recipes p = elem.getParent();
		while (p != null) {
			if (elem.IsFistChild())
				p.getHead().setPreconditions(elem.getHead().getPreconditions());
			elem = p;
			p = p.getParent();
		}
	}

	public static void propagatePostcondition(Tree_Recipes root) {
		Tree_Recipes elem = root;
		Tree_Recipes p = elem.getParent();
		while (p != null) {
			if (elem.IsLastChild())
				p.getHead().setPostconditions(elem.getHead().getPostconditions());
			elem = p;
			p = p.getParent();
		}
	}

	public static void defineKnowledge(Tree_Recipes root) {
		ArrayList<Tree_Recipes> leafs = root.getLeaves();
		for (int i = 0; i < leafs.size(); i++) {
			leafs.get(i).head.setPreconditions("P" + i);
			leafs.get(i).head.setPostconditions("P" + (i + 1));
			propagatePrecondition(leafs.get(i));
			propagatePostcondition(leafs.get(i));
		}
	}

	public void setLevelOfKnowledg(Tree_Recipes root,int percentage)  {
		
	}
	
public static String Init(Tree_Recipes root){
	ArrayList<Tree_Recipes> leafs = root.getLeaves();
	String init = "var P0 = true ";
	for (int i = 1; i <= leafs.size(); i++) {
		init+= ",P"+i;
		
	}
	return init;
	}*/
	public static void main(String[] args) {
		Node A = new Node("a");
		Tree_Recipes root = new Tree_Recipes(A);
		int depth = 2;
		int length = 3;
		createTree(root, depth, length,2 , "");
		//defineKnowledge(root);
		//System.out.println(cmpt);
		printTree(root);
		
		//System.out.println(Init(root));
		
	}
	
}

