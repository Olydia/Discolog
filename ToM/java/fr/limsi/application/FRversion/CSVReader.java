package fr.limsi.application.FRversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Read a csv file that contains agents preferences and user preferences 
 * transform them to instances of Negotiation<Restaurant>
 * 
 * @author ouldouali
 *
 */
public class CSVReader {
	/* Odre des criteres : Prix, Cuisine, Ambiance, Localisation 
	 */

	public static ArrayList<String[]> parse (String csvFile){
		ArrayList<String[]> preferences = new ArrayList<String[]>();
 		BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
            	if(!line.contains("class fr.limsi.negotiate.restaurant") &&  !line.equals("")) {
            		
            		preferences.add(nettoyer(line));
            		
            	}
               

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return preferences;
	}

	protected static String[] nettoyer( String line) {
        String cvsSplitBy = ",";
		line = line.replace("[", "");
		line = line.replace("]", "");
//		
		line = line.replace("(", "");
		line = line.replace(")", "");
		
		 String[] country = line.split(cvsSplitBy);
		return country;
	}
	
    public static void main(String[] args) {
    	
       String csvFile = System.getProperty("user.dir")+File.separator+"Participant.txt";
       ArrayList<String[]> preferences = new ArrayList<String[]>();

		preferences = parse(csvFile);
		for(String[] e : preferences){
			System.out.println("");
			for(String ee : e)
				System.out.print(ee + "\t");
		}

    }

}