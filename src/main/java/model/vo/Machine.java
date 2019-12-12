package model.vo;

import javax.persistence.*;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String hostname;

    public Machine() {
    }
    
    public String toString() {
        return this.hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
