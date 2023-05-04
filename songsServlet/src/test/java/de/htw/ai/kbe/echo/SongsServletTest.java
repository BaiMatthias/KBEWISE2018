package de.htw.ai.kbe.echo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class SongsServletTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private SongsServlet servlet;
    @Mock
    private MockServletConfig config;
    @Mock
    private MockHttpServletRequest request;
    @Mock
    private MockHttpServletResponse response;

    private File f;

    @Before
    public void setUp() throws ServletException {
        MockitoAnnotations.initMocks(this);
        servlet = new SongsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        when(config.getInitParameter("uriToJSONComponent")).thenReturn("songs.json");
        servlet.init(config);

    }

    @Test
    public void initNormal() throws IOException, ServletException {
        assertEquals(10, servlet.getSongs().getSongs().size());
    }

    /*@Test File.Readable(false, false) funktioniert nicht
    public void initCannotReadFile() throws  ServletException {
        thrown.expect(ServletException.class);
        when(config.getInitParameter("uriToJSONComponent")).thenReturn("songs2.json");
        servlet.init(config);
        assertEquals(10, servlet.getSongs().getSongs().size());

    } */

    @Test
    public void initNoFile() throws ServletException {
        thrown.expect(ServletException.class);
        when(config.getInitParameter("uriToJSONComponent")).thenReturn("songs3.json");
        servlet.init(config);
        assertEquals(10, servlet.getSongs().getSongs().size());
    }

    @Test
    public void doGetNormal() throws IOException, ServletException {
        request.addParameter("all");

        servlet.doGet(request, response);

        assertTrue(response.getContentAsString().contains("Private Show"));
        assertTrue(response.getContentAsString().contains("Britney Spears"));
        assertTrue(response.getContentAsString().contains("Glory"));

        assertEquals("application/json", response.getContentType());


    }

    @Test
    public void doGetNormalWithHeader() throws IOException, ServletException {
        request.addParameter("all");
        request.addHeader("Accept", "application/json");
        servlet.doGet(request, response);

        assertTrue(response.getContentAsString().contains("Private Show"));
        assertTrue(response.getContentAsString().contains("Britney Spears"));
        assertTrue(response.getContentAsString().contains("Glory"));

        assertEquals("application/json", response.getContentType());


    }

    @Test
    public void doGetWrongUri() throws IOException {
        request.addParameter("al");
        request.addHeader("Accept", "application/json");
        ;
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void doGetIdNotInList() throws IOException, ServletException {
        request.addParameter("songId", "12");
        request.addHeader("Accept", "application/json");
        servlet.doGet(request, response);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void doGetIdNegative() throws IOException, ServletException {
        request.addParameter("songId", "-12");
        request.addHeader("Accept", "application/json");
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void doGetIdWithLetter() throws IOException, ServletException {
        request.addParameter("songId", "A");
        request.addHeader("Accept", "application/json");
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void doGetWrongAcceptHeader() throws IOException, ServletException {
        request.addParameter("songId", "1");
        request.addHeader("Accept", "application/xml");
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void doGetEmptyHeader() throws IOException, ServletException {
        request.setContentType(null);
        request.addHeader("Accept", "");
        request.addParameter("songId", "1");
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void doPostNormal() throws IOException, ServletException {
        request.setContentType("application/json");
        request.addHeader("Accept", "text/plain");
        request.setContent("{\"title\": \"Testtitle\", \"artist\": \"Testartist\",  \"album\": \"Testalbum\",   \"released\": \"1234\"}".getBytes());
        servlet.doPost(request, response);
        assertEquals(request.getRequestURL() + "?songId=11", response.getHeader("Location"));
    }

    @Test
    public void doPostDoubleId() throws IOException, ServletException {
        request.setContentType("application/json");
        request.addHeader("Accept", "text/plain");
        request.setContent("{\"title\": \"Testtitle\", \"artist\": \"Testartist\",  \"album\": \"Testalbum\",   \"released\": \"1234\"}".getBytes());
        servlet.doPost(request, response);
        servlet.doPost(request, response);
        assertEquals(409, response.getStatus());
    }

    @Test
    public void doPostEmptyContent() throws IOException, ServletException {
        request.setContentType("application/json");
        request.addHeader("Accept", "text/plain");
        request.setContent("".getBytes());
        servlet.doPost(request, response);
        assertEquals(409, response.getStatus());
    }

    @Test
    public void doPostWrongAcceptHeader() throws IOException, ServletException {
        request.setContentType("application/xml");
        request.addHeader("Accept", "text/plain");
        request.setContent("".getBytes());
        servlet.doPost(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void destroyNormal() throws IOException, ServletException {
        doPostNormal();
        servlet.destroy();
        f = new File(servlet.getUriToJSON() + "_1.json");
        assertTrue(f.exists());
        String text = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
        assertTrue(text.contains("Testtitle"));
        assertTrue(text.contains("Testartist"));
        assertTrue(text.contains("Testalbum"));

    }


    @After
    public void deleteOutputFile() {
        if (f != null && f.exists()) {
            f.delete();
        }
    }
}