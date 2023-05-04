package de.htw.ai.kbe.interfaces;

import de.htw.ai.kbe.bean.Song;

import java.util.Collection;

public interface ISongStorage {

    Collection<Song> getSongs();

    //void setSongs(ConcurrentHashMap<Integer, Song> songMap);
    boolean addSong(Song newSong) throws IllegalArgumentException;

    String toString();

    Song getSong(int id);

    boolean updateSong(int id, Song song);

    boolean deleteSong(Integer id);

    void loadSongs();

}
