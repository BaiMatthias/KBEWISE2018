package de.htw.ai.kbe.binder;

import de.htw.ai.kbe.interfaces.ISonglistStorage;
import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.DBSongStorageDAO;
import de.htw.ai.kbe.interfaces.ISongStorage;
import de.htw.ai.kbe.storage.DBSonglistStorageDAO;
import de.htw.ai.kbe.storage.DBUserStorageDAO;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;


import javax.inject.Singleton;

public class SongBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(Persistence
                .createEntityManagerFactory("songRXDB-Prod-PU"))
                .to(EntityManagerFactory.class);
        bind(DBSongStorageDAO.class).to(ISongStorage.class).in(Singleton.class);
        bind(DBUserStorageDAO.class).to(IUserStorage.class).in(Singleton.class);
        bind(DBSonglistStorageDAO.class).to(ISonglistStorage.class).in(Singleton.class);
    }
}
