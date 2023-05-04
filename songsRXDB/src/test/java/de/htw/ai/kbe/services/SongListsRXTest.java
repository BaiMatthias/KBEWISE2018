package de.htw.ai.kbe.services;

import de.htw.ai.kbe.interfaces.ISonglistStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.DBSonglistStorageDAO;
import de.htw.ai.kbe.helper.DBUserStorageDAOHelper;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class SongListsRXTest extends JerseyTest {

    private static ResourceConfig service;
    private final String TOKEN = "abcde12345";

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
        service = new ResourceConfig(SongListsRX.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(Persistence
                                .createEntityManagerFactory("songRXDB-Test-PU"))
                                .to(EntityManagerFactory.class);
                        bind(DBUserStorageDAOHelper.class).to(IUserStorage.class).in(Singleton.class);
                        bind(DBSonglistStorageDAO.class).to(ISonglistStorage.class).in(Singleton.class);
                    }
                });
    }

    @Test
    public void getSonglistNormal_XML() {
        Response response = target("/songLists/3").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN).get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getSonglistNotPublic_XML() {
        Response response = target("/songLists/4").request().accept("application/xml").header(HttpHeaders.AUTHORIZATION, TOKEN).get();
        assertEquals(404, response.getStatus());
    }

}