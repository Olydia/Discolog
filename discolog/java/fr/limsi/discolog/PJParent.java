package fr.limsi.discolog;
import alice.tuprologx.pj.annotations.*;
import alice.tuprologx.pj.engine.*;
import alice.tuprologx.pj.model.*;
import alice.tuprologx.pj.meta.*;

@PrologClass(clauses = {"pere(geoffroy,hubert).",
						"pere(hubert,charles).",
						"pere(charles,sylvie).",
						"pere(charles,corinne).",
						"mere(bertille,sylvie).",
						"mere(bertille,corinne).",
						"mere(sidonie,geoffroy)." }
			)

abstract class PJParent {
	@PrologMethod(clauses = {"parent(X,Y) :- mere(X,Y).",
							"parent(X,Y) :- pere(X,Y).",
							"ancetre(X,Y) :- parent(X,Y).",
							"ancetre(X,Y) :- parent(X,Z),ancetre(Z,Y)."}
				)
	
public abstract <$Y, $X> Iterable<$Y> ancetre($X par);


public static void main(String[] args) throws Exception {
		PJParent p = PJ.newInstance(PJParent.class);
		for (Object solution : p.ancetre(new Atom("corinne"))) {
			System.out.println(solution.toString());
		}
	}
}