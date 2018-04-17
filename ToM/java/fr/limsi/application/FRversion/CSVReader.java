package fr.limsi.application.FRversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

import sun.management.resources.agent_zh_CN;


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
		HashMap<Integer, ArrayList<String[]>> values = new HashMap<Integer, ArrayList<String[]>>();
 		BufferedReader br = null;
        String line = "";
        boolean lecture = false;
        int criteria = 1;
        int agents = 0;
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
            	if(!line.contains("class fr.limsi.negotiate.restaurant") &&  !line.equals("")) {
            		
            		if(isAgentName(line)){
            			lecture = true;
            		}
            		
        			preferences.add(nettoyer(line));

//            		if(lecture && criteria <=4){
//            			preferences.add(nettoyer(line));
//            			criteria ++;
//
//            		}
            		
            		
//            		else {
//            			
//            			values.put(agents,(ArrayList<String[]>) preferences.clone());
//            			preferences.clear();
//            			agents ++;
//            			lecture = false;
//            			criteria = 1;
//            		}
            		
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
	
	public static boolean isAgentName(String line){
		List<String> myList = Arrays.asList("Bob", "Arthur", "Kevin", "User");
    	return (myList.stream().anyMatch(str -> line.contains(str)));
    	
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
    	

    
    	String line = "";
    	System.out.println(isAgentName(line));
    }
}