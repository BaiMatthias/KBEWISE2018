package de.htw.ai.kbe.binder;

import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.SongStorage;
import de.htw.ai.kbe.interfaces.ISongStorage;
import de.htw.ai.kbe.storage.UserStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


import javax.inject.Singleton;

public class SongBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SongStorage.class).to(ISongStorage.class).in(Singleton.class);
        bind(UserStorage.class).to(IUserStorage.class).in(Singleton.class);
    }
}
