package controller.reporter;

import controller.provider.PowerProvider;
import model.vo.BatteryState;
import model.vo.MachineState;

import javax.persistence.EntityManager;

public class BatteryReporter implements Reporter {
    PowerProvider gather;

    public BatteryReporter() {
        this.gather = new PowerProvider();
    }

    @Override
    public MachineState tickTask(EntityManager database, MachineState state) {
        BatteryState batteryState = gather.getState();
        state.setBatteryState(batteryState);
        return state;
    }
}
