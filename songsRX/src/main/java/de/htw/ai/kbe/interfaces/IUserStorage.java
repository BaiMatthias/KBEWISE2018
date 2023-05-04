package de.htw.ai.kbe.interfaces;

import de.htw.ai.kbe.bean.User;

import java.util.Collection;

public interface IUserStorage {

    User getUser(String userID);

    Collection<User> getUsers();

    String getTokenFromUser(String userID);

    boolean isUserAuth(String token);

    void readUsers();


}
