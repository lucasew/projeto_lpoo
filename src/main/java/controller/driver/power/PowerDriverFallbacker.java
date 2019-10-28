package controller.driver.power;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PowerDriverFallbacker {
    private static List<Class<? extends GenericPowerDriver>> pretendents =
            new ArrayList<Class<? extends GenericPowerDriver>>() {{
                add(LinuxPowerDriver.class);
            }};

    public static GenericPowerDriver getDriver() {
        for (Class<? extends GenericPowerDriver> pretendent : pretendents) {
            try {
                return pretendent.newInstance();
            } catch (IllegalAccessException | InstantiationException | UnsupportedOperationException e) {
                e.printStackTrace();
                continue;
            }
        }
        throw new UnsupportedOperationException();
    }
}
