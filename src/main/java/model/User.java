package model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String user;
    private String password;

    public User() {}

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
