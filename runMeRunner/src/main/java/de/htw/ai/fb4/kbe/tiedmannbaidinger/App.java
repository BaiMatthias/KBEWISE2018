package de.htw.ai.fb4.kbe.tiedmannbaidinger;

/**
 * Diese Klasse stellt den Startpunkt des Programms dar, hier werden die verschiedenen Komponenten zusammengefuehrt
 * @author tiedmannbaidinger
 * @version 1.0
 */



import java.io.IOException;


import de.htw.ai.fb4.kbe.runmerunner.*;

public class App {
    public static void main(String[] args) {
        try {
            //parse options
            Parser par = new Parser(args);
            String coutput = par.parse("c");
            String ooutput = par.parse("o");

            //Uebung print output
            String output = "Input class: " + coutput + "\nReport: " + ooutput;
            System.out.println(output);

            //find Methods
            ReflectionHandler rh = new ReflectionHandler(coutput);
            rh.findMethods();

            //generate Output
            OutputHandler oh = new OutputHandler(rh);
            String fianlOutput = oh.createOutput();

            //write Output
            FileHandler.writeFile(ooutput, fianlOutput);
        } catch (WrongArgumentsException e) {
            System.out.println(getHelpText(e.getMessage()));
        } catch (IOException e) {
            System.out.println(getHelpText(e.getMessage()));
        }
    }

    /**
     * Hilft dem Nutzer, die richtige Syntax in die Kommandozeile einzugeben, falls dieser bereits eine falsche Eingabe
     * getaetigt hat
     * @param error Der Fehler, der vom Nutzer ausgeloest wurde
     * @return Der Hilfetext
     */
    public static String getHelpText(String error){
        String helptext = "";
        helptext += error;
        helptext += "\nSyntax: java -jar runMeRunner-x.x-jar-with-dependencies.jar -c <classname> [-o <output>]";
        return helptext;
    }
}
