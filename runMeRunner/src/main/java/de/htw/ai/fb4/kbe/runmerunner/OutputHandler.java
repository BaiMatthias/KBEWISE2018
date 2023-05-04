package de.htw.ai.fb4.kbe.runmerunner;

/**
 * Diese Klasse kuemmert sich um die Formatierung des Textes, bevor dieser in die Datei geschrieben wird
 *
 * @author tiedmannbaidinger
 * @version 1.0
 */


import java.util.ArrayList;
import java.util.HashMap;

public class OutputHandler {

            private ArrayList<String> methodsWithoutRM;
            private ArrayList<String> methodsWithRM;
            private HashMap<String, String> methodsException;

    /**
     * Erstellt ein Objekt, dass die Listen enthaelt, die der ReflectionHandler erstellt hat
     * @param rh Ein Objekt der Klasse ReflectionHandler
     */
    public OutputHandler(ReflectionHandler rh){
        this.methodsWithoutRM = rh.getMethodsWithoutRM();
        this.methodsWithRM = rh.getMethodsWithRM();
        this.methodsException = rh.getMethodsException();
    }

    /**
     * Erstellt den formatierten Output abhaengig von den Daten, die der ReflectionHandler gesammelt hat
     * @return Der formatierte Text
     */
    public String createOutput(){
        StringBuilder sb = new StringBuilder();
        sb.append("Methodennamen ohne @RunMe:\n");

        for (String method: methodsWithoutRM) {
            sb.append(method+"\n");
        }
        sb.append("\nMethodennamen mit @RunMe:\n");
        for (String method:methodsWithRM) {
            sb.append(method+"\n");
        }
        sb.append("\n");
        sb.append("Nicht invokierbare Methoden mit @RunMe:\n");
        for (HashMap.Entry<String, String> entry : methodsException.entrySet()) {
            sb.append(entry.getKey()+": " + entry.getValue()+"\n");

        }

        return sb.toString();
    }
}
