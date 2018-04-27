package fr.limsi.application.FRversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

import fr.limsi.application.FRversion.SaisiePref.dndTestFR.ModelDePreferences;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;


/**
 * Read a csv file that contains agents preferences and user preferences 
 * transform them to instances of Negotiation<Restaurant>
 * 
 * @author ouldouali
 *
 */
public class PreferencesExtracter {
	/* Odre des criteres : Prix, Cuisine, Ambiance, Localisation 
	 */
	String csvFile;
	
	public PreferencesExtracter(String csvFile){
		this.csvFile = csvFile;
	}

	
	
	public  HashMap<Integer, ArrayList<String[]>>  parse (){
		ArrayList<String[]> preferences = new ArrayList<String[]>();
		HashMap<Integer, ArrayList<String[]>> values = new HashMap<Integer, ArrayList<String[]>>();
 		BufferedReader br = null;
        String line = "";
        int criteria = 1;
        int agents = 0;
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	
            	if(line.startsWith("[(")) {
            		
            		criteria ++;
        			preferences.add(nettoyer(line));

            	}
               
            	if(criteria>=5){
            		ArrayList<String[]> clone = new ArrayList<String[]>();
            		for(String[] e : preferences){
            			clone.add(e.clone());
//       					for(String ee : e)
//       						System.out.print(ee + "\t");
       				}
       				//System.out.println("\n");
            		values.put(agents,clone);
            		preferences.clear();
       				criteria = 1;
            		agents ++;
//        			lecture = false;
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
        return values;
	}
	
	public  boolean isAgentName(String line){
		List<String> myList = Arrays.asList("Bob", "Arthur", "Kevin", "User");
    	return (myList.stream().anyMatch(str -> line.contains(str)));
    	
	}
	
	protected  String[] nettoyer( String line) {
        String cvsSplitBy = ",";
		line = line.replace("[", "");
		line = line.replace("]", "");
//		
		line = line.replace(" (", "");
		line = line.replace("(", "");
		line = line.replace(")", "");
		
		 String[] country = line.split(cvsSplitBy);
		return country;
	}
	public Negotiation<? extends Option> createModel
				(ArrayList<String[]> values){
		
		ModelDePreferences model = new ModelDePreferences();
		model.d1_cost.fromStringToPreferences(values.get(0));
		model.d1_cuisine.fromStringToPreferences(values.get(1));
		model.d1_atmosphere.fromStringToPreferences(values.get(2));
		model.d1_location.fromStringToPreferences(values.get(3));
		
		return model.createModel();
	}
	
    public static void main(String[] args) {	
    	

       String csvFile = System.getProperty("user.dir")+File.separator+"Participant50.txt";
       PreferencesExtracter p = new PreferencesExtracter(csvFile);
       HashMap<Integer, ArrayList<String[]>> values = new HashMap<Integer, ArrayList<String[]>>();
    		  values = p.parse();
       
       System.out.println(p.createModel(values.get(1)).printPreferences());
       
       
       /**
        * Pour chaque model calculer les distances avec le premier model
        * lire fichier csv avec separateur
        * En fonction du id dans csv ouvrir fichier et creer preference
        * pour colonne 1 pref(user + bob)
        * pour colonne 2 pref(user +arthur)
        * pour colonne 3 pref(user + kevin)
        */

    
    }
}