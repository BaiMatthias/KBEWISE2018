package de.htw.ai.kbe.echo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class SongTest {

    @Mock
    private Song song;

    @Before
    public void setUp() throws Exception {
        song = new Song(5, "MeinLied", "MeinArtist", "MeinAlbum", 2018);
    }

    @Test
    public void getId() {
        assertEquals(5, song.getId());
    }

    @Test
    public void getTitle() {
        assertEquals("MeinLied", song.getTitle());
    }

    @Test
    public void getArtist() {
        assertEquals("MeinArtist", song.getArtist());
    }

    @Test
    public void getAlbum() {
        assertEquals("MeinAlbum", song.getAlbum());
    }

    @Test
    public void getReleased() {
        assertEquals(2018, song.getReleased());
    }
}