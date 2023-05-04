package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.helper.TestUserStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserStorageTest {

    private IUserStorage userStorage;

    @Before
    public void setUp() {
        userStorage = new TestUserStorage();
    }

    @Test
    public void getUserValidUserId() {
        User u = userStorage.getUser("mmuster");
        assertNotNull(u);
    }

    @Test
    public void getUserInvalidUserId() {
        User u = userStorage.getUser("blabla");
        assertNull(u);
    }

    @Test
    public void getUsersTest() {
        assertTrue(userStorage.getUsers().size() == 2);
    }

    @Test
    public void getTokenFromUserTest() {
        User u = userStorage.getUser("mmuster");
        u.setToken("123456abcdef");
        assertEquals("123456abcdef", userStorage.getTokenFromUser("mmuster"));
    }

    @Test
    public void isUserAuthValidToken() {
        User u = userStorage.getUser("mmuster");
        u.setToken("123456abcdef");
        boolean result = userStorage.isUserAuth("123456abcdef");
        assertTrue(result);

    }

    @Test
    public void isUserAuthInvalidToken() {
        User u = userStorage.getUser("mmuster");
        u.setToken("123456abcdef");
        boolean result = userStorage.isUserAuth("Blabla");
        assertFalse(result);

    }

}