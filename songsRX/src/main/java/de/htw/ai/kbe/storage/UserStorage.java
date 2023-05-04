package de.htw.ai.kbe.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.interfaces.IUserStorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorage implements IUserStorage {

    private ConcurrentHashMap<String, User> userMap;

    public UserStorage() {
        userMap = new ConcurrentHashMap<>();
        readUsers();
    }

    public void readUsers() {
        try (InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream("users.json")) {
            ObjectMapper objMapper = new ObjectMapper();
            List<User> users = objMapper.readValue(fileStream, new TypeReference<List<User>>() {
            });
            for (User user : users) {
                userMap.put(user.getUserId(), user);
            }
        } catch (IOException e) {
            userMap.clear();
        }
    }


    @Override
    public User getUser(String userID) {
        return this.userMap.get(userID);
    }

    @Override
    public Collection<User> getUsers() {
        return this.userMap.values();
    }

    @Override
    public String getTokenFromUser(String userID) {
        return this.userMap.get(userID).getToken();
    }

    @Override
    public boolean isUserAuth(String token) {
        for (User u : this.userMap.values()) {
            if (u.getToken() != null && u.getToken().equalsIgnoreCase(token)) {
                return true;
            }
        }
        return false;
    }
}
