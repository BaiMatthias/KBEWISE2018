package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import de.htw.ai.fb4.kbe.runmerunner.WrongArgumentsException;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.cli.*;

import java.util.InputMismatchException;

import static org.junit.Assert.*;


public class ParserTest {


    private CommandLineParser parser;
    private Parser par;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIfCIsGiven() {
        try {
            String[] args = {"-c", "Test"};
            par = new Parser(args);
            String coutput = par.parse("c");
            assertEquals("Test", coutput);
        } catch (WrongArgumentsException e) {
            assertFalse(true);
        }
    }

    @Test(expected = WrongArgumentsException.class)
    public void testArgumentsEmpty() throws WrongArgumentsException {
        String[] args = {};
        par = new Parser(args);
        String coutput = par.parse("c");
    }

    @Test(expected = WrongArgumentsException.class)
    public void testCWithoutArguments() throws WrongArgumentsException {
        String[] args = {"-c"};
        par = new Parser(args);
        String coutput = par.parse("c");
    }

    @Test
    public void testOWithoutArguments() {
        try {
            String[] args = {"-c", "Test", "-o"};
            par = new Parser(args);
            String coutput = par.parse("c");
            String ooutput = par.parse("o");
            assertEquals("report.txt", ooutput);
        } catch (WrongArgumentsException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testIfOIsGiven() {
        try {
            String[] args = {"-c", "Test", "-o", "Test"};
            par = new Parser(args);
            String coutput = par.parse("c");
            String ooutput = par.parse("o");
            assertEquals("Test", ooutput);
        } catch (WrongArgumentsException e) {
            assertFalse(true);
        }
    }

    @Test(expected = WrongArgumentsException.class)
    public void testJustOInput() throws WrongArgumentsException {
        String[] args = {"-o", "Test"};
        par = new Parser(args);
        String coutput = par.parse("c");
        String ooutput = par.parse("o");
    }
}
