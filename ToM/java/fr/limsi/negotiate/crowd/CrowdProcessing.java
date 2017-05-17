package fr.limsi.negotiate.crowd;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CrowdProcessing {
	public static void main(String args[]) {
		String domModel = "model3";
		String subModel = "model1";
		String path = "/people/ouldouali/git/Discolog/cooperativeNegotiation/test/";
		String fileName = path+"Console";

		//read file into stream, try-with-resources
		try (Stream<String> input = Files.lines(Paths.get(fileName));
				PrintWriter output = new PrintWriter(path+"/fileName.txt", "UTF-8"))
		{
			input.map(s -> modifyLine(s))
			.forEachOrdered(output::print);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
	
	public static String modifyLine(String line){
		
			String elem = line.replaceAll("Agent1 says", "<br>A:");
			elem = elem.replaceAll("Agent2 says", "<br>&nbsp&nbsp&nbsp&nbspB:");
			elem = elem + "<br>";
			return elem;
	}


}
