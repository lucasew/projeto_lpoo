package controller.gather;

import controller.driver.ping.GenericPingDriver;
import controller.driver.power.GenericPowerDriver;
import model.BatteryState;
import model.Timestamp;

import java.io.IOException;
import java.util.Calendar;
import model.PingState;

public class PingGather {
    private GenericPingDriver driver;
    public PingGather(GenericPingDriver driver) {
        this.driver = driver;
    }

    public PingState getState(Timestamp timestamp) throws IOException, InterruptedException {
        Integer res = driver.pingTo("google.com");
        PingState state = new PingState(timestamp, res);
        state.setTimestamp(timestamp);
        return state;
    }
}
