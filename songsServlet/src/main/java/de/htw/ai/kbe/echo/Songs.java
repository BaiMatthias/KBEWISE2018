package de.htw.ai.kbe.echo;

import java.util.ArrayList;
import java.util.List;

public class Songs {

    private List<Song> songs = new ArrayList<Song>();
    private int nextID = 0;

    public Songs(List<Song> songs) {
        this.songs = songs;
        for (Song song : this.songs) {
            if (this.nextID < song.getId()) {
                this.nextID = song.getId();
            }
        }
        this.nextID = this.nextID + 1;
    }

    /**
     * returns all songs as a List
     *
     * @return all songs
     */
    public List<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    /**
     * adds a given song to the Song-List
     *
     * @param newSong Song-Object of new song
     * @return return true if added return false if not added
     * @throws IllegalArgumentException if one field of song is empty, id or song already exists
     */
    public boolean addSong(Song newSong) throws IllegalArgumentException {
        for (Song song : this.songs) {
            //Check ID
            if (newSong.getId() == 0) throw new IllegalArgumentException("No empty ID allowed");
            if (song.getId() == newSong.getId())
                throw new IllegalArgumentException("Song-ID (" + newSong.getId() + ") already exists");
            //Check Artist
            if (newSong.getArtist() == null) throw new IllegalArgumentException("No empty artist allowed");
            //Check Album
            if (newSong.getAlbum() == null) throw new IllegalArgumentException("No empty album allowed");
            //Check Title
            if (newSong.getTitle() == null) throw new IllegalArgumentException("No empty title allowed");
            //Check released
            if (newSong.getReleased() == 0) throw new IllegalArgumentException("No empty released allowed");
            //Check Song is duplikate
            if (song.getAlbum().equals(newSong.getAlbum()) &&
                    song.getArtist().equals(newSong.getArtist()) &&
                    song.getTitle().equals(newSong.getTitle()) &&
                    song.getReleased() == newSong.getReleased())
                throw new IllegalArgumentException("Song already exists");
            //Check
        }
        this.nextID = this.nextID + 1;
        this.songs.add(newSong);
        return true;
    }

    @Override
    public String toString() {
        return "Songs [Song: " + songs + "]";
    }

    /**
     * returns the next free ID
     *
     * @return next ID
     */
    public int getNextID() {
        return this.nextID;
    }

    /**
     * return the song with the given id
     *
     * @param id song id
     * @return the song or null if not exists
     */
    public Song getSong(int id) {
        for (Song song : this.songs) {
            if (song.getId() == id) return song;
        }
        return null;
    }
}
