package controller.driver.hostname;

import controller.driver.GenericDriver;

import java.io.IOException;

public abstract class GenericHostnameDriver extends GenericDriver {
    public abstract String getHostname() throws IOException;
}
