package de.htw.ai.fb4.kbe.runmerunner;

/**
 * Diese Klasse kuemmert sich um die Erstellung einer Datei und die Speicherung des erstellten Textes in dieselbige
 * @author tiedmannbaidinger
 * @version 1.0
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    /**
     * Schreibt den erstellten Text in eine Datei, die frisch erstellt wird
     * @param path Der Dateipfad, an dem die Datei erstellt werden soll
     * @param output Der Text, der in die Datei geschrieben werden soll
     * @throws IOException Wenn der Dateipfad ungueltig ist
     */
    public static void writeFile(String path, String output) throws IOException {
            if (path.trim().isEmpty()){
            path = "report.txt";
        }
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(output);
                writer.close();
            }catch(IOException e){
               // System.out.println("Cannot write file");
                throw new IOException("Cannot write file");
            }
    }



}
