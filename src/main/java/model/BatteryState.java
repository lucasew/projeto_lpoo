package model;

import controller.driver.power.PowerState;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class BatteryState implements Comparable {
    @Enumerated(EnumType.STRING)
    private PowerState state;

    @Column
    private int level;

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

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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
