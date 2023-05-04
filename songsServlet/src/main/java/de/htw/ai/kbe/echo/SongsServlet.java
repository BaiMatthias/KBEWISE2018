package de.htw.ai.kbe.echo;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String uriToDB = null;
    private String uriToJSON = null;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Songs songs;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        this.uriToDB = servletConfig.getInitParameter("uriToDBComponent");
        this.uriToJSON = servletConfig.getInitParameter("uriToJSONComponent");

        //for JSON-File
        if (!new File(this.uriToJSON).exists()) throw new ServletException("No file in " + this.uriToJSON);
        try {
            List<Song> songlist = this.objectMapper.readValue(new File(this.uriToJSON), new TypeReference<List<Song>>() {
            });
            this.songs = new Songs(songlist);
        } catch (JsonParseException e) {
            throw new ServletException("Parsing not successful\n" + e.getMessage());
        } catch (JsonMappingException e) {
            throw new ServletException("Mapping not successful\n" + e.getMessage());
        } catch (IOException e) {
            throw new ServletException("I/O Error\n" + e.getMessage() + "\n" + "JSON-Path:" + this.uriToJSON);
        } catch (NullPointerException e) {
            throw new ServletException("Null Pointer\n" + e.getMessage() + "\n" + "JSON-Path:" + this.uriToJSON);
        }
    }

    /**
     * checks if the header expects JSON or not
     *
     * @param request request of the client
     * @return true expects no JSON false if it expects
     */
    private boolean noJSONHeader(HttpServletRequest request) {
        String typeContent = request.getContentType(); //first method to get Content Type
        String typeHeader = request.getHeader("Accept"); //second method to get Content Type
        if (typeContent != null){//content null?
            if (typeContent.toLowerCase().contains("application/json")){//application/json in Content?
                return false;
            }else{
                if (typeHeader != null){//Content != null && type Header != null
                    if (typeHeader.toLowerCase().contains("application/json")  ||
                            typeHeader.toLowerCase().contains("*")){//application/json in header?
                        return false;
                    }else{ // content = null && header != json
                        return true;
                    }
                }else{ //both != null -> json
                    return false;
                }
            }
        }else{
            if (typeHeader != null){//Content = null -> header != null
                if (typeHeader.toLowerCase().contains("application/json")  ||
                        typeHeader.toLowerCase().contains("*")){//check application/json in Header?
                    return false;
                }else{
                    return true;
                }
            }else{ //both null
                return false;
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // alle Parameter (keys)
        Enumeration<String> paramNames = request.getParameterNames();

        if (noJSONHeader(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON payloads only" +
                    " getContentType:" + request.getContentType() +
                    " getHeader(Accept): " + request.getHeader("Accept"));
            return;
        }

        //check Parameter
        if (!paramNames.hasMoreElements()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Arguments given!");
            return;
        }
        //execute the parameter
        String param = paramNames.nextElement();
        if (param.equalsIgnoreCase("all")) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), songs.getSongs());
        }
        if (param.equalsIgnoreCase("songId")) {
            try {
                int id = Integer.parseInt(request.getParameter("songId"));
                if (id < 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Song Id is negative!");
                    return;
                }
                Song song = songs.getSong((id));
                if (song == null) { // no song found
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Song does not exits.");
                    return;
                } else {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), song);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Song Id is not a number");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Arguments have to be: ?all or ?songId=##");
            return;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (noJSONHeader(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON payloads only" +
                    " getContentType:" + request.getContentType() +
                    " getHeader(Accept): " + request.getHeader("Accept"));
            return;
        }
        try {
            if (request.getInputStream().toString().isEmpty() || request.getInputStream().toString() == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Body should not be empty");
                return;
            }
            Song song = this.objectMapper.readValue(request.getInputStream(), new TypeReference<Song>() {
            });
            song.setId(songs.getNextID());
            if (songs.addSong(song)) {
                response.setHeader("Location", request.getRequestURL() + "?songId=" + song.getId());
            } else {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Song could not added");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Could not save song");
        }
    }

    /**
     * returns the DB-Connect-String
     *
     * @return connectstring to DB
     */
    protected String getUriToDB() {
        return this.uriToDB;
    }

    /**
     * returns the Path of the JSON-File
     *
     * @return path to jsonfile
     */
    protected String getUriToJSON() {
        return this.uriToJSON;
    }

    @Override
    public void destroy() {
        OutputHandler outHandler = new OutputHandler(this.songs);
        String uriToJSON = getUriToJSON();
        uriToJSON += "_1.json";
        try {
            outHandler.writeToFile(uriToJSON);
        } catch (IOException e) {
            System.out.println("Cannot write File\n" + e.getMessage());
        }
    }

    /**
     * returns the songs
     *
     * @return songsobjeect with songlist
     */
    public Songs getSongs() {
        return this.songs;
    }
}