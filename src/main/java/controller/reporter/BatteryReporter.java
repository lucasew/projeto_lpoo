package controller.reporter;

import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import controller.gather.PowerGather;

import javax.persistence.EntityManager;
import model.BatteryState;
import model.TimestampState;

public class BatteryReporter implements Reporter {
    PowerGather gather;
    BatteryState state;
    TimestampState lastTimestamp;

    public BatteryState getLastState() {
        return state;
    }

    public BatteryReporter() {
        GenericPowerDriver driver = PowerDriverFallbacker.getDriver();
        this.gather = new PowerGather(driver);
        lastTimestamp = new TimestampState();
        while (state == null) {
            state = gather.getState();
        }
    }

    @Override
    public boolean tickTask(EntityManager database, TimestampState timestamp) {
        BatteryState current = gather.getState();
        if (this.state.equals(current)) {
            return false;
        }
        this.state = current;
        current.setTimestamp(timestamp);
        database.persist(current);
        return true;
    }
}
