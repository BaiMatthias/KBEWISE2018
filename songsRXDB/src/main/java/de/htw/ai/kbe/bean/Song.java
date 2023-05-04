package de.htw.ai.kbe.bean;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "song")
@Entity
@Table(name = "Song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String artist;
    private String album;
    private Integer released;

    public Song() {
    }

    public static class Builder {
        private Integer id;
        private String title;
        private String artist;
        private String album;
        private Integer released;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder artist(String val) {
            artist = val;
            return this;
        }

        public Builder album(String val) {
            album = val;
            return this;
        }

        public Builder released(int val) {
            released = val;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    private Song(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artist = builder.artist;
        this.album = builder.album;
        this.released = builder.released;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(album, song.album) &&
                Objects.equals(released, song.released);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, album, released);
    }

    @Override
    public String toString() {
        String songstring = "Song{" +
                "id=" + id +
                ", title=\'" + title + '\'' +
                ", artist=\'" + artist + '\'' +
                ", album=\'" + album + '\'' +
                ", released=" + released +
                '}';
        return songstring;
    }
}
