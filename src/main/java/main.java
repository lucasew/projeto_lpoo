import controller.driver.hostname.GenericHostnameDriver;
import controller.driver.hostname.HostnameDriverFallbacker;
import controller.facade.MainFacade;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        String hostname = HostnameDriverFallbacker.getDriver().getHostname();
        if (args.length > 1) {
            hostname = args[1];
        }
        new MainFacade(hostname).run();
    }
}
