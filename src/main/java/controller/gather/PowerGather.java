package controller.gather;

import controller.driver.power.GenericPowerDriver;
import model.BatteryState;
import model.TimestampState;

public class PowerGather {
    private final GenericPowerDriver driver;
    public PowerGather(GenericPowerDriver driver) {
        this.driver = driver;
    }

    public BatteryState getState() {
        BatteryState state = new BatteryState();
        state.setState(driver.getState());
        state.setLevel(driver.getChargeLevel());
        return state;
    }
}
