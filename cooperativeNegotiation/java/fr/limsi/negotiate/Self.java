package fr.limsi.negotiate;

import java.util.Comparator;
import java.util.List;

import fr.limsi.negotiate.Statement.Satisfiable;


public abstract class Self<C> {

	protected MatrixOfPref<C> matrix;

	
	public Satisfiable isSatisfiable(C value){
		if (this.satisfaction(value) >= NegotiationParameters.beta)
			return Satisfiable.TRUE;
		else 
			return Satisfiable.FALSE;
	}



	public List<C> sortValues(){
		List<C> elems = getElements();
		elems.sort(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2){
				 int result = Float.compare(satisfaction(o2), satisfaction(o1));
				return result;
			}
		});
		return elems;
	}
	
	
	public float satisfaction(C value){
		float score = matrix.getScoreOf(value);
		return (1-score);
	}
	
	public abstract List<C> getElements ();
	public abstract void addPreference (C less, C more);

}
