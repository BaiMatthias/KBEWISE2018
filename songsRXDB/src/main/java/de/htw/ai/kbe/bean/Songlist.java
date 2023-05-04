package de.htw.ai.kbe.bean;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@XmlRootElement(name = "songlist")
//https://vladmihalcea.com/how-to-map-java-and-sql-arrays-with-jpa-and-hibernate/ :
@TypeDefs({@TypeDef(name = "int-array", typeClass = IntArrayType.class)})
@Entity
@Table(name = "Songlist")
public class Songlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer owner;
    private boolean personal;

    //https://vladmihalcea.com/how-to-map-java-and-sql-arrays-with-jpa-and-hibernate/ :
    @Type(type = "int-array")
    @Column(name = "playlist", columnDefinition = "integer[]")
    private int[] playlist;


    public Songlist() {
    }

    public static class Builder {
        private int id;
        private String name;
        private int owner;
        private boolean personal;
        private int[] playlist;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder owner(int val) {
            owner = val;
            return this;
        }

        public Builder personal(boolean val) {
            personal = val;
            return this;
        }

        public Builder playlist(int[] val) {
            playlist = val;
            return this;
        }

        public Songlist build() {
            return new Songlist(this);
        }
    }

    private Songlist(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.owner = builder.owner;
        this.personal = builder.personal;
        this.playlist = builder.playlist;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getPersonal() {
        return personal;
    }

    public boolean listBelongsToUser(int userId) {
        return (userId == getOwner());
    }

    public void setPersonal(Boolean personal) {
        this.personal = personal;
    }


    public int[] getPlaylist() {
        return playlist;
    }

    public void setPlaylist(int[] playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Songlist songlist = (Songlist) o;
        return Objects.equals(name, songlist.name) &&
                Objects.equals(owner, songlist.owner) &&
                Objects.equals(personal, songlist.personal) &&
                Objects.equals(playlist, songlist.playlist);
    }

    @Override
    public String toString() {
        String songliststring = "Song{" +
                "id=" + id +
                ", name=\'" + name + '\'' +
                ", owner=\'" + owner + '\'' +
                ", personal=\'" + personal + '\'' +
                ", playlist=" + playlist +
                '}';
        return songliststring;
    }
}
