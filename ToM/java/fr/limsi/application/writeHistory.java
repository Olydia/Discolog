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
