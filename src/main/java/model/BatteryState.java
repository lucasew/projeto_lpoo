package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class BatteryState implements Serializable {
    @Enumerated(EnumType.STRING)
    private PowerState state;

    @Column
    private int level;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private TimestampState timestamp;

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

    public TimestampState getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(TimestampState timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatteryState that = (BatteryState) o;
        return level == that.level &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, level, timestamp, id);
    }
}
