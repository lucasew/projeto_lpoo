package controller.reporter;

import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import controller.gather.PowerGather;
import model.BatteryState;
import model.MachineState;

import javax.persistence.EntityManager;

public class BatteryReporter implements Reporter {
    PowerGather gather;

    public BatteryReporter() {
        this.gather = new PowerGather();
    }

    @Override
    public MachineState tickTask(EntityManager database, MachineState state) {
        BatteryState batteryState = gather.getState();
        state.setBatteryState(batteryState);
        return state;
    }
}
