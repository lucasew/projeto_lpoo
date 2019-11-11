package controller.driver.power;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PowerDriverFallbacker {
    private static List<Class<? extends GenericPowerDriver>> pretendents =
            new ArrayList<Class<? extends GenericPowerDriver>>() {{
                add(LinuxPowerDriver.class);
                add(MockedPowerDriver.class);
            }};
    private static GenericPowerDriver backend = null;

    public static GenericPowerDriver getDriver() {
        if (PowerDriverFallbacker.backend != null) {
            return PowerDriverFallbacker.backend;
        }
        for (Class<? extends GenericPowerDriver> pretendent : pretendents) {
            try {
                PowerDriverFallbacker.backend = pretendent.newInstance();
                return PowerDriverFallbacker.backend;
            } catch (IllegalAccessException | InstantiationException | UnsupportedOperationException e) {
                System.out.println(String.format("%s não pode ser utilizado neste contexto por não ser suportado.", pretendent.toString()));
                continue;
            }
        }
        throw new UnsupportedOperationException();
    }
}
