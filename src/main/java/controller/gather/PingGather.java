package controller.gather;

import controller.driver.ping.GenericPingDriver;
import model.TimestampState;

import java.io.IOException;
import model.PingState;

public class PingGather {
    private final GenericPingDriver driver;
    private final String hostToPing;
    public PingGather(GenericPingDriver driver, String hostToPing) {
        this.driver = driver;
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
