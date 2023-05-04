package de.htw.ai.kbe.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.interfaces.ISongStorage;


import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SongStorage implements ISongStorage {

    ConcurrentHashMap<Integer, Song> songMap;

    public SongStorage() {
        songMap = new ConcurrentHashMap<>();
        loadSongs();
    }

    public void loadSongs() {
        try (InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream("songs.json")) {
            ObjectMapper objMapper = new ObjectMapper();
            List<Song> songs = objMapper.readValue(fileStream, new TypeReference<List<Song>>() {
            });
            for (Song s : songs) {
                songMap.put(s.getId(), s);
            }
        } catch (Exception e) {
            songMap.clear();
        }
    }

    /**
     * returns all songs as a List
     *
     * @return all songs
     */
    public Collection<Song> getSongs() {
        return this.songMap.values();
    }

    public void setSongs(ConcurrentHashMap<Integer, Song> songMap) {
        this.songMap = songMap;
    }

    /**
     * adds a given song to the Song-List
     *
     * @param newSong Song-Object of new song
     * @return return true if added return false if not added
     */
    public synchronized boolean addSong(Song newSong) {
        try {
            for (Song song : this.songMap.values()) {
                //Check ID
                if (newSong.getId() == null) throw new IllegalArgumentException("No empty ID allowed");
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
                //Check Song is duplicate
                try {
                    if (song.getAlbum().equals(newSong.getAlbum()) &&
                            song.getArtist().equals(newSong.getArtist()) &&
                            song.getTitle().equals(newSong.getTitle()) &&
                            song.getReleased() == newSong.getReleased())
                        throw new IllegalArgumentException("Song already exists");
                    //Check
                } catch (NullPointerException e) {
                    System.out.println("Somthing is empty");
                }
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        if (this.songMap.containsKey(newSong.getId())) {
            return false;
        } else {
            this.songMap.put(newSong.getId(), newSong);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Songs [Song: " + this.songMap.values() + "]";
    }

    /**
     * return the song with the given id
     *
     * @param id song id
     * @return the song or null if not exists
     */
    public Song getSong(int id) {
        return this.songMap.get(id);
    }

    @Override
    public synchronized boolean updateSong(int id, Song song) {
        if (this.songMap.get(id) != null) {
            try {
                //Check ID
                if (song.getId() == null) throw new IllegalArgumentException("No empty ID allowed");
                if (song.getId() == 0) throw new IllegalArgumentException("No empty ID allowed");
                //Check Artist
                if (song.getArtist() == null) throw new IllegalArgumentException("No empty artist allowed");
                //Check Album
                if (song.getAlbum() == null) throw new IllegalArgumentException("No empty album allowed");
                //Check Title
                if (song.getTitle() == null) throw new IllegalArgumentException("No empty title allowed");
                //Check released
                if (song.getReleased() == 0) {
                    throw new IllegalArgumentException("No empty released allowed");
                }
            } catch (IllegalArgumentException d) {
                return false;
            }
            this.songMap.put(id, song);
            song.setId(id); // Vorsichtshalber
            return true;
        } else return false;
    }


    public synchronized boolean deleteSong(Integer id) {
        if (this.songMap.remove(id) != null) {
            return true;
        } else return false;

    }
}