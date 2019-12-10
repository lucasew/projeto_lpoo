package model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class TimestampState {
    public TimestampState() {
        timestamp = Calendar.getInstance();
    }
    public TimestampState(Calendar c) {
        timestamp = c;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    @OneToOne(fetch = FetchType.EAGER)
    private BatteryState batteryState;

    @OneToOne(fetch = FetchType.EAGER)
    private PingState pingState;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimestampState that = (TimestampState) o;
        return id == that.id &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, id, batteryState, pingState);
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BatteryState getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(BatteryState batteryState) {
        this.batteryState = batteryState;
    }

    public PingState getPingState() {
        return pingState;
    }

    public void setPingState(PingState pingState) {
        this.pingState = pingState;
    }
}
