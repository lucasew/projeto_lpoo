package controller.provider.driver.hostname;

import java.util.ArrayList;
import java.util.List;

public class HostnameDriverFallbacker {
    private static final List<Class<? extends GenericHostnameDriver>> pretendents =
            new ArrayList<Class<? extends GenericHostnameDriver>>() {{
                add(LinuxHostnameDriver.class);
            }};
    private static GenericHostnameDriver backend = null;

    public static GenericHostnameDriver getDriver() {
        if (HostnameDriverFallbacker.backend != null) {
            return HostnameDriverFallbacker.backend;
        }
        for (Class<? extends GenericHostnameDriver> pretendent : pretendents) {
            try {
                HostnameDriverFallbacker.backend = pretendent.newInstance();
                return HostnameDriverFallbacker.backend;
            } catch (IllegalAccessException | InstantiationException | UnsupportedOperationException e) {
                System.out.println(String.format("%s não pode ser utilizado neste contexto por não ser suportado.", pretendent.toString()));
            }
        }
        throw new UnsupportedOperationException();
    }
}
