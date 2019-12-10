package model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Timestamp {
    public Timestamp() {
        timestamp = Calendar.getInstance();
    }
    public Timestamp(Calendar c) {
        timestamp = c;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    @OneToOne(fetch = FetchType.LAZY)
    private BatteryState batteryState;

    @OneToOne(fetch = FetchType.LAZY)
    private PingState pingState;


}
