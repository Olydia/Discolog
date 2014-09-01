private static ArrayList<String> getPlannerOutput(Term plan) {
	ArrayList<String> Output = new ArrayList<String>();
	String init;
	if(plan == null)
		System.out.println("No recovery STRIPS plan found !");
	else{
	Pattern p = Pattern.compile("(do\\()");
	String[] splitString = (p.split(plan.toString()));
	for (String element : splitString) {
		String elem = element.replaceAll("(init)(\\)+)", "");
		elem = elem.replaceAll(",", "");
		init = elem.replaceAll("\\(.*\\)", "");
		Output.add(init);
	}
	Collections.reverse(Output);
	}
	return Output;
}
