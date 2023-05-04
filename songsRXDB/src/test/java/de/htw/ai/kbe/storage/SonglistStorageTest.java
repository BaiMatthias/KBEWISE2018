package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.Songlist;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class SonglistStorageTest {

    private static EntityManagerFactory emf;
    private static DBSonglistStorageDAO songliststorage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("songRXDB-Test-PU");
        songliststorage = new DBSonglistStorageDAO(emf);
    }

    @Test
    public void isValidTest() {
        Songlist list = new Songlist();
        list.setOwner(1);
        list.setName("supermix");
        list.setPersonal(true);
        list.setPlaylist(new int[] {4,5,6,7});
        assertTrue(songliststorage.isValid(list));
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

}

