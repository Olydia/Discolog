package fr.limsi.discolog;

import java.util.ArrayList;
import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;

public class Node {

	/**
	 * @param args
	 */
	private String Name;
	private Precondition Preconditions;
	private Postcondition Postconditions;

	public Node(String name) {
		this.Name = name;
			}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPreconditions() {
		return Preconditions.getScript();
	}

	public void setPreconditions(Precondition preconditions) {
		Preconditions = preconditions;
	}

	public String getPostconditions() {
		return Postconditions.getScript();
	}

	public void setPostconditions(Postcondition postconditions) {
		Postconditions = postconditions;
	}

	@Override
	public String toString() {
		return "Node [Name=" + this.getName() + ", Preconditions="
				+ this.getPreconditions() + ", Postconditions="
				+ this.getPreconditions() + "]";
	}
	public void RemovePrecondition(Node T){
		T.setPreconditions(null);
	}
	public void RemovePostcondition(Node T){
		T.setPostconditions(null);
	}
	/*public class Recipe {
		private ArrayList<Tree> Decomposition; 
		private Applicability Applicable;
		private Node Task;
		
		public Recipe(ArrayList<Tree> decomposition, Applicability applicable,
				Node task) {
			Decomposition = decomposition;
			Applicable = applicable;
			Task = task;
		}
		public ArrayList<Tree> getDecomposition() {
			return Decomposition;
		}
		public void setDecomposition(ArrayList<Tree> decomposition) {
			Decomposition = decomposition;
		}
		public Applicability getApplicable() {
			return Applicable;
		}
		public void setApplicable(Applicability applicable) {
			Applicable = applicable;
		}
		public Node getTask() {
			return Task;
		}
		public void setTask(Node task) {
			Task = task;
		}
		
		
	}*/

}

