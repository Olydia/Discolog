package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cetask.DecompositionClass;
import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskClass;
import edu.wpi.cetask.TaskModel;
import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.DecompositionClass.Step;
import edu.wpi.cetask.TaskClass.Grounding;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import fr.limsi.discolog.Node;

public class Tree {

	/**
	 * @param args
	 */
	static int cmpt = 0;

	private Node head;
	private ArrayList<Tree> children;
	private Tree Parent;

	public Tree(Node head) {
		this.head = head;
	}

	public Tree getParent() {
		return Parent;
	}

	@Override
	public String toString() {
		return "[Head=" + getHead().toString() + ", Parent=" + getParent() + "]";
	}

	public void setParent(Tree parent) {
		Parent = parent;
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		head = head;
	}

	public ArrayList<Tree> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Tree> sibling) {
		children = sibling;
	}

	// add a child to the current node

	public void addSibling(Tree child) {
		// checks if it does already exist
		if (this.getChildren() == null) {
			this.setChildren(new ArrayList<Tree>());
			child.setParent(this);
			this.getChildren().add(child);
		} else if (!this.getChildren().contains(child)) {
			child.setParent(this);
			this.getChildren().add(child);
		} else
			System.out.println("The node" + child.getHead().getName()
					+ "is already a sibling of the task"
					+ this.getHead().getName());
	}

	// check if the current node is a leaf (doesn't have children)
	public boolean isLeaf() {
		if (this.getChildren() == null)
			return true;
		else
			return false;
	}

	// return the level or depth of the current node in the tree
	public int getLevel() {
		int level = 0;
		Tree p = getParent();
		while (p != null) {
			++level;
			p = p.getParent();
		}
		return level;
	}

	// Function to create a tree

	public static void createTree(Tree root, int depth, int length, String name) {
		if (depth >= 1) {
			for (int i = 0; i < length; i++) {
				cmpt++;
				Tree newTreeElem = new Tree(new Node("a" + name + (i + 1)));
				root.addSibling(newTreeElem);
				System.out.println(newTreeElem.toString());
				createTree(newTreeElem, depth - 1, length, name + (i + 1));
			}
		}
	}

	// Printing the tree
	public static void printTree(Tree root) {
		System.out.println(root.toString());
		if (!root.isLeaf()) {
			for (Tree i : root.getChildren()) {
				printTree(i);
			}
		}
	}

	// returns all the leaves in the tree by only giving as input a node
	public ArrayList<Tree> getLeaves() {
		ArrayList<Tree> leaves = new ArrayList<Tree>();

		if (isLeaf())
			leaves.add(this);
		else {
			for (Tree child : this.getChildren())
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

	public static void propagatePrecondition(Tree root) {
		Tree elem = root;
		Tree p = elem.getParent();
		while (p != null) {
			if (elem.IsFistChild())
				p.getHead().setPreconditions(elem.getHead().getPreconditions());
			elem = p;
			p = p.getParent();
		}
	}

	public static void propagatePostcondition(Tree root) {
		Tree elem = root;
		Tree p = elem.getParent();
		while (p != null) {
			if (elem.IsLastChild())
				p.getHead().setPostconditions(elem.getHead().getPostconditions());
			elem = p;
			p = p.getParent();
		}
	}

	public static void defineKnowledge(Tree root) {
		ArrayList<Tree> leafs = root.getLeaves();
		for (int i = 0; i < leafs.size(); i++) {
			leafs.get(i).head.setPreconditions("P" + i);
			leafs.get(i).head.setPostconditions("P" + (i + 1));
			propagatePrecondition(leafs.get(i));
			propagatePostcondition(leafs.get(i));
		}
	}

	public void setLevelOfKnowledg(Tree root,int percentage)  {
		
	}
	
public static String Init(Tree root){
	ArrayList<Tree> leafs = root.getLeaves();
	String init = "var P0 = true ";
	for (int i = 1; i <= leafs.size(); i++) {
		init+= ",P"+i;
		
	}
	return init;
	}
	public static void main(String[] args) {
		Node A = new Node("a");
		Tree root = new Tree(A);
		int depth = 2;
		int length = 3;
		createTree(root, depth, length, "");
		//defineKnowledge(root);
		//System.out.println(cmpt);
		//printTree(root);
		
		//System.out.println(Init(root));
		
	}

}
