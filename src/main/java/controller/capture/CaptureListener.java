package controller.capture;

import model.MachineState;

public interface CaptureListener {
    void handleMachineStateCapture(MachineState state);
}
