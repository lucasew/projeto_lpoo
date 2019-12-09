package controller.daemon.task;

import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import model.PingState;
import model.Timestamp;

import javax.persistence.EntityManager;
import java.io.IOException;

public class PingTask implements Task{
    private String hostToPing;
    private Integer state;
    private GenericPingDriver driver;
    private PingState lastState;

    public PingState getLastState() {
        return lastState;
    }

    public PingTask(String hostToPing) {
        driver = PingDriverFallbacker.getDriver();
        this.hostToPing = hostToPing;
        try {
            this.state = driver.pingTo(hostToPing);
        } catch (IOException | InterruptedException e) {
            this.state = null;
        }
    }

    Integer handleRead() {
        try {
            return driver.pingTo(hostToPing);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean tickTask(EntityManager database) {
        Integer current = handleRead();
        if (this.state == null) {
            return false;
        }
        if (this.state.equals(current)) {
            return false;
        }
        this.lastState = new PingState(new Timestamp(), current);
        database.persist(this.lastState);
        this.state = current;
        return true;
    }
}
