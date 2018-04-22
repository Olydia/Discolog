package fr.limsi.application.FRversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;

public class CSVReader {
	String fileName;
	
	public CSVReader(String fileName){
		this.fileName = fileName;
	}
	
	public void readCSV(){
		String path ="";
    	String csvFile = path +".csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] restaurants = line.split(cvsSplitBy);
                /**
                 * 0 id user
                 * 1 Bob
                 * 2 Arthur
                 * 3 Kevin
                 * 
                 */
                String userId = restaurants[0];
                String prefFile = System.getProperty("user.dir")+File.separator+
                		"Participant"+userId+"txt";
               
                PreferencesExtracter extracter = new PreferencesExtracter(prefFile);
                HashMap<Integer, ArrayList<String[]>> values = 
                		new HashMap<Integer, ArrayList<String[]>>();
                values = extracter.parse();
                
                //Bob
                Negotiation<? extends Option> bob = extracter.createModel(values.get(0));
                
                // getRestaurant name
                bob.getSat(restaurants[1]);
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
	
	public void writeLine(String line, String file){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter(file);
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
    	
    }

    	

}