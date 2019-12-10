package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class BatteryState implements Serializable {
    @Enumerated(EnumType.STRING)
    private PowerState state;

    @Column
    private int level;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Timestamp timestamp;

    public PowerState getState() {
        return state;
    }

    public void setState(PowerState state) {
        this.state = state;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
