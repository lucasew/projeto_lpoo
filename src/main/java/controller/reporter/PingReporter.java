package controller.reporter;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import controller.gather.PingGather;
import model.vo.MachineState;
import model.vo.PingState;

import javax.persistence.EntityManager;
import java.io.IOException;

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
