package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import org.junit.Before;
import org.junit.Test;

import javax.swing.text.html.Option;

import static org.junit.Assert.*;

public class CliTest {
    private Cli cli1, cli2;
    private String[] args1 = {"-c", "Test", "-o", "Test"};
    private String[] args2 = {};

    @Before
    public void setUp() throws Exception {
        this.cli1 = new Cli(this.args1);
        this.cli2 = new Cli(this.args2);
    }

    @Test
    public void getOptionsTestNormal() {
        assertTrue(this.cli1.getOptions().hasOption("c"));
        assertTrue(this.cli1.getOptions().hasOption("o"));
    }

    @Test
    public void getOptionsTestNoOption() {
        assertFalse(this.cli1.getOptions().hasOption("t"));
        assertFalse(this.cli1.getOptions().hasOption("q"));
    }

    @Test
    public void getOptionTestNormal() {
        assertEquals("[ option: c  [ARG] :: Classname ]", this.cli1.getOption("c").toString());
        assertEquals("[ option: o  [ARG] :: Output ]", this.cli1.getOption("o").toString());
    }

    @Test
    public void getOptionTestNoOption() {
        assertSame(null, this.cli1.getOption("t"));
    }

    @Test
    public void getArgsTestNormal() {
        assertSame(this.args1, cli1.getArgs());
    }

    @Test
    public void getArgsTestEmptyArgs() {
        assertSame(this.args2, cli2.getArgs());
    }
}