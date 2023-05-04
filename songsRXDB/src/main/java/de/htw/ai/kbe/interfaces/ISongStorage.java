package de.htw.ai.kbe.interfaces;

import de.htw.ai.kbe.bean.Song;

import java.util.Collection;

public interface ISongStorage {

    Collection<Song> getSongs();

    //void setSongs(ConcurrentHashMap<Integer, Song> songMap);
    // In der Vorlesung war Rückgabewert ein Integer?
    boolean addSong(Song newSong) throws IllegalArgumentException;

    Song getSong(int id);

    boolean updateSong(int id, Song song);
}
