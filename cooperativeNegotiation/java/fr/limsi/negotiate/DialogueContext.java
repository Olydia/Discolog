package fr.limsi.negotiate;

import java.util.*;

public class DialogueContext {
	private List<NegoUtterance> history;
	
	public DialogueContext() {
		this.history =new ArrayList<NegoUtterance>();
	}

	public List<NegoUtterance> getHistory() {
		return history;
	}
	public void addUtt (NegoUtterance ut){
		this.history.add(ut);
	}

	public NegoUtterance getLastMove(){
		return history.get(history.size()-1);
	}
	
	public NegoUtterance getLastMove(boolean external){
		for(int i=history.size()-1; i>=0; i--){
			if(history.get(i).isExtrenal()== external)
				return history.get(i);
		}
		return null;
	}



}