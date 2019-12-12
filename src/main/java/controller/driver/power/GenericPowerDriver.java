package controller.driver.power;

import controller.driver.GenericDriver;
import model.PowerState;

public abstract class GenericPowerDriver extends GenericDriver {
    public abstract PowerState getState();
    public abstract int getChargeLevel();
}
