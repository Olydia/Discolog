package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cetask.DecompositionClass.Applicability;
import edu.wpi.cetask.TaskClass.Postcondition;
import edu.wpi.cetask.TaskClass.Precondition;

public class Node {

	private String Name;
	private String Preconditions;
	private String Postconditions;
	private ArrayList<String> Grounding;
	public Node(String name, String preconditions, String postconditions) {
		super();
		this.Name = name;
		this.Preconditions = preconditions;
		this.Postconditions = postconditions;
		
	}
	
	public ArrayList<String> getGrounding() {
		return Grounding;
	}
	
	public void defineGrounding() {
		this.Grounding = new ArrayList<String>();
		this.Grounding.add(this.Preconditions);
		this.Grounding.add(this.Postconditions);
	}
	
	public void setGrounding(ArrayList<String> grounding) {
		Grounding = grounding;
	}
	/**
	 * @param args
	 */


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
		return Preconditions;
	}

	public void setPreconditions(String preconditions) {
		Preconditions = preconditions;
	}

	public String getPostconditions() {
		return Postconditions;
	}

	public void setPostconditions(String postconditions) {
		Postconditions = postconditions;
	}

	@Override
	public String toString() {
		return "Node [" + Name + ", Preconditions=" + Preconditions
				+ ", Postconditions=" + Postconditions +", Grounding "+ Grounding+ "]";
	}
	public void RemovePrecondition(Node T){
		T.setPreconditions(null);
	}
	public void RemovePostcondition(Node T){
		T.setPostconditions(null);
	}

}

