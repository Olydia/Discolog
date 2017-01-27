package fr.limsi.negotiate.crowd;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CrowdProcessing {
	public static void main(String args[]) {
		String domModel = "model2";
		String subModel = "model1";
		String path = "/people/ouldouali/git/Discolog/cooperativeNegotiation/test/dom08_sub04";
		String fileName = path+"/Dominant-"+domModel+"__Submissive-"+subModel+".test";

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
		
			String elem = line.replaceAll("  > next", "");
			elem = elem.replaceAll("Dominant says", "A:");
			elem = elem.replaceAll("Submissive says", "&nbsp&nbsp&nbsp&nbspB:");
			elem = elem.replaceAll("\\[", "<small> &nbsp&nbsp[");
			elem = elem.replaceAll("]", "] </small> ");
			elem = elem + "<br>";
			return elem;
	}


}
