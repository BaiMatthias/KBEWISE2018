package de.htw.ai.kbe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.interfaces.ISongStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;

import javax.inject.Singleton;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

//The URL for this Service is: http://localhost:8080/SongsRX/rest/songs
@Singleton
@Path("/songs")
public class SongsRX {

    private ObjectMapper objMapper = new ObjectMapper();
    private IUserStorage userStorage;
    private ISongStorage songStorage;

    @Inject
    public SongsRX(IUserStorage userStorage, ISongStorage songStorage) {
        this.userStorage = userStorage;
        this.songStorage = songStorage;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllSongs(@Context HttpHeaders headers) {

        try {
            if (isUserAuthorized(headers)) {
                return Response.ok(this.objMapper.writeValueAsString(songStorage.getSongs())).build();
            } else return Response.status(401, "User not authorised").build();
            //return Response.ok(this.objMapper.writeValueAsString(SongStorage.getInstance().getAllSongs())).build();

        } catch (JsonProcessingException e) {
            return Response.status(400, "Could not process JSON request. " + e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(404, "ein Fehler ist aufgetaucht " + e.getMessage()).build();
        }


    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSongWithID(@PathParam("id") int id, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            for (Song s : songStorage.getSongs()) {
                if (s.getId() == id) {
                    try {
                        return Response.ok(this.objMapper.writeValueAsString(s)).build();
                    } catch (JsonProcessingException e) {
                        return Response.status(400, "Could not process JSON request.").build();
                    }
                }
            }
        } else return Response.status(401, "User not authorised").build();

        return Response.status(404, "Song with passed id couldn't be found.").build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postNewSong(Song song, @Context UriInfo uriInfo, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            if (songStorage.addSong(song)) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder();
                builder.path(Integer.toString(song.getId()));
                return Response.created(builder.build()).status(201).build();
                // https://stackoverflow.com/questions/26092318/create-response-with-location-header-in-jax-rs
            } else {
                return Response.status(409, "Song couldn't be added to database.").build();
            }
        } else return Response.status(401, "User not authorised").build();

    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putSong(@PathParam("id") Integer id, Song song, @Context HttpHeaders headers) {

        if (isUserAuthorized(headers)) {
            if (songStorage.updateSong(id, song)) {
                return Response.status(204).build();
            } else return Response.status(409, "Couldn't update Song").build();
        } else return Response.status(401, "User not authorised").build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteSong(@PathParam("id") Integer id, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            if (songStorage.deleteSong(id)) {
                return Response.status(204).build();
            } else return Response.status(409, "Song not found in database").build();
        } else return Response.status(401, "User not authorised").build();

    }

    private boolean isUserAuthorized(HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null) return false;
        for (String s : authHeaders) {
            if (userStorage.isUserAuth(s)) return true;
        }
        return false;
    }

}
