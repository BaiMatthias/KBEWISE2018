package de.htw.ai.kbe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.bean.Songlist;
import de.htw.ai.kbe.interfaces.ISonglistStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

//The URL for this Service is: http://localhost:8080/songsRX/rest/songLists
@Singleton
@Path("/songLists")
public class SongListsRX {

    private ObjectMapper objMapper = new ObjectMapper();
    private IUserStorage userStorage;
    private ISonglistStorage songlistStorage;

    @Inject
    public SongListsRX(IUserStorage userStorage, ISonglistStorage songlistStorage) {
        this.userStorage = userStorage;
        this.songlistStorage = songlistStorage;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSonglistFromUser(@QueryParam("userId") int userId, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            int user = getUserId(headers);
            if (user < 1) {
                return Response.status(401, "Somthing is wrong. " +
                        "No User found. " +
                        "Error:" + user + ": " +
                        "0(not in Tokenmap), -1(not in DB), -2(somthing else)").build();
            }
            try {
                Collection<Songlist> list = songlistStorage.getAllUserSonglists(userId, user);
                if (list == null) {
                    return Response.status(404, "No Songlists belongs to user (" + user + ") or are public").build();
                } else {
                    return Response.ok(this.objMapper.writeValueAsString(list)).build();
                }
            } catch (JsonProcessingException e) {
                return Response.status(401, "No lists with authorization found").build();
            }
        } else
            return Response.status(401, "User not authorised").build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSonglistWithID(@PathParam("id") int id, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            int user = getUserId(headers);
            if (user < 1) {
                return Response.status(401, "Somthing is wrong. " +
                        "No User found. " +
                        "Error:" + user + ": " +
                        "0(not in Tokenmap), -1(not in DB), -2(somthing else)").build();
            }
            Songlist s = songlistStorage.getSonglist(id, user);
            if (s != null) {
                if (s.getId() == id) {
                    try {
                        return Response.ok(this.objMapper.writeValueAsString(s)).build();
                    } catch (JsonProcessingException e) {
                        return Response.status(400, "Could not process request.").build();
                    }
                }
            } else {
                return Response.status(404, "Songlist(" + id + ") don't belongs to user (" + user +
                        "),is not public or does not exist").build();
            }
        } else
            return Response.status(401, "User not authorised").build();
        return Response.status(404, "Songlist(" + id + ")  with passed id couldn't be found.").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postNewSong(Songlist songlist, @Context UriInfo uriInfo, @Context HttpHeaders headers) {
        if (songlist == null){
            return Response.status(409, "Songlist is not a JSON or XML").build();
        }
        if (isUserAuthorized(headers)) {
            if (songlist.getOwner() != getUserId(headers))
                return Response.status(401, "User not authorised to add a list for another user").build();
            if (!songlistStorage.isValid(songlist))
                return Response.status(401, "One or more songs in this list doesn't exist").build();
            if (songlistStorage.addList(songlist)) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder();
                builder.path(Integer.toString(songlist.getId()));
                return Response.created(builder.build()).status(201).build();
            } else {
                return Response.status(409, "Songlist couldn't be added to database.").build();
            }
        } else return Response.status(401, "User not authorised").build();

    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSonglist(@PathParam("id") Integer id, @Context HttpHeaders headers) {
        if (isUserAuthorized(headers)) {
            if (songlistStorage.deleteList(id, getUserId(headers))) {
                return Response.status(204).build();
            } else return Response.status(404, "Couldn't delete Songlist").build();
        } else return Response.status(401, "User not authorised").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putSong(@PathParam("id") Integer id, Songlist songlist, @Context HttpHeaders headers) {
        if (songlist.getOwner() != getUserId(headers))
            return Response.status(401, "User not authorised to add a list for another user").build();
        if (isUserAuthorized(headers)) {
            if (songlistStorage.updateSonglist(songlist.getId(), getUserId(headers), songlist)) {
                return Response.status(204).build();
            } else return Response.status(409, "Couldn't update Songlist").build();
        } else return Response.status(401, "User not authorised").build();
    }

    private int getUserId(HttpHeaders headers) {
        return userStorage.getUserByToken(headers.getRequestHeader(HttpHeaders.AUTHORIZATION));
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
