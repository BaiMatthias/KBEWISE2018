package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserStorageTest {

    private static EntityManagerFactory emf;
    private static DBUserStorageDAO userStorage;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("songRXDB-Test-PU");
        userStorage = new DBUserStorageDAO(emf);
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
        assertEquals(userStorage.getUsers().size(), 2);
    }

    @Test
    public void getTokenFromUserTest() {
        User u = userStorage.getUser("mmuster");
        userStorage.addTokenToUser(u.getUserId(), "123456abcdef");
        assertEquals("123456abcdef", userStorage.getTokenFromUser("mmuster"));
    }

    @Test
    public void getUserTestInvalidParam() {
        assertNull( userStorage.getUser("invalidPara"));
    }

    @Test
    public void getUserByTokenTest() {
        //add Token to map
        User u = userStorage.getUser("mmuster");
        String token = "123456abcdef";
        userStorage.addTokenToUser(u.getUserId(), token);
        assertEquals(token, userStorage.getTokenFromUser("mmuster"));

        //test if Token returns user
        List<String> testheader = new ArrayList<>();
        testheader.add("123456abcdef");
        int user = userStorage.getUserByToken(testheader);
        assertEquals(1, user);
    }

    @Test
    public void isUserAuthValidToken() {
        User u = userStorage.getUser("mmuster");
        userStorage.addTokenToUser(u.getUserId(), "123456abcdef");
        boolean result = userStorage.isUserAuth("123456abcdef");
        assertTrue(result);
    }

    @Test
    public void isUserAuthInvalidToken() {
        User u = userStorage.getUser("mmuster");
        userStorage.addTokenToUser(u.getUserId(), "123456abcdef");
        boolean result = userStorage.isUserAuth("Blabla");
        assertFalse(result);
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

}