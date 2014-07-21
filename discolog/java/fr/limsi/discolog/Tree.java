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

	public Tree getParent() {
		return Parent;
	}

	public void setParent(Node parent) {
		Tree p = new Tree(parent);
		this.Parent = p;
		p.addSibling(this);
	}

	public void addSibling(Tree child) {
		// checks if it does already exist
		if (this.getSibling() == null) {
			this.setSibling(new ArrayList<Tree>());
			child.Parent = this;
			this.getSibling().add(child);
		} else if (!this.getSibling().contains(child)) {
			child.Parent = this;
			this.getSibling().add(child);
		} else
			System.out.println("The node" + child.getHead().getName()
					+ "is already a sibling of the task"
					+ this.getHead().getName());
	}

	public boolean isLeaf() {
		if (this.getSibling() == null)
			return true;
		else
			return false;
	}

	public ArrayList<Tree> getLeaf() {
		ArrayList<Tree> leafs = new ArrayList<Tree>();

		for (Tree i : this.getSibling()) {
			if (i.isLeaf())
				leafs.add(i);
		}
		return leafs;
	}

	public int getLevel() {
		int level = 0;
		Tree p = this.Parent;
		while (p != null) {
			++level;
			p = p.Parent;
		}
		return level;
	}

	public static void CreateTree(Tree root, int depth, int length, String name) {
		if (depth >= 1) {
			for (int i = 0; i < length; i++) {
				cmpt++;
				Tree newTreeElem = new Tree(new Node("A"+name+(i+1)));
				root.addSibling(newTreeElem);
				CreateTree(newTreeElem, depth - 1, length,name+(i+1));
			}
		}
	}

	public static void main(String[] args) {
		// Tree M = new Tree();
		ArrayList<Tree> nodes = new ArrayList<Tree>();
		Node A = new Node("A");
		Tree root = new Tree(A);
		nodes.add(root);
		int depth = 3;
		int length = 3;
		CreateTree(root, depth, length, "");
		// for (int i = 0; i < depth ; i++) {
		/*
		 * for (int j = 0; j <= length; j++) { root.addSibling(new Tree(new
		 * Node("A" + cmpt))); // nodes.add(node); cmpt++; }
		 */
		// for(Tree roo: nodes){
		/*
		 * for(int j=0; j< nodes.size() && j< depth; j++) { if (
		 * nodes.get(j).isLeaf()){ for (int i = 0; i < length ; i++) { Tree
		 * newTreeElem = new Tree(new Node("A" + cmpt));
		 * nodes.get(j).addSibling(newTreeElem); cmpt ++;
		 * nodes.add(newTreeElem); }
		 * 
		 * } }
		 */
		PrintTree(root);
	}

	public static void PrintTree(Tree root) {
		System.out.println(root.getHead().getName());
		if (!root.isLeaf()) {
			for (Tree i : root.getSibling()) {
				PrintTree(i);
			}
		}
	}
}
