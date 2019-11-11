package controller.driver.power;

import model.PowerState;

public interface GenericPowerDriver {
    void Initialize() throws UnsupportedOperationException;

    PowerState getState();
    int getChargeLevel();
}
