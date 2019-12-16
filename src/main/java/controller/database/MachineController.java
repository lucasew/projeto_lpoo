package controller.database;

import model.vo.Machine;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MachineController {
    public static Machine getMachine(String hostname) {
        Query query = DatabaseController.getInstance()
                .createQuery("select m from Machine m where hostname = :hostname");
        query.setParameter("hostname", hostname);
        List<Machine> machines = query.getResultList();
        if (machines.size() == 1) {
            return machines.get(0);
        }
        return null;
    }

    public static List<Machine> getAllMachines() {
        return DatabaseController.getInstance()
                .createQuery("select m from Machine m")
                .getResultList();
    }
    public static Machine registerMachine(String hostname) {
        Machine m = MachineController.getMachine(hostname);
        if (m != null) {
            return m;
        }
        m = new Machine();
        m.setHostname(hostname);
        EntityManager database = DatabaseController.getInstance();
        database.getTransaction().begin();
        database.persist(m);
        database.getTransaction().commit();
        return m;
    }
}
