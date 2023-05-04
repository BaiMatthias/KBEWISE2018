package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import de.htw.ai.fb4.kbe.runmerunner.FileHandler;

import org.junit.Test;
import  org.junit.After;
import java.nio.file.*;;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private String path;

    @Test
    public void testFileSuccessfulCreatedWithPathFilled() throws IOException {
        path = "test.txt";
        FileHandler.writeFile(path, "Test");
        File f = new File(path);
        assertTrue(f.exists());

    }

    @Test
    public void testFileSuccessfulCreatedWithoutPathFilled() throws IOException {
        path = "report.txt";
        FileHandler.writeFile("", "");
        File f = new File(path);
        assertTrue(f.exists());
    }

    @Test
    public void testFileSuccessfulCreatedWithPathFilledWithSpaces() throws IOException {
        path = "report.txt";
        FileHandler.writeFile("        ", "");
        File f = new File(path);
        assertTrue(f.exists());
    }

    @Test (expected = IOException.class)
    public void testFileInvalidFilename() throws IOException {
        path = "???";
        FileHandler.writeFile(path, "Test");
        File f = new File(path);
        //assertTrue(f.exists());
    }

    @Test
    public void testFileContainsCorrectContent() throws IOException {
        FileHandler.writeFile("", "Mein Text");
        path = "report.txt";
        String text = new String(Files.readAllBytes(Paths.get(path)));
        assertEquals("Mein Text", text);
    }

    @After
    public void deleteOutputFile(){
        File f;
        if(!(path == null || path.isEmpty())){
             f = new File(path);
        }
        else f = new File("report.txt");
        if(f.exists()) f.delete();
    }
}
