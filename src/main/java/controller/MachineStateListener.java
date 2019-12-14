package controller;

import model.vo.MachineState;

public interface MachineStateListener {
    void handleMachineStateChange(MachineState state);
}
