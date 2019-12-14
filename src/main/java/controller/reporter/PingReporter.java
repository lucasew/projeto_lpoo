package controller.reporter;

import controller.provider.driver.ping.GenericPingDriver;
import controller.provider.driver.ping.PingDriverFallbacker;
import controller.provider.PingProvider;
import model.vo.MachineState;
import model.vo.PingState;

import javax.persistence.EntityManager;
import java.io.IOException;

public class PingReporter implements Reporter {
    private String hostToPing;
    private PingProvider gather;

    public PingReporter(String hostToPing) {
        GenericPingDriver driver = PingDriverFallbacker.getDriver();
        gather = new PingProvider(hostToPing);
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
