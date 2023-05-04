package de.htw.ai.kbe.echo;

public class Song {
    private int id = 0;
    private String title = "";
    private String artist = "";
    private String album = "";
    private int released = 0;

    public Song(int id, String title, String artist, String album, int released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }

    public Song() {
        this.id = 0;
        this.title = "";
        this.artist = "";
        this.album = "";
        this.released = 0;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public int getReleased() {
        return this.released;
    }

    @Override
    public String toString() {
        String song = "\n";
        song += "Id: " + this.id + "\n";
        song += "Title: " + this.title + "\n";
        song += "Artist: " + this.artist + "\n";
        song += "Album: " + this.album + "\n";
        song += "Released: " + this.released + "\n";
        return song;
    }
}
