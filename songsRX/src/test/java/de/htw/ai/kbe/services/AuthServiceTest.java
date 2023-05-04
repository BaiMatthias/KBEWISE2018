package de.htw.ai.kbe.services;

import de.htw.ai.kbe.helper.TestUserStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AuthServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(TestUserStorage.class).to(IUserStorage.class).in(Singleton.class);
                    }
                });
    }

    @Before
    public void setUpChild() {

    }

    @After
    public void tearDownChild() {
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