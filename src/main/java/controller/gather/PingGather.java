package controller.gather;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import model.TimestampState;

import java.io.IOException;
import model.PingState;

public class PingGather {
    private final GenericPingDriver driver = PingDriverFallbacker.getDriver();
    private final String hostToPing;
    public PingGather(String hostToPing) {
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
