package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.helper.TestSongStorage;
import de.htw.ai.kbe.interfaces.ISongStorage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class SongStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private Song mockSong;
    ISongStorage songStorage;

    @Before
    public void setUp() {
        songStorage = new TestSongStorage();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSong() {
        Song s = songStorage.getSong(1);
        assertEquals("Testartist", s.getArtist());
        assertEquals("Testlied", s.getTitle());
        assertEquals("Testalbum", s.getAlbum());
        assertTrue(s.getReleased() == 2018);
    }

    @Test
    public void testGetSongWrongId() {
        Song s = songStorage.getSong(100);
        assertNull(s);
    }

    /**
     * *************addSong*************************
     */

    @Test
    public void addSongNormal() {
        Song s = new Song();
        s.setId(30);
        s.setTitle("NeuerTitel");
        s.setArtist("NeuerArtist");
        s.setAlbum("NeuesAlbum");
        s.setReleased(2018);
        songStorage.addSong(s);
        assertEquals(3, songStorage.getSongs().size());

        when(mockSong.getId()).thenReturn(3);
        when(mockSong.getTitle()).thenReturn("NeuerTitel");
        when(mockSong.getArtist()).thenReturn("NeuerArtist");
        when(mockSong.getAlbum()).thenReturn("NeuesAlbum");
        when(mockSong.getReleased()).thenReturn(2018);

        s = songStorage.getSong(3);
        assertEquals(mockSong.getId(), s.getId());
        assertEquals(mockSong.getTitle(), s.getTitle());
        assertEquals(mockSong.getArtist(), s.getArtist());
        assertEquals(mockSong.getAlbum(), s.getAlbum());
        assertEquals(mockSong.getReleased(), s.getReleased());

    }

    @Test
    public void addSongEmptySong() {
        Song s = new Song();
        s.setId(3);
        s.setAlbum("");
        s.setArtist("");
        s.setTitle("");
        s.setReleased(1);
        boolean result = songStorage.addSong(s);

        s = songStorage.getSong(3);
        assertTrue(s.getId() == 3);
        assertEquals("", s.getTitle());
        assertEquals("", s.getArtist());
        assertEquals("", s.getAlbum());
        assertTrue(s.getReleased() == 1);
    }

    @Test
    public void addSongDoubleId() {
        thrown.expect(IllegalArgumentException.class);
        Song song1 = new Song();
        song1.setId(1);
        song1.setTitle("NeuerTitel");
        song1.setArtist("NeuerArtist");
        song1.setAlbum("NeuesAlbum");
        song1.setReleased(2018);
        songStorage.addSong(song1);
    }


    @Test
    public void addSongTitleNull() {

        thrown.expect(IllegalArgumentException.class);
        Song s0 = new Song();
        s0.setId(102);
        s0.setTitle(null);
        s0.setArtist("NeuerArtist");
        s0.setAlbum("NeuesAlbum");
        s0.setReleased(2018);
        songStorage.addSong(s0);

    }

    @Test
    public void addSongArtistNull() {

        thrown.expect(IllegalArgumentException.class);
        Song s0 = new Song();
        s0.setId(103);
        s0.setTitle("MeinTitel");
        s0.setArtist(null);
        s0.setAlbum("NeuesAlbum");
        s0.setReleased(2018);

        songStorage.addSong(s0);
    }

    @Test
    public void addSongAlbumNull() {

        thrown.expect(IllegalArgumentException.class);
        Song s2 = new Song();
        s2.setId(104);
        s2.setTitle("MeinTitel");
        s2.setArtist("MeinArtist");
        s2.setAlbum(null);
        s2.setReleased(2018);
        songStorage.addSong(s2);
    }

    @Test
    public void addSongReleasedZero() {

        thrown.expect(IllegalArgumentException.class);
        Song s3 = new Song();
        s3.setId(105);
        s3.setTitle("MeinTitel");
        s3.setArtist("MeinArtist");
        s3.setAlbum("MeinAlbum");
        s3.setReleased(0);
        songStorage.addSong(s3);
    }

    @Test
    public void updateSongValidID() {
        Song songTest = new Song();
        songTest.setId(2);
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);

        songStorage.updateSong(2, songTest);
        Song song = songStorage.getSong(2);
        assertEquals("UpdatedArtist", song.getArtist());
        assertEquals("UpdatedLied", song.getTitle());
        assertEquals("UpdatedAlbum", song.getAlbum());
        assertTrue(song.getReleased() == 2020);
    }

    @Test
    public void updateSongInvalidID() {
        Song songTest = new Song();
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle("UpdatedLied");
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(3, songTest);
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
    public void updateSongNullID() {
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
        songTest.setId(2);
        songTest.setArtist("UpdatedArtist");
        songTest.setTitle(null);
        songTest.setAlbum("UpdatedAlbum");
        songTest.setReleased(2020);
        boolean result = songStorage.updateSong(songTest.getId(), songTest);
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

    @Test
    public void deleteSongValidID() {
        boolean result = songStorage.deleteSong(2);
        Song s = songStorage.getSong(2);
        assertNull(s);
        assertTrue(result);
    }

    @Test
    public void deleteSongInvalidID() {
        boolean result = songStorage.deleteSong(4);
        assertFalse(result);
    }

}

