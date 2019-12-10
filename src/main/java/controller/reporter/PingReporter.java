package controller.reporter;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import controller.gather.PingGather;
import model.PingState;
import model.TimestampState;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingReporter implements Reporter {
    private String hostToPing;
    private PingState lastState;
    private PingGather gather;

    public PingState getLastState() {
        return lastState;
    }

    public PingReporter(String hostToPing) {
        GenericPingDriver driver = PingDriverFallbacker.getDriver();
        gather = new PingGather(driver, hostToPing);
        try {
            while(this.lastState == null) {
                this.lastState = gather.getState();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PingReporter.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    @Override
    public boolean tickTask(EntityManager database, TimestampState timestamp) {
        try {
            PingState cur = gather.getState();
            if (this.lastState.equals(cur)) {
                return false;
            }
            cur.setTimestamp(timestamp);
            database.persist(lastState);
            this.lastState = cur;
            return true;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PingReporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
