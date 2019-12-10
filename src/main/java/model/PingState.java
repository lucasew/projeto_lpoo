/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author a2049031
 */
@Entity
public class PingState implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    @Column
    private boolean isValido = true;

    public PingState(Integer latency, boolean isValido) {
        this.latency = latency;
    }

    public PingState() {
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private TimestampState timestamp;

    private Integer latency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TimestampState getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(TimestampState timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PingState pingState = (PingState) o;
        return latency.equals(pingState.latency);
    }

    public boolean isValido() {
        return isValido;
    }

    public void setValido(boolean valido) {
        isValido = valido;
    }
}
