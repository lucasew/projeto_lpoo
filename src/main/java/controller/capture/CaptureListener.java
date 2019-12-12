package controller.capture;

import model.vo.MachineState;

public interface CaptureListener {
    void handleMachineStateCapture(MachineState state);
}
