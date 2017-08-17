package fr.limsi.negotiate.ToM;

import java.util.*;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class OtherSelf<C> extends Self_Ci<C> {

	HashMap<C, Satisfiable> preferences; 
	public OtherSelf(Class<C> type, HashMap<C, Satisfiable> preferences) {
		super(type);
		this.preferences = preferences;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Satisfiable isSatisfiable(C value, double pow){
		return isSatisfiable(value);
	}
	
	public Satisfiable isSatisfiable(C value){
		return preferences.get(value);
	}
	
	@Override
	public float satisfaction(C value){
		if(isSatisfiable(value).equals(Satisfiable.TRUE))
			return 1;
		return 0;
	}
	
	@Override
	public List<C> getSatisfiableValues(double pow){
		List<C> val = sortValues();
		List<C> sat = new ArrayList<C>();
		for(C elem : val) {
			if(isSatisfiable(elem, pow).equals(Satisfiable.TRUE))
				sat.add(elem);
		}
		return sat;
	}
	

}
