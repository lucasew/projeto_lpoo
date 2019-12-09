package controller.daemon.task;

import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import model.PowerState;

import javax.persistence.EntityManager;

public class PowerTask implements Task {
    GenericPowerDriver driver;
    PowerState state;

    public PowerState getLastState() {
        return state;
    }

    public PowerTask() {
        driver = PowerDriverFallbacker.getDriver();
        state = driver.getState();
    }

    @Override
    public boolean tickTask(EntityManager database) {
        PowerState current = driver.getState();
        if (current.equals(this.state)) {
            return false;
        }
        this.state = current;
        database.persist(current);
        return true;
    }
}
