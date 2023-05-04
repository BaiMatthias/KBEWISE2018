package de.htw.ai.kbe.interfaces;

import de.htw.ai.kbe.bean.Songlist;

import java.util.Collection;

public interface ISonglistStorage {

    boolean addList(Songlist newSonglist) throws IllegalArgumentException;

    boolean deleteList(int songlistId, int userId) throws IllegalArgumentException;

    boolean isValid(Songlist songlist);

    Songlist getSonglist(int songlistId, int userId);

    Collection<Songlist> getAllUserSonglists(int userId, int authUser);

    boolean updateSonglist(int songlistId, int userId, Songlist songlist);
}
