package controller.daemon.task;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import controller.gather.PingGather;
import model.PingState;
import model.Timestamp;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingTask implements Task{
    private String hostToPing;
    private PingState lastState;
    private PingGather gather;

    public PingState getLastState() {
        return lastState;
    }

    public PingTask(String hostToPing) {
        GenericPingDriver driver = PingDriverFallbacker.getDriver();
        gather = new PingGather(driver, hostToPing);
        try {
            this.lastState = gather.getState(new Timestamp());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PingTask.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    @Override
    public boolean tickTask(EntityManager database) {
        try {
            PingState cur = gather.getState(new Timestamp());
            if (cur == null)
                return false;
            if (this.lastState.equals(cur)) {
                return false;
            }
            synchronized(this) {
                this.notify();
            }
            this.lastState = cur;
            database.persist(this.lastState);
            return true;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PingTask.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
