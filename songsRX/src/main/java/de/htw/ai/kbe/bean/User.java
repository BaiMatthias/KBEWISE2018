package de.htw.ai.kbe.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "user")
public class User {
    private int id;
    private String userId;
    private String lastName;
    private String firstName;
    private String token;

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(firstName, user.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lastName, firstName);
    }

    @Override
    public String toString() {
        String userstring = "User{" +
                ", id=\'" + this.id + "\'" +
                ", userId=\'" + this.userId + "\'" +
                ", lastName=\'" + this.lastName + "\'" +
                ", firstName=\'" + this.firstName + "\'" +
                "}";
        return userstring;
    }


}
