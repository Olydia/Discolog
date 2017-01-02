package fr.limsi.negotiate.lang;

/* Copyright (c) 2009 Charles Rich and Worcester Polytechnic Institute.
 * All Rights Reserved.  Use is subject to license terms.  See the file
 * "license.terms" for information on usage and redistribution of this
 * file and for a DISCLAIMER OF ALL WARRANTIES.
 */

import edu.wpi.cetask.*;
import edu.wpi.disco.Disco;
import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;
import fr.limsi.negotiate.NegoUtterance.UtType;

public class StatePreference extends NegotiationUtterance {

	public static TaskClass CLASS;

	// for TaskClass.newStep
	public StatePreference (Disco disco, Decomposition decomp, String name, boolean repeat) { 
		super(StatePreference.class, disco, decomp, name, repeat);
	}

	public StatePreference (Disco disco, Boolean external, Criterion value, Satisfiable likable) { 
		super(StatePreference.class, disco, external);
		if ( value != null ) setSlotValue("value", value);
		if ( likable != null ) setSlotValue("likable", likable);
	}

	public Criterion getValue () { return (Criterion) getSlotValue("value"); }
	public Satisfiable getLikable () { return (Satisfiable) getSlotValue("likable"); }
	// count the number of statements ?
	@Override
	public void interpret () {
		Statement<Criterion> statement = new Statement<Criterion>(getValue(),getLikable());
		PreferenceMove st = new PreferenceMove(statement, getExternal(), UtType.STATE);
		getNegotiation().getContext().addUtt(st);
		getNegotiation().addStatement(statement, getExternal());

	}

}