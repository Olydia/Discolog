package fr.limsi.application;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

public class writeHistory {

	public void write(String txt){
		try{
			File ff=new File("C:\\Users\\dhouib\\Desktop\\test.txt");
			ff.createNewFile();
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
	/*
			//création ou ajout dans le fichier texte
			try {
				FileWriter fw = new FileWriter (fichier);
				BufferedWriter bw = new BufferedWriter (fw);
				PrintWriter fichierSortie = new PrintWriter (bw);
					fichierSortie.println (chaine+"\n test de lecture et écriture !!");
				fichierSortie.close();
				System.out.println("Le fichier " + fichier + " a été créé!");
				return chaine;
			}
			catch (Exception e){
				System.out.println(e.toString());
			}*/
			return chaine;
		}



}
