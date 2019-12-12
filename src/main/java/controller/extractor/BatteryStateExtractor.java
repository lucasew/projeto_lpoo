/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.extractor;

import controller.ImageProvider;
import controller.capture.CaptureDaemon;
import model.BatteryState;
import model.MachineState;

import javax.swing.*;

/**
 *
 * @author lucasew
 */
public class BatteryStateExtractor implements StateExtractor {

    private BatteryState getBatteryState(MachineState state) {
        return state.getBatteryState();
    }
    @Override
    public ImageIcon getIcon(MachineState state) {
        BatteryState bstate = getBatteryState(state);
        if (bstate == null) {
            return ImageProvider.getImage("/META-INF/icon/battery_unknown.png");
        }
        switch (bstate.getState()) {
            case AC:
                return ImageProvider.getImage("/META-INF/icon/ac.png");
            case Charging:
            case Full:
                return ImageProvider.getImage("/META-INF/icon/battery_charging.png");
            case Discharging:
                return ImageProvider.getImage("/META-INF/icon/battery.png");
        }
        return ImageProvider.getImage("/META-INF/icon/battery_unknown.png");
    }

    @Override
    public String getLabel(MachineState state) {
        BatteryState batteryState = getBatteryState(state);
        return batteryState == null
                ? "[DESCONHECIDO]"
                : String.format("%d %% - %s", batteryState.getLevel(), batteryState.getState().toString());
    }
}
