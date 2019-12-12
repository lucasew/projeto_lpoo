package model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.InvalidObjectException;

@Entity
public class MachineState implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private PingState pingState;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private BatteryState batteryState;

    @OneToOne(optional = false)
    @Cascade(CascadeType.ALL)
    private TimestampState timestampState;

    @ManyToOne(optional = false)
    @Cascade(CascadeType.ALL)
    private Machine machine;

    public MachineState derivate(MachineState other) throws InvalidObjectException {
        if (other.pingState == null && other.batteryState == null) {
            throw new InvalidObjectException("É necessário pelo menos o estado de ping ou o de bateria");
        }
        MachineState ret = new MachineState();
        ret.batteryState = this.batteryState == null || !this.batteryState.equals(other.batteryState)
                ? other.batteryState
                : batteryState;
        ret.pingState = this.pingState == null || !this.pingState.equals(other.pingState)
                ? other.pingState
                : pingState;
        ret.timestampState = this.timestampState == null
                ? other.timestampState
                : timestampState;
        ret.machine = this.machine;
        return ret;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public PingState getPingState() {
        return pingState;
    }

    public void setPingState(PingState pingState) {
        this.pingState = pingState;
    }

    public BatteryState getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(BatteryState batteryState) {
        this.batteryState = batteryState;
    }

    public TimestampState getTimestampState() {
        return timestampState;
    }

    public void setTimestampState(TimestampState timestampState) {
        this.timestampState = timestampState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
