private static void Strips_Input(List<String> Initial_state, String Goal,Prolog engine) {
	try {
	for(String init : Initial_state){
		engine.addTheory(new Theory("strips_holds(" + init + ",init)."));
	}
	Theory PlannerCall = new Theory("test1(Plan):- strips_solve(["+ Goal + "],30,Plan).");
	engine.addTheory(PlannerCall);
	} catch (InvalidTheoryException e) {
	e.printStackTrace();
	}

}