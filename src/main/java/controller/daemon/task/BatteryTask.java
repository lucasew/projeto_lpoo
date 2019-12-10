package controller.daemon.task;

import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import controller.gather.PowerGather;

import javax.persistence.EntityManager;
import model.BatteryState;
import model.Timestamp;

public class BatteryTask implements Task {
    PowerGather gather;
    BatteryState state;

    public BatteryState getLastState() {
        return state;
    }

    public BatteryTask() {
        GenericPowerDriver driver = PowerDriverFallbacker.getDriver();
        this.gather = new PowerGather(driver);
        state = gather.getState(new Timestamp());
    }

    @Override
    public boolean tickTask(EntityManager database) {
        BatteryState current = gather.getState(new Timestamp());
        if (current.equals(this.state)) {
            return false;
        }
        synchronized(this) {
            this.notify();
        }
        this.state = current;
        database.persist(current);
        return true;
    }
}
