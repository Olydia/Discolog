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

	@Override
	public String toString() {
		return "Tree [" + Head.getName() + "]";
	}
	public Tree getParent() {
		return Parent;
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

	public boolean isLeaf() {
		if (this.getSibling() == null)
			return true;
		else
			return false;
	}


	public int getLevel() {
		int level = 0;
		Tree p = getParent();
		while (p != null) {
			++level;
			p = p.getParent();
		}
		return level;
	}

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

	public static void printTree(Tree root) {
		System.out.println(root.getLevel());
		if (!root.isLeaf()) {
			for (Tree i : root.getSibling()) {
				printTree(i);
			}
		}
	}

	public ArrayList<Tree> getLeaves () {
		ArrayList<Tree> leaves = new ArrayList<Tree> ();
		
		if (isLeaf())
			leaves.add (this);
		else {
			for (Tree child : this.getSibling())
				leaves.addAll (child.getLeaves());
		}
		
		return leaves;
	}
	boolean IsFistChild(){
		if (this.getParent().getSibling().get(0).equals(this))
			return true;
			
	    return false;
	}
	boolean IsLastChild(){
		int index = this.getParent().getSibling().size()-1;
		if ((this.getParent().getSibling().indexOf(this)) == index ){
			return true;

		}
			
	    return false;
	}

	public static void propagatePrecondition(Tree root){
		Tree elem = root;
		Tree p = elem.getParent();
		while (p != null) {
			if (elem.IsFistChild()) 
				p.Head.setPreconditions(root.Head.getPreconditions());
			elem = p;
			p = p.getParent();
		}			
			
		}
	public static void propagatePostcondition(Tree root){
		Tree elem = root;
		Tree p = elem.getParent();
		while (p != null) {
			if (elem.IsLastChild()) 
				p.Head.setPostconditions(root.Head.getPostconditions());
			elem = p;
			p = p.getParent();
		}			
			
		}
	public void defineKnowledge(Tree root){
		ArrayList<Tree> leafs = root.getLeaves();
		String pre = "p";
		//Precondition p =  new Precondition(pre," precondition");
		for(int i =0; i<leafs.size(); i++){
			//leafs.get(i).Head.setPreconditions(pre, simple+" precondition")
		}
		
	}
	public static void main(String[] args) {
		ArrayList<Tree> nodes = new ArrayList<Tree>();
		ArrayList<Tree> leafs = new ArrayList<Tree>();
		Node A = new Node("A");
		Tree root = new Tree(A);
		nodes.add(root);
		int depth = 2;
		int length = 3;
		createTree(root, depth, length, "");
		leafs= root.getLeaves();
		for (Tree node: leafs){
			propagatePrecondition(node);
			propagatePostcondition(node);

		}
		
	}

	
}
