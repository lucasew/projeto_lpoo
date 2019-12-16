package controller.database;

import model.vo.Machine;
import model.vo.MachineState;

import javax.persistence.Query;
import java.util.List;

public class MachineStateController {
    public static List<MachineState> getByMachine(Machine m) {
        Query query = DatabaseController.getInstance()
            .createQuery("select s from MachineState s where machine = :machine_id");
        query.setParameter("machine_id", m);
        return query.getResultList();
    }
}
