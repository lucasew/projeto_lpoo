package controller.gather;

import controller.driver.ping.GenericPingDriver;
import model.Timestamp;

import java.io.IOException;
import model.PingState;

public class PingGather {
    private final GenericPingDriver driver;
    private final String hostToPing;
    public PingGather(GenericPingDriver driver, String hostToPing) {
        this.driver = driver;
        this.hostToPing = hostToPing;
    }

    public PingState getState(Timestamp timestamp) throws IOException, InterruptedException {
        Integer res = driver.pingTo(hostToPing);
        PingState state = new PingState(timestamp, res);
        state.setTimestamp(timestamp);
        return state;
    }
}
