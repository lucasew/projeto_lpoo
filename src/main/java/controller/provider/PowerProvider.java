package controller.provider;

import controller.provider.driver.power.GenericPowerDriver;
import controller.provider.driver.power.PowerDriverFallbacker;
import model.vo.BatteryState;

public class PowerProvider {
    private final GenericPowerDriver driver = PowerDriverFallbacker.getDriver();

    public BatteryState getState() {
        BatteryState state = new BatteryState();
        state.setState(driver.getState());
        state.setLevel(driver.getChargeLevel());
        return state;
    }
}
