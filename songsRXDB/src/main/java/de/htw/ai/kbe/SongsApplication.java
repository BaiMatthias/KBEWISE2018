package de.htw.ai.kbe;

import de.htw.ai.kbe.binder.SongBinder;
import de.htw.ai.kbe.services.AuthService;
import de.htw.ai.kbe.services.SongListsRX;
import de.htw.ai.kbe.services.SongsRX;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("(/rest")
public class SongsApplication extends ResourceConfig {
    public SongsApplication() {
        packages(true, "de.htw.ai.kbe.services");
        register(SongsRX.class);
        register(AuthService.class);
        register(SongListsRX.class);
        register(new SongBinder());
    }
}
