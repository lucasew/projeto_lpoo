/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author a2049031
 */
@Entity
public class PingState implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    public PingState(Timestamp timestamp, Integer latency) {
        this.timestamp = timestamp;
        this.latency = latency;
    }

    public PingState() {
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Timestamp timestamp;

    private Integer latency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public int compareTo(Object o) {
        PingState pingState = (PingState)o;
        if (this.latency == null || pingState.latency == null ) {
            return this.latency == null ^ pingState.latency == null ? 1 : 0;
        }
        return this.latency.compareTo(pingState.latency);
    }
}
