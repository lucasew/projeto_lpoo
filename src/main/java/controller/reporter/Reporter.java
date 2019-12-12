package controller.reporter;

import model.vo.MachineState;

import javax.persistence.EntityManager;

public interface Reporter {
    MachineState tickTask(EntityManager database, MachineState state);
}
