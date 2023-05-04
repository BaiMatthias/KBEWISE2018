package de.htw.ai.kbe.helper;

import de.htw.ai.kbe.interfaces.IUserStorage;
import de.htw.ai.kbe.storage.DBUserStorageDAO;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class DBUserStorageDAOHelper extends DBUserStorageDAO implements IUserStorage {

    @Inject
    public DBUserStorageDAOHelper(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public boolean isUserAuth(String token) {
        if (super.isUserAuth(token))
            return true;
        else {
            return token.equals("abcde12345");
        }
    }

    @Override
    public int getUserByToken(List<String> l) {
        int r = super.getUserByToken(l);
        if (r < 1) {
            return 1;
        } else
            return r;
    }
}
