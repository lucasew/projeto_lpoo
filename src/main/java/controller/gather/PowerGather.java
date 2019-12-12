package controller.gather;

import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import model.BatteryState;
import model.TimestampState;

public class PowerGather {
    private final GenericPowerDriver driver = PowerDriverFallbacker.getDriver();

    public BatteryState getState() {
        BatteryState state = new BatteryState();
        state.setState(driver.getState());
        state.setLevel(driver.getChargeLevel());
        return state;
    }
}
