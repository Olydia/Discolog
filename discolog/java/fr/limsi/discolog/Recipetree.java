package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RecipeTree {

	static int cmpt = 0;
	static int cmp = 3;
	private Node head;
	// private ArrayList<Tree_Recipes> children;
	private Map<String, ArrayList<RecipeTree>> children;
	private RecipeTree Parent;

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
		HashMap<String, ArrayList<RecipeTree>> child = new HashMap<String, ArrayList<RecipeTree>>();
		RecipeTree root = new RecipeTree(A, child);
		int depth = 2;
		int length = 2;
		int recipe = 2;
		createTree(root, depth, length, recipe);
		defineKnowledge(root);
		for (RecipeTree leaf: root.getLeaves())
			System.out.println(leaf.toString());
		//printTree(root);
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
				root.getChildren().put(recipe, new ArrayList<RecipeTree>());
				int cc = cmpt;
				for (int i = 0; i < length; i++) {

					RecipeTree newTreeElem = new RecipeTree(new Node("a" + cc
							+ (i + 1)),
							new HashMap<String, ArrayList<RecipeTree>>());
					newTreeElem.setParent(root);
					root.getChildren().get(recipe).add(newTreeElem);
					createTree(newTreeElem, depth - 1, length, rep);

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
					System.out.println(i.toString());
					printTree(i);
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

	public static void propagatePrecondition(RecipeTree root) {

		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<RecipeTree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (RecipeTree i : NodeEntry.getValue()) {
					if (NodeEntry.getValue().indexOf(i) == 0) {
						i.getHead().setPreconditions(
								root.getHead().getPreconditions());
						// System.out.println("parent: " + elem.toString()
						// + "  firste child :" + i.toString());
						propagatePrecondition(i);
					}
				}
			}
		}
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
					if (NodeEntry.getValue().get(i).getHead()
							.getPostconditions() == null) {
						NodeEntry.getValue().get(i).getHead()
								.setPostconditions("P" + cmp);
						cmp++;
						propagatePostcondition(NodeEntry.getValue().get(i));
						if (NodeEntry.getValue().get(i + 1).getHead()
								.getPreconditions() == null) {
							NodeEntry
									.getValue()
									.get(i + 1)
									.getHead()
									.setPreconditions(
											NodeEntry.getValue().get(i)
													.getHead()
													.getPostconditions());
							propagatePrecondition(NodeEntry.getValue().get(
									i + 1));
						}
					}
					if (NodeEntry.getValue().get(i).getHead()
							.getPreconditions() == null) {
						NodeEntry.getValue().get(i).getHead()
								.setPreconditions("P" + cmp);
						cmp++;
						propagatePrecondition(NodeEntry.getValue().get(i));
						if (NodeEntry.getValue().get(i - 1).getHead()
								.getPostconditions() == null) {
							NodeEntry
									.getValue()
									.get(i - 1)
									.getHead()
									.setPostconditions(
											NodeEntry.getValue().get(i)
													.getHead()
													.getPreconditions());
							propagatePostcondition(NodeEntry.getValue().get(
									i - 1));
						}
					}
					DefineCondition(NodeEntry.getValue().get(i));

				}
			}
		}
	}

	public static void defineKnowledge(RecipeTree root) {
		propagatePrecondition(root);
		propagatePostcondition(root);
		DefineCondition(root);
	}

	public static String Init(int cond, ArrayList<String> recipecondition) {

		String init = "var P1=true";
		for (int i = 2; i < cond; i++) {
			init += ",P" + i;
		}
		init += "=false";
		for (int i = 0; i < recipecondition.size() ; i++) {
			init += "," + recipecondition.get(i);
		}
		return init;

	}
}
