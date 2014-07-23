package fr.limsi.discolog;

import java.util.ArrayList;
import fr.limsi.discolog.Node;

public class Tree {

	/**
	 * @param args
	 */
	static int cmpt = 0;

	private Node Head;
	private ArrayList<Tree> Sibling;
	private Tree Parent;

	public Tree(Node head) {
		this.Head = head;
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
		return Head;
	}

	public void setHead(Node head) {
		Head = head;
	}

	public ArrayList<Tree> getSibling() {
		return Sibling;
	}

	public void setSibling(ArrayList<Tree> sibling) {
		Sibling = sibling;
	}

	// add a child to the current node

	public void addSibling(Tree child) {
		// checks if it does already exist
		if (this.getSibling() == null) {
			this.setSibling(new ArrayList<Tree>());
			child.setParent(this);
			this.getSibling().add(child);
		} else if (!this.getSibling().contains(child)) {
			child.setParent(this);
			this.getSibling().add(child);
		} else
			System.out.println("The node" + child.getHead().getName()
					+ "is already a sibling of the task"
					+ this.getHead().getName());
	}

	// check if the current node is a leaf (doesn't have children)
	public boolean isLeaf() {
		if (this.getSibling() == null)
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
				Tree newTreeElem = new Tree(new Node("A" + name + (i + 1)));
				root.addSibling(newTreeElem);
				createTree(newTreeElem, depth - 1, length, name + (i + 1));
			}
		}
	}

	// Printing the tree
	public static void printTree(Tree root) {
		System.out.println(root.toString());
		if (!root.isLeaf()) {
			for (Tree i : root.getSibling()) {
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
			for (Tree child : this.getSibling())
				leaves.addAll(child.getLeaves());
		}

		return leaves;
	}

	// checks if the current node is the first child of its father (useful for
	// the propagation of the precondition)
	boolean IsFistChild() {
		if (this.getParent().getSibling().indexOf(this) == 0)
			return true;

		return false;
	}

	// checks if the current node is the last child of its father (useful for
	// the propagation of the postcondition)
	boolean IsLastChild() {
		int index = this.getParent().getSibling().size() - 1;
		if ((this.getParent().getSibling().indexOf(this)) == index) {
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
		// Precondition p = new Precondition(pre," precondition");
		for (int i = 0; i < leafs.size(); i++) {
			leafs.get(i).Head.setPreconditions("p" + i);
			leafs.get(i).Head.setPostconditions("p" + (i + 1));
			propagatePrecondition(leafs.get(i));
			propagatePostcondition(leafs.get(i));
		}

	}

	public static void main(String[] args) {
		ArrayList<Tree> nodes = new ArrayList<Tree>();
		Node A = new Node("A");
		Tree root = new Tree(A);
		nodes.add(root);
		int depth = 2;
		int length = 3;
		createTree(root, depth, length, "");
		defineKnowledge(root);
		printTree(root);
		/*
		 * leafs= root.getLeaves(); for (Tree node: leafs){
		 * defineKnowledge(node); }
		 */

	}

}
