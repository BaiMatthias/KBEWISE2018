package de.htw.ai.kbe.interfaces;

import de.htw.ai.kbe.bean.User;

import java.util.Collection;
import java.util.List;

public interface IUserStorage {

    User getUser(String userID);

    Collection<User> getUsers();

    String getTokenFromUser(String userID);

    int getUserByToken(List<String> l);

    boolean isUserAuth(String token);

    void addTokenToUser(String userId, String token);
}
