package fr.limsi.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class CSVReader {
	
	public String[] codes = {"BFGF",
			"BTGT",
			"BFGT",
			 "BTGF",
			 "BFGFAU4",
			"BTGTAU4",
			 "BFGTAU4",
			 "BTGFAU4",
			 "BFGFAU1+AU2",
			 "BTGTAU1+AU2",
			 "BFGTAU1+AU2",
			 "BTGFAU1+AU2"
};
	
	public static HashMap<String, Double>  parse (String csvFile){
		
		HashMap<String, Double>  elements = new HashMap<String, Double> ();
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                elements.put(country[0], Double.valueOf(country[1]));
              //  System.out.println("code " + country[0] + " , value=" + country[1] + "]");

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
		return elements;
	}
	public static double [] [] createMatrix(String[] codes, String csvFile){
		int size= codes.length;
		double [] [] pvalues = new double [size][size];
		HashMap<String, Double>  elements = parse(csvFile);
		
		for (Map.Entry<String,Double> mapentry : elements.entrySet()) {
	        String key =  mapentry.getKey();
	        String[] c =key.split("-");
	        System.out.println("HashMap : " +c[0] + "  "+ c[1]);
	        
	        int ligne = Arrays.asList(codes).indexOf(c[0]);
	        int colonne = Arrays.asList(codes).indexOf(c[1]);
	        System.out.println("Dans la liste :" + codes[ligne]+ "  "+ codes[colonne] );
	       
	        pvalues[ligne][colonne] =  mapentry.getValue();
//			System.out.println("clé: "+mapentry.getKey() 
//	                              + " | valeur: " + mapentry.getValue());
	        }
		return pvalues;
	}
    public static void main(String[] args) {
    	String[] b = {"BFGF",
    			"BTGT",
    			"BFGT",
    			 "BTGF",
    			 "BFGFAU4",
    			"BTGTAU4",
    			 "BFGTAU4",
    			 "BTGFAU4",
    			 "BFGFAU1+AU2",
    			 "BTGTAU1+AU2",
    			 "BFGTAU1+AU2",
    			 "BTGFAU1+AU2"
    };

       String csvFile = "/people/ouldouali/Desktop/exemple.csv";
       double[][] matrix = createMatrix(b, csvFile);
       System.out.println(b);
       for (int k = 0; k < b.length; k++)
    	   System.out.print(b[k] + ";");
    	   
       for (int i = 0; i < matrix.length; i++) {
    	    for (int j = 0; j < matrix[i].length; j++) {
    	        System.out.print(matrix[i][j] + ";");
    	    }
    	    System.out.println("\n");
    	}
       
    }

}