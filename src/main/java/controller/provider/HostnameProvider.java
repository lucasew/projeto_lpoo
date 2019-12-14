package controller.provider;

import controller.provider.driver.hostname.GenericHostnameDriver;
import controller.provider.driver.hostname.HostnameDriverFallbacker;

import java.io.IOException;

public class HostnameProvider {
    GenericHostnameDriver driver;

    public HostnameProvider() {
        driver = HostnameDriverFallbacker.getDriver();
    }
    public String getHostname() throws IOException {
        return driver.getHostname();
    };
}
