package controller.reporter;

import controller.gather.PowerGather;
import model.vo.BatteryState;
import model.vo.MachineState;

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
