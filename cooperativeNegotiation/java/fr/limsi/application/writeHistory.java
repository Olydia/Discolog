package fr.limsi.application;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

public class writeHistory {

	public void write(String txt){
		try{
			File ff=new File("C:\\Users\\dhouib\\Desktop\\test.txt");
			//ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			ffw.write(txt);  // écrire une ligne dans le fichier resultat.txt
			ffw.write("\n"); // forcer le passage à la ligne
			ffw.close(); // fermer le fichier à la fin des traitements
			} catch (Exception e) {}
		}
		public String read(){
			String chaine="";
			String fichier ="C:\\Users\\dhouib\\AppData\\Local\\Temp\\Console.test";

			//lecture du fichier texte
			try{
				InputStream ips=new FileInputStream(fichier);
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					write(ligne);
					chaine+=ligne+"\n";
				}
				br.close();


			}
			catch (Exception e){
				System.out.println(e.toString());
			}

			return chaine;
		}
		public boolean copyFile(File source, File dest) {
		    try (InputStream sourceFile = new java.io.FileInputStream(source);
		            OutputStream destinationFile = new FileOutputStream(dest)) {
		        // Lecture par segment de 0.5Mo
		        byte buffer[] = new byte[512 * 1024];
		        int nbLecture;
		        while ((nbLecture = sourceFile.read(buffer)) != -1){
		            destinationFile.write(buffer, 0, nbLecture);
		        }
		    } catch (IOException e){
		        e.printStackTrace();
		        return false; // Erreur
		    }
		    return true; // Résultat OK
		}



}
