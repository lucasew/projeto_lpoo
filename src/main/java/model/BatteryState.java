package model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class BatteryState extends SystemState implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        BatteryState other = (BatteryState)o;
        if (this.state != other.state) {
            return this.state.ordinal() - other.state.ordinal();
        }
        if (this.level != other.level) {
            return this.level - other.level;
        }
        return 0;
    }
}
