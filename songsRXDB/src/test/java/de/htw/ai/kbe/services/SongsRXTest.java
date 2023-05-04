package de.htw.ai.kbe.services;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.interfaces.ISongStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.DBSongStorageDAO;
import de.htw.ai.kbe.helper.DBUserStorageDAOHelper;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class SongsRXTest extends JerseyTest {

    private static ResourceConfig service;
    private final String TOKEN = "abcde12345";
    private Song song;

    @Override
    protected Application configure() {
        //For a purpose of running multiple test containers in parallel
        // //you need to set the TestProperties.CONTAINER_PORT to 0 value.
        // //This will tell Jersey Test Framework (and the underlying test container) to use the first available port.
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return service;
    }

    @BeforeClass
    public static void setUpClass() {
        service = new ResourceConfig(SongsRX.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(Persistence
                                .createEntityManagerFactory("songRXDB-Test-PU"))
                                .to(EntityManagerFactory.class);
                        bind(DBUserStorageDAOHelper.class).to(IUserStorage.class).in(Singleton.class);
                        bind(DBSongStorageDAO.class).to(ISongStorage.class).in(Singleton.class);
                    }
                });
    }

    @Before
    public void setUpChild() {
        song = new Song();
        song.setAlbum("Mein Album");
        song.setArtist("Mein Artist");
        song.setReleased(2018);
        song.setTitle("Mein Lied");
    }

    @Test
    public void updateSongNotExist_XML() {
        Response response = target("/songs/11").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.xml(song));
        assertEquals(409, response.getStatus());
    }

    @Test
    public void updateSongNotExist_JSON() {
        Response response = target("/songs/11").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.json(song));
        assertEquals(409, response.getStatus());
    }

    @Test
    public void updateSongInvalidParam_XML() {
        Response response = target("/songs/invalidPara").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.xml(song));
        assertEquals(404, response.getStatus());
    }

    @Test
    public void updateSongInvalidParam_JSON() {
        Response response = target("/songs/invalidParam").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.json(song));
        assertEquals(404, response.getStatus());
    }

    @Test
    public void updateValidParameter_JSON() {
        Song newSong = new Song();
        newSong.setAlbum("Neues Album");
        newSong.setArtist("Neuer Artist");
        newSong.setReleased(2016);
        newSong.setTitle("Neuer Titel");
        Response response = target("/songs/1").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.json(newSong));
        assertEquals(204, response.getStatus());
    }

    @Test
    public void updateValidParam_XML() {
        Song newSong = new Song();
        newSong.setAlbum("Neues Album");
        newSong.setArtist("Neuer Artist");
        newSong.setReleased(2016);
        newSong.setTitle("Neuer Titel");
        Response response = target("/songs/1").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.xml(newSong));
        assertEquals(204, response.getStatus());
    }

    @Test
    public void updateWithNull_XML() {
        Song newSong = new Song();
        newSong.setAlbum(null);
        newSong.setArtist(null);
        newSong.setReleased(null);
        newSong.setTitle(null);
        Response response = target("/songs/1").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.xml(newSong));
        assertEquals(409, response.getStatus());
    }

    @Test
    public void updateWithNull_JSON() {
        Song newSong = new Song();
        newSong.setAlbum(null);
        newSong.setArtist(null);
        newSong.setReleased(null);
        newSong.setTitle(null);
        Response response = target("/songs/1").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, TOKEN)
                .put(Entity.json(newSong));
        assertEquals(409, response.getStatus());
    }

    @Test
    public void getWithUnauthorizedUser() {
        Response response = target("/songs/1").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, "1234")
                .get();
        assertEquals(401, response.getStatus());
    }

    @Test
    public void getWithNullToken() {
        Response response = target("/songs/1").request().accept("application/json").header(HttpHeaders.AUTHORIZATION, null)
                .get();
        assertEquals(401, response.getStatus());
    }

}