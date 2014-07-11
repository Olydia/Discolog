package fr.limsi.discolog;

import java.util.regex.*;

public class ReplaceDemo {
	public static void main(String[] args) {
		String input = 
                  " do(paint(box1, painting_room), do(putdown(box1), "
		           +"do(walk(box1, room1, painting_room), do(pickup(box1),"
                   +" do(open, do(unlock, init))))))";

		Pattern p = Pattern.compile("(^do()(.*)()$)");
		Matcher m = p.matcher(input);

		StringBuffer result = new StringBuffer();
		while (m.find()) {
			System.out.println("Masking: " + m);
			m.appendReplacement(result, m.group(1) + "***masked***");
		}
		m.appendTail(result);
		System.out.println(result);
	}
}