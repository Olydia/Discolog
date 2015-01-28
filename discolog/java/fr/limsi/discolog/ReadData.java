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
		int depth =2, taskBranching = 3, recipe =3, level = 25;
		int lineNumber =20;
		String destination = System.getProperty("user.dir") 
				+ "/prolog/test-2p/recipes/"+level+"/"+level;
		try{
			FileWriter fw;
			fw = new FileWriter(destination, true);
			BufferedWriter output = new BufferedWriter(fw);
			//	try {


			output.write("Level NbBreakdwon NbRecover NbCandidates NbRecoveredCandidates");
			output.flush();
			output.newLine();output.flush();

			for(int j =21; j<= 81; j++){
			//	for(int k =1; k<= 10; k++){
					String adresse = "test_"+depth+"_"+taskBranching+"_"+recipe +"_"+level+"_"+j+".txt";
					String adressedufichier = System.getProperty("user.dir") 
							+ "/prolog/test-2p/recipes/"+level+"/"+adresse;

					InputStream is = new FileInputStream(adressedufichier);
					InputStreamReader isr = new InputStreamReader(is);		
					BufferedReader r = new BufferedReader(isr);// read line from file
					r.readLine();
					for (int i = 1; i <= lineNumber ; i++)
					{
						String line = r.readLine();
						output.write(line);
						System.out.println(line);
						output.flush();
						output.newLine();
						output.flush();
					}
					
					output.newLine();
					output.flush();
					// write it in the destination file
				//}
			}
			//		} finally {
	         output.close();
			//        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}