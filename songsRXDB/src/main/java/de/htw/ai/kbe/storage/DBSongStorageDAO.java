package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.interfaces.ISongStorage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;

public class DBSongStorageDAO implements ISongStorage {

    private EntityManagerFactory emf;

    @Inject
    public DBSongStorageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * returns all songs as a List
     *
     * @return all songs
     */
    public Collection<Song> getSongs() {
        EntityManager em = emf.createEntityManager();
        List<Song> sList;
        try {
            sList = em.createQuery("Select s FROM Song s").getResultList();
        } finally {
            em.close();
        }
        return sList;
    }

    /**
     * adds a given song to the Song-List
     *
     * @param newSong Song-Object of new song
     * @return return true if added return false if not added
     */
    public synchronized boolean addSong(Song newSong) {
        try {
            //Check Artist
            if (newSong.getArtist() == null) throw new IllegalArgumentException("No empty artist allowed");
            //Check Album
            if (newSong.getAlbum() == null) throw new IllegalArgumentException("No empty album allowed");
            //Check Title
            if (newSong.getTitle() == null) throw new IllegalArgumentException("No empty title allowed");
            //Check released
            if (newSong.getReleased() == 0) throw new IllegalArgumentException("No empty released allowed");
            //Check Song is duplicate
            Collection<Song> tmpSongList = this.getSongs();
            for (Song song : tmpSongList) {
                try {
                    if (song.getAlbum().equals(newSong.getAlbum()) &&
                            song.getArtist().equals(newSong.getArtist()) &&
                            song.getTitle().equals(newSong.getTitle()) &&
                            song.getReleased() == newSong.getReleased())
                        throw new IllegalArgumentException("Song already exists");
                    //Check
                } catch (NullPointerException e) {
                    System.out.println("Something is empty");
                }
            }

            // add Song
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(newSong);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * return the song with the given id
     *
     * @param id song id
     * @return the song or null if not exists
     */
    public Song getSong(int id) {
        EntityManager em = emf.createEntityManager();
        Song s = null;
        try {
            s = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return s;
    }

    @Override
    public synchronized boolean updateSong(int id, Song updatedSong) {
        EntityManager em = emf.createEntityManager();
        Song s = null;
        try {
            s = em.find(Song.class, id);
            if (s == null){
                em.close();
                return false;
            }

            if (updatedSong != null) {
                try {
                    //Check Artist
                    if (updatedSong.getArtist() == null || updatedSong.getArtist().isEmpty()) throw new IllegalArgumentException("No empty artist allowed");
                    //Check Album
                    if (updatedSong.getAlbum() == null || updatedSong.getAlbum().isEmpty()) throw new IllegalArgumentException("No empty album allowed");
                    //Check Title
                    if (updatedSong.getTitle() == null || updatedSong.getTitle().isEmpty()) throw new IllegalArgumentException("No empty title allowed");
                    //Check released
                    if (updatedSong.getReleased() == 0) {
                        throw new IllegalArgumentException("No empty released allowed");
                    }
                    // update Song
                    em.getTransaction().begin();
                    s.setArtist(updatedSong.getArtist());
                    s.setAlbum(updatedSong.getAlbum());
                    s.setTitle(updatedSong.getTitle());
                    s.setReleased(updatedSong.getReleased());
                    em.getTransaction().commit();
                } catch (IllegalArgumentException d) {
                    em.close();
                    System.out.println("IllegalArgumentException: " + d.getMessage());
                    return false;
                }
            } else {
                em.close();
                return false;
            }
        } finally {
            em.close();
        }
        return true;
    }
}
