package controller.driver.hostname;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxHostnameDriver extends GenericHostnameDriver {
    public String getHostname() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("hostname");
        return new BufferedReader(new InputStreamReader(proc.getInputStream())).readLine();
    }

    @Override
    public void Initialize() throws UnsupportedOperationException {}
}
