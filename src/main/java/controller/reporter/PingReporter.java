package controller.reporter;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import controller.gather.PingGather;
import model.MachineState;
import model.PingState;
import model.TimestampState;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingReporter implements Reporter {
    private String hostToPing;
    private PingGather gather;

    public PingReporter(String hostToPing) {
        GenericPingDriver driver = PingDriverFallbacker.getDriver();
        gather = new PingGather(hostToPing);
    }

    @Override
    public MachineState tickTask(EntityManager database, MachineState state) {
        try {
            PingState pingState = gather.getState();
            state.setPingState(pingState);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return state;
    }
}
