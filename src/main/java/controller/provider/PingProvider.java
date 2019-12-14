package controller.provider;

import controller.provider.driver.ping.GenericPingDriver;
import controller.provider.driver.ping.PingDriverFallbacker;

import java.io.IOException;
import model.vo.PingState;

public class PingProvider {
    private final GenericPingDriver driver = PingDriverFallbacker.getDriver();
    private final String hostToPing;
    public PingProvider(String hostToPing) {
        this.hostToPing = hostToPing;
    }

    public PingState getState() throws IOException, InterruptedException {
        PingState state = new PingState();
        Integer res = driver.pingTo(hostToPing);
        if (res == null) {
            res = 0;
            state.setValido(false);
        }
        state.setLatency(res);
        return state;
    }
}
