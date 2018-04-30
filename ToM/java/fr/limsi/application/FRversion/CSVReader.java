package fr.limsi.application.FRversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;

public class CSVReader {
	String fileName;
	
	public CSVReader(String fileName){
		this.fileName = fileName;
	}
	
	public double getFinalPow(String pow){
		pow =pow.replace("[", "");
		pow = pow.replace("]", "");
		String [] v = pow.split(",");
		String po = v[v.length-1];
		po = po.replaceAll(" ", "");
		return Double.parseDouble(po);
	}
	public ArrayList<String> getPower (){
			
			ArrayList<String> agents = new ArrayList<String>();
			String path =System.getProperty("user.dir")+File.separator+"";
	    	String csvFile = path + "power.txt";
	        BufferedReader br = null;
	        String line = "";

	        try {
	        	 
	            br = new BufferedReader(new FileReader(csvFile));
	            //igner la premiere ligne
	            line = br.readLine();
	            while ((line = br.readLine()) != null) {
	            	String[] powers = line.split(";");
	            	StringBuilder concat = new StringBuilder();
	            	concat.append(powers[0]+";"); 
	            		
	            	for ( int i =1; i<powers.length; i++){
	            		String p = powers[i];
	            		double value = getFinalPow(p);
	            		concat.append(value+";");
	            	}
	            	agents.add(concat.toString());
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
	        return agents;
	    }
	            
	
 	
	/**
     * 0 id user
     * 1 Bob
     * 2 Arthur
     * 3 Kevin
     * 
     */
	public void readCSV(){
		String path =System.getProperty("user.dir")+File.separator+"";
    	String csvFile = path + fileName+".csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {
        	 
            br = new BufferedReader(new FileReader(csvFile));
            //igner la premiere ligne
            line = br.readLine();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] restaurants = line.split(cvsSplitBy);
               
                String userId = restaurants[0];
                String prefFile = System.getProperty("user.dir")+File.separator+
                		"Participant"+userId+".txt";
               
                PreferencesExtracter extracter = new PreferencesExtracter(prefFile);
                HashMap<Integer, ArrayList<String[]>> values = 
                		new HashMap<Integer, ArrayList<String[]>>();
                values = extracter.parse();
                Negotiation<? extends Option> userPref = extracter.createModel(values.get(0));
                StringBuilder result = new StringBuilder();
                result.append(userId);
                for(int i =1; i<restaurants.length; i++ ){
                	String r = restaurants[i];
                	int index = 1;
                    Negotiation<? extends Option> agentPref = extracter.createModel(values.get(index));
                    
                    // getSat of the restaurant
                    double agent = agentPref.getSat(r);
                    double user = userPref.getSat(r);
                    result.append(";"+user +";"+ agent);
                    
                    index ++;
                }
                
                writeLine(result.toString()+"\n", System.getProperty("user.dir")+File.separator+
                		"restaurantsSat.txt");
                
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

    }
	
	public void writeLine(String line, String fileName){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(line);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}
	
	
    public static void main(String[] args) {
    	CSVReader read = new CSVReader(""); //compVsMimic_Etude/EtudeFinale/Data/restaurantsChoisis
    	//read.readCSV();
    	for(String line : read.getPower())
    		System.out.println(line);
    	
    }

    	

}