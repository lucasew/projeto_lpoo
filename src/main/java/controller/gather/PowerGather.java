package controller.gather;

import controller.driver.power.GenericPowerDriver;
import model.BatteryState;
import model.Timestamp;

import java.sql.Time;
import java.util.Calendar;

public class PowerGather {
    private GenericPowerDriver driver;
    public PowerGather(GenericPowerDriver driver) {
        this.driver = driver;
    }

    public BatteryState getState(Timestamp timestamp) {
        BatteryState state = new BatteryState();
        state.setState(driver.getState());
        state.setLevel(driver.getChargeLevel());
        state.setTimestamp(timestamp);
        return state;
    }
}
