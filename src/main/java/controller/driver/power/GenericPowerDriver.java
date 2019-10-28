package controller.driver.power;

import model.BatteryState;

public abstract class GenericPowerDriver {
    GenericPowerDriver() throws UnsupportedOperationException {};

    public abstract PowerState getState();
    public abstract int getChargeLevel();
}
