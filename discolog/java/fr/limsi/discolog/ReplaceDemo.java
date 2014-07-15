package fr.limsi.discolog;

import java.util.ArrayList;
import java.util.regex.*;

public class ReplaceDemo {
	public static final String EXAMPLE_TEST = "This is my small example "
			+ "string which I'm going to " + "use for pattern matching.";

	public static void main(String[] args) {
		String input = " do(paint(box1, painting_room), do(putdown(box1), "
				+ "do(walk(box1, room1, painting_room), do(pickup(box1),"
				+ " do(open, do(unlock, init))))))";
		//Pattern p=Pattern.compile("do\\(.*?\\)");
		/*Pattern p=Pattern.compile(",do\\(");
		String[] splitString = (p.split(input));
		System.out.println(splitString.length);// should be 14
		for (String string : splitString) {
			string.replaceAll(", init", " ");
			System.out.println(string);
		}*/
		ArrayList<String> JavaPlan = new ArrayList<String>();
	    for (String string: input.split(",do\\(")){
	    	JavaPlan.add(string);
	    	string.replaceAll(", init", " ");
			System.out.println(string);
	    }
	    System.out.println(JavaPlan.size());
	}

}