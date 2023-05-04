package de.htw.ai.kbe.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.interfaces.ISongStorage;


import java.io.InputStream;
import java.util.Collection;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestSongStorage implements ISongStorage {

    ConcurrentHashMap<Integer, Song> songMap;

    public TestSongStorage(){
        songMap = new ConcurrentHashMap<>();
        loadSongs();
    }

    public void loadSongs(){
        try (InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream("testsongs.json")) {
            ObjectMapper objMapper = new ObjectMapper();
            List<Song> songs = objMapper.readValue(fileStream, new TypeReference<List<Song>>() {});
            for(Song s : songs){
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
     * @throws IllegalArgumentException if one field of song is empty, id or song already exists
     */
    public synchronized boolean addSong(Song newSong) throws IllegalArgumentException {
        for (Song song : this.songMap.values()) {
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
            //Check Song is duplicate
            if (song.getAlbum().equals(newSong.getAlbum()) &&
                    song.getArtist().equals(newSong.getArtist()) &&
                    song.getTitle().equals(newSong.getTitle()) &&
                    song.getReleased() == newSong.getReleased())
                throw new IllegalArgumentException("Song already exists");
            //Check
        }
        int nextId = this.songMap.size()+1;
        if (nextId == newSong.getId()){
            this.songMap.put(nextId, newSong);
        }else{
            newSong.setId(nextId);
            this.songMap.put(nextId, newSong);
        }
        return true;
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
        if(this.songMap.get(id) != null){
            try {
                //Check ID
                if (song.getId() == null) throw new IllegalArgumentException("No empty ID allowed");
                if(song.getId() == 0) throw new IllegalArgumentException("No empty ID allowed");
                //Check Artist
                if (song.getArtist() == null) throw new IllegalArgumentException("No empty artist allowed");
                //Check Album
                if (song.getAlbum() == null) throw new IllegalArgumentException("No empty album allowed");
                //Check Title
                if (song.getTitle() == null) throw new IllegalArgumentException("No empty title allowed");
                //Check released
                if (song.getReleased() == 0) {throw new IllegalArgumentException("No empty released allowed");}
            } catch(IllegalArgumentException d){ return false;}
            this.songMap.put(id, song);
            song.setId(id); // Vorsichtshalber
            return true;
        } else return false;
    }


    public synchronized boolean deleteSong(Integer id) {
        if(this.songMap.remove(id) != null){
            return true;
        } else return false;

    }
}