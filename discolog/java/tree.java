import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class tree {

	static int cmpt = 0;
	static int cmp = 3;
	private Node head;
	// private ArrayList<Tree_Recipes> children;
	private Map<String, ArrayList<tree>> children;
	private tree Parent;

	public tree(Node head, HashMap<String, ArrayList<tree>> children) {
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
		HashMap<String, ArrayList<tree>> child = new HashMap<String, ArrayList<tree>>();
		tree root = new tree(A, child);
		int depth = 2;
		int length = 2;
		int recipe = 2;
		createTree(root, depth, length, recipe);
		// defineKnowledge(root);
		//printTree(root);
		// for(tree i :root.getLeaves())
		// System.out.println(i);
		propagatePrecondition(root);
		propagatePostcondition(root);
		DefineCondition(root);
		printTree(root);
		// System.out.println(Init(root));

	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Map<String, ArrayList<tree>> getChildren() {
		return children;
	}

	public void setChildren(Map<String, ArrayList<tree>> child_recipe) {
		this.children = child_recipe;
	}

	public tree getParent() {
		return Parent;
	}

	public void setParent(tree parent) {
		Parent = parent;
	}

	public static void createTree(tree root, int depth, int length, int rep) {

		// Map<String, ArrayList<tree>> child_recipe = new HashMap<String,
		// ArrayList<tree>>();
		if (depth >= 1) {

			for (int j = 0; j < rep; j++) {
				cmpt++;
				String recipe = "R" + cmpt;
				root.getChildren().put(recipe, new ArrayList<tree>());
				int cc = cmpt;
				for (int i = 0; i < length; i++) {

					tree newTreeElem = new tree(new Node("a" + cc + (i + 1)),
							new HashMap<String, ArrayList<tree>>());
					newTreeElem.setParent(root);
					root.getChildren().get(recipe).add(newTreeElem);
					createTree(newTreeElem, depth - 1, length, rep);

				}

			}

		}
	}

	public static void printTree(tree root) {
		if (!root.isLeaf()) {
			//System.out.println(root.toString());
			for (Map.Entry<String, ArrayList<tree>> NodeEntry : root
					.getChildren().entrySet()) {
				 System.out.println(NodeEntry.getKey() );
				for (tree i : NodeEntry.getValue()) {
					System.out.println(i.toString());
					printTree(i);
				}

			}

		}
	}

	private boolean isLeaf() {
		boolean value = false;
		if (this.getChildren().isEmpty())
			value = true;
		return value;
	}

	// returns all the leaves in the tree by only giving as input a node
	public ArrayList<tree> getLeaves() {
		ArrayList<tree> leaves = new ArrayList<tree>();

		if (this.isLeaf())
			leaves.add(this);
		else {

			for (Map.Entry<String, ArrayList<tree>> child : this.getChildren()
					.entrySet()) {
				for (tree i : child.getValue()) {
					leaves.addAll(i.getLeaves());
				}
			}
		}
		return leaves;
	}

	public static void propagatePrecondition(tree root) {
	
		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<tree>> NodeEntry : root.getChildren().entrySet()) {
				for (tree i : NodeEntry.getValue()) {
					if (NodeEntry.getValue().indexOf(i) == 0){
						i.getHead().setPreconditions(root.getHead().getPreconditions());
						//System.out.println("parent: " + elem.toString()
							//	+ "  firste child :" + i.toString());
						propagatePrecondition(i);
					}	
				}
			}
		}
	}
	
	public static void propagatePostcondition(tree root) {
	
		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<tree>> NodeEntry : root
					.getChildren().entrySet()) {
				for (tree i : NodeEntry.getValue()) {
					if (NodeEntry.getValue().indexOf(i) == NodeEntry.getValue()
							.size() - 1){
						i.getHead().setPostconditions(root.getHead().getPostconditions());
						propagatePostcondition(i);
					}
				}
			}
		}
	}
	public static void DefineCondition(tree root) {
		
		if (!root.isLeaf()) {
			for (Map.Entry<String, ArrayList<tree>> NodeEntry : root
					.getChildren().entrySet()) {
				
				for (int i=0; i<NodeEntry.getValue().size();i++) {
					if(NodeEntry.getValue().get(i).getHead().getPostconditions() == null){
						NodeEntry.getValue().get(i).getHead().setPostconditions("P"+ cmp);
						cmp ++;
						propagatePostcondition(NodeEntry.getValue().get(i));
						if(NodeEntry.getValue().get(i+1).getHead().getPreconditions() == null){	
						NodeEntry.getValue().get(i+1).getHead().setPreconditions(NodeEntry.getValue().get(i).getHead().getPostconditions());
						propagatePrecondition(NodeEntry.getValue().get(i+1));
						}		
					}
					if(NodeEntry.getValue().get(i).getHead().getPreconditions() == null){
						NodeEntry.getValue().get(i).getHead().setPreconditions("P"+ cmp);
						cmp ++;
						propagatePrecondition(NodeEntry.getValue().get(i));
						if(NodeEntry.getValue().get(i-1).getHead().getPostconditions() == null){	
						NodeEntry.getValue().get(i-1).getHead().setPostconditions(NodeEntry.getValue().get(i).getHead().getPreconditions());
						propagatePostcondition(NodeEntry.getValue().get(i-1));
						}
					}
					DefineCondition(NodeEntry.getValue().get(i));

				}
			}
		}
	}
}




