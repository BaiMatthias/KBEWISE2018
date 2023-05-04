package de.htw.ai.fb4.kbe.tiedmannbaidinger;

/**
 * Diese Klasse kuemmert sich um die Erstellung der Befehle fuer die Kommandozeile, damit diese
 * spaeter vom Nutzer verwendet werden koennen
 *
 * @author tiedmannbaidinger
 * @version 1.0
 * */

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class Cli {
    private String[] args = null;
    private Options options = new Options();

    /**
     * Erstellt die Befehle und weist ihnen verschiedene Parameter zu
     * @param args Die Befehle, die hinzugefuegt werden soll
     */
    public Cli(String[] args) {
        this.args = args;
        Option cOpt = OptionBuilder.withArgName("class")
                .hasArg()
                .withDescription("Classname")
                .isRequired()
                .create("c");
        options.addOption(cOpt);
        Option oOpt = OptionBuilder.withArgName("output")
                .hasOptionalArg()
                .withDescription("Output")
                .create("o");
        options.addOption(oOpt);
    }


    public Options getOptions() {
        return this.options;
    }

    public Option getOption(String option) {
        return this.options.getOption(option);
    }

    public String[] getArgs() {
        return this.args;
    }
}
