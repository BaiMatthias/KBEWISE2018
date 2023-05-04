package de.htw.ai.fb4.kbe.tiedmannbaidinger;

/**
 * Diese Klasse kuemmert sich um das Parsen der Eingaben des Nutzers und prueft diese auf Korrektheit
 * und gibt bei korrekter Nutzung die entsprechenden Befehle zur Analyse an die anderen Klasse weiter
 *
 * @author tiedmannbaidinger
 * @version 1.0
 */

import java.util.InputMismatchException;
import java.util.logging.Level;

import de.htw.ai.fb4.kbe.runmerunner.WrongArgumentsException;
import org.apache.commons.cli.*;

public class Parser {
    CommandLineParser parser = new BasicParser();
    Cli cli = null;
    Options options = null;
    CommandLine cmd = null;

    /**
     * Erstellt ein Parser-Objekt, dass dazu verwendet werden kann, die verschiedenen Befehle zu parsen und zu pruefen.
     * @param args Die Befehle, die für die Kommandozeile nutzbar gemacht werden sollen
     * @throws WrongArgumentsException Wenn ein nicht-optionales Argument bei der Eingabe des Nutzers fehlt
     */
    public Parser(String[] args) throws WrongArgumentsException{
        this.cli = new Cli(args);
        this.options = cli.getOptions();
        try {
            this.cmd = this.parser.parse(options, args);
        } catch (ParseException e) {
            throw new WrongArgumentsException("Missing Argument!\n");
        }
    }

    /**
     * Parsed eine Eingabe des Nutzers und gibt diese zur Pruefung weiter
     * @param option Der Befehl, der auf Korrektheit geprueft werden soll
     * @return Der Optionswert, der nach Pruefung korrekt ist und somit weiterverwendet werden kann
     * @throws WrongArgumentsException Wenn eine nicht-optionale Option fehlt
     */
    public String parse(String option) throws WrongArgumentsException{
        return this.checkOption(option);
    }

    /**
     * Prueft einzelne Befehle auf Richtigkeit und ermittelt die dazugehoerigen Attribute, die nach dem Befehl
     * eingegeben wurden
     * @param option Die Option, die geprueft werden soll
     * @return Der zur Option passende Optionswert
     * @throws WrongArgumentsException Wenn eine nicht-optionale Option fehlt
     */
    public String checkOption(String option) throws WrongArgumentsException{
        String optionVal = "";
        if (this.cmd.hasOption(option)) {
            if (this.cmd.getOptionValue(option) == null || this.cmd.getOptionValue(option).isEmpty()) {
                optionVal = "report.txt";
            } else {
                optionVal = this.cmd.getOptionValue(option);
            }

        } else {
            if (this.cli.getOption(option).isRequired()) {
                throw new WrongArgumentsException("Missing Option " + option);
            }
        }
        return optionVal;
    }

}
