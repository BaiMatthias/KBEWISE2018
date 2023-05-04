package de.htw.ai.kbe.services;

import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.DBUserStorageDAO;
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
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AuthServiceTest extends JerseyTest {

    private static ResourceConfig service;

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
        service = new ResourceConfig(AuthService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(Persistence
                                .createEntityManagerFactory("songRXDB-Test-PU"))
                                .to(EntityManagerFactory.class);
                        bind(DBUserStorageDAO.class).to(IUserStorage.class).in(Singleton.class);
                    }
                });
    }


    @Test
    public void testGetUserValidID() {
        Response response = target("/auth").queryParam("userId", "mmuster").request().get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetUserInvalidID() {
        Response response = target("/auth").queryParam("userId", "invalidID").request().get();
        assertEquals(403, response.getStatus());
    }

    @Test
    public void testGetUserInvalidParamButValidID() {
        Response response = target("/auth").queryParam("invalidParam", "mmuster").request().get();
        assertEquals(403, response.getStatus());
    }

}