package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.interfaces.IUserStorage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DBUserStorageDAO implements IUserStorage {

    private ConcurrentHashMap<String, String> tokenMap;


    private EntityManagerFactory emf;

    @Inject
    public DBUserStorageDAO(EntityManagerFactory emf) {
        tokenMap = new ConcurrentHashMap<>();
        this.emf = emf;
    }

    @Override
    public User getUser(String userID) {
        if (userID == null ||userID.isEmpty()) {
            return null;
        }
        User u = null;
        EntityManager em = emf.createEntityManager();
        try {
            u = em.createQuery("SELECT u FROM User u WHERE u.userId LIKE :userID", User.class).setParameter("userID", userID).getSingleResult();
        }catch (NoResultException e){
            u = null;
        }finally {
            em.close();
        }
        return u;
    }

    @Override
    public Collection<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        Collection<User> u;
        try {
            u = em.createQuery("Select u FROM User u", User.class).getResultList();
        }catch (NoResultException e){
            u = null;
        } finally {
            em.close();
        }
        return u;
    }

    @Override
    public String getTokenFromUser(String userID) {
        for (String token : this.tokenMap.keySet()) {
            if (this.tokenMap.get(token).equalsIgnoreCase(userID)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public int getUserByToken(List<String> l) {
        String u = "";
        if (l.size() == 1) {
            u = this.tokenMap.get(l.get(0));
        } else {
            for (String token : l) {
                String user = this.tokenMap.get(token);
                if (this.tokenMap.get(user).equalsIgnoreCase(token)) {
                    u = user;
                }
            }
        }
        if (u == null || u.equals("") || u.isEmpty()) { // kein User gefunden
            return 0;
        }
        EntityManager em = emf.createEntityManager();
        int r = -2;
        try {
            User user = em.createQuery("Select u FROM User u WHERE userId =\'" + u + "\'", User.class).getSingleResult();
            if (user == null) { //Kein User gefunden
                r = -1;
            } else {
                r = user.getId();
            }
        } catch (Exception e) {
            r = -1;
        } finally {
            em.close();
        }
        return r;
    }

    /**
     * Sucht nach User, zu dem das Token passt
     *
     * @param token das Token mit dem nach Usern gesucht werden soll
     * @return true, wenn ein User gefunden wurde, false, wenn nicht
     */
    @Override
    public boolean isUserAuth(String token) {
        if (this.tokenMap.get(token) != null) {
            return true;
        } else return false;
    }

    /**
     * fuegt Token zu einem User hinzu, wenn Token bereits vorhanden, wird ueberschrieben
     */
    @Override
    public void addTokenToUser(String userId, String token) {
        this.tokenMap.put(token, userId);
    }
}
