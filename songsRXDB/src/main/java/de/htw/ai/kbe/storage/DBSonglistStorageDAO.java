package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.Songlist;
import de.htw.ai.kbe.interfaces.ISonglistStorage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DBSonglistStorageDAO implements ISonglistStorage {

    private EntityManagerFactory emf;

    @Inject
    public DBSonglistStorageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public boolean addList(Songlist newSonglist) throws IllegalArgumentException {
        EntityManager em = emf.createEntityManager();
        boolean check;
        try {
            em.getTransaction().begin();
            em.persist(newSonglist);
            em.getTransaction().commit();
            check = true;
        } catch (Exception e) {
            check = false;
        } finally {
            em.close();
        }
        return check;
    }

    @Override
    public boolean deleteList(int songlistId, int userId) {
        EntityManager em = emf.createEntityManager();
        boolean check = false;
        try {
            Songlist sList = em.find(Songlist.class, songlistId);
            if (sList.getOwner() == userId) { //wenn auth -> delete
                em.getTransaction().begin();
                em.remove(sList);
                em.getTransaction().commit();
                check = true;
            }
        } catch (Exception e) {
            check = false;
        } finally {
            em.close();
        }
        return check;
    }

    @Override
    public Songlist getSonglist(int songlistId, int userId) {
        EntityManager em = emf.createEntityManager();
        Songlist sList = em.find(Songlist.class, songlistId);
        if (sList == null) { //Liste existiert nicht
            em.close();
            return null;
        }
        if (sList.listBelongsToUser(userId)) { //List gehoert user
            em.close();
            return sList;
        } else {
            if (!sList.getPersonal()) { //Liste is public
                em.close();
                return sList;
            } else {
                em.close();
                return null; //user hat keine Berechtiggung fuer die Liste
            }
        }
    }

    @Override
    public boolean isValid(Songlist songlist) {
        EntityManager em = emf.createEntityManager();
        try {
            List<int[]> songs = em.createQuery("Select s.id FROM Song s").getResultList();
            for (int s : songlist.getPlaylist()) {
                if (!songs.contains(s)) {
                    em.close();
                    return false;
                }
            }
        } catch (Exception e) {
            em.close();
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    @Override
    public Collection<Songlist> getAllUserSonglists(int userId, int authUser) {
        EntityManager em = emf.createEntityManager();
        List<Songlist> sList = null;
        try {
            if (userId == authUser) { //user fragt eigene Listen ab
                sList = em.createQuery("Select s FROM Songlist s Where owner=" + userId).getResultList();
            } else {
                sList = em.createQuery("Select s FROM Songlist s Where owner=" + userId + " and personal=FALSE").getResultList();
            }
            if (sList.isEmpty()) {
                sList = null;
            }
        } catch (Exception e) {
            sList = null;
        } finally {
            em.close();
        }
        return sList;
    }

    @Override
    public boolean updateSonglist(int songlistId, int userId, Songlist songlist) {
        EntityManager em = emf.createEntityManager();
        Songlist sList = em.find(Songlist.class, songlistId);
        if (sList == null){
            em.close();
            return false;
        }
        boolean success = true;
        try {
            if (sList.getOwner() == userId) { //wenn auth -> update
                em.getTransaction().begin(); //begin Transaction
                sList.setName(songlist.getName());
                sList.setPersonal(songlist.getPersonal());
                sList.setPlaylist(songlist.getPlaylist());
                em.getTransaction().commit(); //commit Transaction
            }
        } catch (Exception e) {
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
}
