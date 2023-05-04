package de.htw.ai.kbe.echo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SongsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Song> songs;


    private Songs songsObj;


    @Mock
    private Song mockSong;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Song song1 = new Song(1, "Testlied", "Testinterpret", "Testalbum", 2018);
        Song song2 = new Song(2, "Lied", "Interpret", "Album", 2017);
        songs = new ArrayList<>();
        this.songs.add(song1);
        this.songs.add(song2);
        this.songsObj = new Songs(new ArrayList<>());

    }

    /**
     * *************setSongs*************************
     */

    @Test
    public void setSongsNormal() {
        this.songsObj.setSongs(songs);
        assertEquals(2, this.songsObj.getSongs().size());
    }


    /**
     * *************getSong*************************
     */

    @Test
    public void getSongNormal() {
        this.songsObj.setSongs(songs);
        when(mockSong.getId()).thenReturn(2);
        when(mockSong.getTitle()).thenReturn("Lied");
        when(mockSong.getArtist()).thenReturn("Interpret");
        when(mockSong.getAlbum()).thenReturn("Album");
        when(mockSong.getReleased()).thenReturn(2017);

        Song song = songsObj.getSong(2);
        song.getId();
        assertEquals(mockSong.getId(), song.getId());
        assertEquals(mockSong.getTitle(), song.getTitle());
        assertEquals(mockSong.getArtist(), song.getArtist());
        assertEquals(mockSong.getAlbum(), song.getAlbum());
        assertEquals(mockSong.getReleased(), song.getReleased());
    }

    @Test
    public void getSongWrongUri() {
        this.songsObj.setSongs(songs);
        Song s = this.songsObj.getSong(4);
        assertNull(s);
    }

    @Test
    public void getSongSongListEmpty() {
        Song s = this.songsObj.getSong(4);
        assertNull(s);
    }

    /**
     * *************addSong*************************
     */

    @Test
    public void addSongNormal() {
        Song s = new Song(3, "NeuerTitel", "NeuerArtist", "NeuesAlbum", 2018);
        songsObj.addSong(s);
        assertEquals(1, songsObj.getSongs().size());

        when(mockSong.getId()).thenReturn(3);
        when(mockSong.getTitle()).thenReturn("NeuerTitel");
        when(mockSong.getArtist()).thenReturn("NeuerArtist");
        when(mockSong.getAlbum()).thenReturn("NeuesAlbum");
        when(mockSong.getReleased()).thenReturn(2018);

        s = songsObj.getSong(3);
        assertEquals(mockSong.getId(), s.getId());
        assertEquals(mockSong.getTitle(), s.getTitle());
        assertEquals(mockSong.getArtist(), s.getArtist());
        assertEquals(mockSong.getAlbum(), s.getAlbum());
        assertEquals(mockSong.getReleased(), s.getReleased());

    }

    @Test

    public void addSongEmptySong() {
        Song s = new Song();
        songsObj.addSong(s);
        assertEquals(1, songsObj.getSongs().size());

        s = songsObj.getSong(0);
        assertEquals(0, s.getId());
        assertEquals("", s.getTitle());
        assertEquals("", s.getArtist());
        assertEquals("", s.getAlbum());
        assertEquals(0, s.getReleased());
    }

    @Test
    public void addSongDoubleId() {
        thrown.expect(IllegalArgumentException.class);
        this.songsObj.setSongs(songs);
        Song song1 = new Song(1, "Testlied", "Testinterpret", "Testalbum", 2018);
        songsObj.addSong(song1);
    }


    @Test
    public void addSongTitleNull() {
        this.songsObj.setSongs(songs);
        thrown.expect(IllegalArgumentException.class);
        Song s0 = new Song(11, null, "MeinArtist", "MeinAlbum", 2010);
        songsObj.addSong(s0);

    }

    @Test
    public void addSongArtistNull() {
        this.songsObj.setSongs(songs);
        thrown.expect(IllegalArgumentException.class);
        Song s1 = new Song(12, "MeinTitel", null, "MeinAlbum", 2010);
        songsObj.addSong(s1);
    }

    @Test
    public void addSongAlbumNull() {
        this.songsObj.setSongs(songs);
        thrown.expect(IllegalArgumentException.class);
        Song s2 = new Song(13, "MeinTitel", "MeinArtist", null, 1200);
        songsObj.addSong(s2);
    }

    @Test
    public void addSongReleasedZero() {
        this.songsObj.setSongs(songs);
        thrown.expect(IllegalArgumentException.class);
        Song s3 = new Song(14, "MeinTitel", "MeinArtist", "MeinAlbum", 0);
        songsObj.addSong(s3);
    }

    /**
     * *************toString*************************
     */

    @Test
    public void toStringNormal() {
        String songstring = "[\n";
        songstring += "Id: 1\n";
        songstring += "Title: Testlied\n";
        songstring += "Artist: Testinterpret\n";
        songstring += "Album: Testalbum\n";
        songstring += "Released: 2018\n";
        songstring += ", \n";
        songstring += "Id: 2\n";
        songstring += "Title: Lied\n";
        songstring += "Artist: Interpret\n";
        songstring += "Album: Album\n";
        songstring += "Released: 2017";
        songstring += "\n]";
        assertEquals(songstring, songs.toString());
    }
}