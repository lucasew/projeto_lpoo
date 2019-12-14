package controller.provider.driver.hostname;

import controller.provider.driver.GenericDriver;

import java.io.IOException;

public abstract class GenericHostnameDriver extends GenericDriver {
    public abstract String getHostname() throws IOException;
}
