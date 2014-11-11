package fr.limsi.discolog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int depth = 2, taskBranching = 2, level = 50;
		int lineNumber =2;
		String destination = System.getProperty("user.dir") 
				+ "/prolog/test-2p/Test_Results_Final/"+level+"/"+level;
		try{
		FileWriter fw;
		fw = new FileWriter(destination, true);
		BufferedWriter output = new BufferedWriter(fw);
	//	try {
			

		output.write("Level NbBreakdwon NbRecover NbCandidates NbRecoveredCandidates");
		output.flush();
		output.newLine();output.flush();

		for(int j =1; j< 100; j++){
			String adresse = "test_"+depth+"_"+taskBranching+"_"+level+"_"+j+".txt";
			String adressedufichier = System.getProperty("user.dir") 
					+ "/prolog/test-2p/Test_Results_Final/"+level+"/"+adresse;

			InputStream is = new FileInputStream(adressedufichier);
			InputStreamReader isr = new InputStreamReader(is);		
			BufferedReader r = new BufferedReader(isr);// read line from file
			

			for (int i = 1; i < lineNumber ; i++)
			{
				r.readLine();
			}
			String line = r.readLine();
			output.write(line);
			output.flush();
			output.newLine();
			output.flush();
			System.out.println(line);
			
			// write it in the destination file
		}
//		} finally {
//            output.close();
//        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
