/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.vo;

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

    @Column
    private boolean isValido = true;

    public PingState(Integer latency, boolean isValido) {
        this.latency = latency;
    }

    public PingState() {}

    @OneToOne
    private MachineState machineState;
    private Integer latency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public MachineState getMachineState() {
        return machineState;
    }

    public void setMachineState(MachineState machineState) {
        this.machineState = machineState;
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
