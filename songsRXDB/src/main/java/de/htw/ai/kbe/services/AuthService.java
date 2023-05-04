package de.htw.ai.kbe.services;

import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.client.TokenGenerator;
import de.htw.ai.kbe.interfaces.IUserStorage;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


// URL  http://localhost:8080/songsRX/rest/auth
@Path("/auth")
public class AuthService {

    @Inject
    public AuthService(IUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    private IUserStorage userStorage;

    //Get http://localhost:8080/songsRX/rest/auth?userId=mmuster
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response assignTokenToUser(@QueryParam("userId") String userId) {
        if (userId != null && !userId.isEmpty()) {
            User user = userStorage.getUser(userId);
            if (user != null) {
                //Generate Token and assign to user
                String token = TokenGenerator.generateToken(userId);
                userStorage.addTokenToUser(userId, token);
                return Response.ok(token).status(200).build();
            } else return Response.status(403, "No User with id " + userId).build();
        } else {
            return Response.status(403, "Missing parameter: id").build();
        }
    }


}
