package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.Song;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class SongStorageTest {

    private static EntityManagerFactory emf;
    private static DBSongStorageDAO songStorage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("songRXDB-Test-PU");
        songStorage = new DBSongStorageDAO(emf);
    }

    @Test
    public void testGetSong() {
        Song s = songStorage.getSong(1);
        assertEquals("Justin Timberlake", s.getArtist());
        assertEquals("Can't Stop the Feeling", s.getTitle());
        assertEquals("Trolls", s.getAlbum());
        assertTrue(s.getReleased() == 2016);
    }

    @Test
    public void testGetSongWrongId() {
        Song s = songStorage.getSong(100);
        assertNull(s);
    }

    @Test
    public void addSongNormal() {
        Song s = new Song();
        s.setTitle("NeuerTitel2");
        s.setArtist("NeuerArtist2");
        s.setAlbum("NeuesAlbum2");
        s.setReleased(2018);
        boolean result = songStorage.addSong(s);
        assertTrue(result);
    }

    @Test
    public void addSongEmptySong() {
        Song s = new Song();
        s.setAlbum("");
        s.setArtist("");
        s.setTitle("");
        s.setReleased(1);
        boolean result = songStorage.addSong(s);
        assertTrue(result);
    }

    @Test
    public void addSongTitleNull() {
        Song s0 = new Song();
        s0.setTitle(null);
        s0.setArtist("NeuerArtist");
        s0.setAlbum("NeuesAlbum");
        s0.setReleased(2018);
        boolean result = songStorage.addSong(s0);
        assertFalse(result);
    }

    @Test
    public void addSongArtistNull() {
        Song s0 = new Song();
        s0.setTitle("MeinTitel");
        s0.setArtist(null);
        s0.setAlbum("NeuesAlbum");
        s0.setReleased(2018);
        boolean result = songStorage.addSong(s0);
        assertFalse(result);
    }

    @Test
    public void addSongAlbumNull() {
        Song s2 = new Song();
        s2.setTitle("MeinTitel");
        s2.setArtist("MeinArtist");
        s2.setAlbum(null);
        s2.setReleased(2018);
        boolean result = songStorage.addSong(s2);
        assertFalse(result);
    }

    @Test
    public void addSongReleasedZero() {
        Song s3 = new Song();
        s3.setTitle("MeinTitel");
        s3.setArtist("MeinArtist");
        s3.setAlbum("MeinAlbum");
        s3.setReleased(0);
        boolean result = songStorage.addSong(s3);
        assertFalse(result);
    }

    @Test
    public void updateSongValidID() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(2, songTest);
        assertTrue(result);
    }

    @Test
    public void updateSongInvalidID() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(500, songTest);
        assertFalse(result);
    }

    @Test
    public void updateSongZeroID() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(0, songTest);
        assertFalse(result);
    }


    @Test
    public void updateSongArtistEmpty() {
        Song songTest = new Song();
        songTest.setArtist(null);
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(2, songTest);
        assertFalse(result);
    }

    @Test
    public void updateSongAlbumEmpty() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum(null);
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(2, songTest);
        assertFalse(result);
    }

    @Test
    public void updateSongTitleEmpty() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle(null);
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(2, songTest);
        assertFalse(result);
    }

    @Test
    public void updateSongReleasedZero() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(0);
        boolean result = songStorage.updateSong(2, songTest);
        assertFalse(result);
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

}

