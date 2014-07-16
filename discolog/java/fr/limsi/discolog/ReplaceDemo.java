package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.regex.*;

public class ReplaceDemo {
	
	@SuppressWarnings("null")
	private static ArrayList<String> getPlannerOutput(String plan) {
		ArrayList<String> javaPlan = new ArrayList<String>();
		String init;

		Pattern p = Pattern.compile("(do\\()");
		String[] splitString = (p.split(plan));
		// remove init, parenthisis and init argument to obtain only the name of
		// actions

		for (String element : splitString) {
			String elem = element.replaceAll("(init)(\\)+)", "");
			elem = elem.replaceAll(",", "");
			init = elem.replaceAll("\\(.*\\)", "");
			javaPlan.add(init);
			System.out.println(init);

		}
		return javaPlan;

	}
	public static void main(String[] args) {
		String input = " do(paint(box1, painting_room), do(putdown(box1), "
				+ "do(walk(box1, room1, painting_room), do(pickup(box1),"
				+ " do(open, do(unlock, init))))))";

		
		ArrayList<String> JavaPlan = new ArrayList<String>();
		JavaPlan=getPlannerOutput(input);
		
		System.out.println(JavaPlan.size());
	}

}
