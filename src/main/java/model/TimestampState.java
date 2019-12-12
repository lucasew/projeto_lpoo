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

    @OneToOne
    private MachineState machineState;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimestampState that = (TimestampState) o;
        return id == that.id &&
                timestamp.equals(that.timestamp);
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
}
