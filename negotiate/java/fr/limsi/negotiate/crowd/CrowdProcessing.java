package fr.limsi.negotiate.crowd;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CrowdProcessing {
	public static void main(String args[]) {
		String path = "/people/ouldouali/git/Discolog/CrowdSourcingStudy/Dialogues_test/Dialogues_Ask/new_dialogues";
		String fileName = path+"/DOM_S2_SUB_D1.test";

		//read file into stream, try-with-resources
		try (Stream<String> input = Files.lines(Paths.get(fileName));
				PrintWriter output = new PrintWriter(path+"/output.txt", "UTF-8"))
		{
			input.map(s -> modifyLine(s))
			.forEachOrdered(output::print);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
	
	public static String modifyLine(String line){
		
			String elem = line.replaceAll("  > next", "");
			elem = elem.replaceAll("Agent1 says", "A:");
			elem = elem.replaceAll("Agent2 says", "&nbsp&nbsp&nbsp&nbspB:");
			// elem = elem.replaceAll("."+"\"", "."+"\"<br>");
			//elem = elem.replaceAll("\?\"", "?\"<br>");
			elem = elem.replaceAll("\\[", "<small> &nbsp&nbsp[");
			elem = elem.replaceAll("]", "] </small> ");
			elem = elem + "<br>";
			return elem;
	}


}
