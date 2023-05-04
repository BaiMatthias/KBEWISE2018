package de.htw.ai.kbe.echo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OutputHandlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private OutputHandler oph;
    private Song s = new Song();
    private Songs songs;
    private File f;

    @Before
    public void setUp() throws Exception {
        List<Song> slist = new ArrayList<>();
        slist.add(s);
        songs = new Songs(slist);
        oph = new OutputHandler(songs);
        f = new File("songs_1.json");
    }

    @Test
    public void writeToFile() throws IOException {
        oph.writeToFile(f.getAbsolutePath());
        String text = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));

        assertTrue(text.contains("\"title\":\"\",\"artist\":\"\","));
    }

   /* @Test f.Readable(false, false) funktioniert nicht
    public void writeToFileError() throws IOException {
        thrown.expect(IOException.class);
        File f = new File("songs2.json");
        oph.writeToFile(f.getAbsolutePath());
        String text = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));

        assertTrue(text.contains("\"title\":\"\",\"artist\":\"\","));
    }*/

    @After
    public void deleteOutputFile() {
        if (f != null && f.exists()) {
            f.delete();
        }
    }
}