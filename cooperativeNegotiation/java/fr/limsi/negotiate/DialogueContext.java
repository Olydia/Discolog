package fr.limsi.negotiate;

import java.util.*;

public class DialogueContext {
	private List<Utterance> history;
	
	public DialogueContext() {
		this.history =new ArrayList<Utterance>();
	}

	public List<Utterance> getHistory() {
		return history;
	}
	public void addUtt (Utterance ut){
		this.history.add(ut);
	}

	public Utterance getLastMove(){
		return history.get(history.size()-1);
	}



}