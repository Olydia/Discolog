package fr.limsi.application.FRversion;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

public class WriteHistory {
	public static ReentrantLock copyLock = new ReentrantLock();
	public static ReentrantLock writeLock = new ReentrantLock();
		
		public boolean copyFile(File source, File dest) {
		    try (InputStream sourceFile = new java.io.FileInputStream(source);
		            OutputStream destinationFile = new FileOutputStream(dest, true)) {
		        // Lecture par segment de 0.5Mo
		        byte buffer[] = new byte[512 * 1024];
		        int nbLecture;
		        while ((nbLecture = sourceFile.read(buffer)) != -1){
		            destinationFile.write(buffer, 0, nbLecture);
		        }
		    	destinationFile.flush();
				destinationFile.close();
		    } catch (IOException e){
		        e.printStackTrace();
		        return false; // Erreur
		    }
		    return true; // R�sultat OK
		}



		public void write (String content, File file){
			writeLock.lock();
			try (FileOutputStream fop = new FileOutputStream(file, true)) {

				// if file doesn't exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				writeLock.unlock();
			}
		}
}
