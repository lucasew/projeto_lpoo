import controller.DatabaseController;
import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import controller.gather.PowerGather;
import model.BatteryState;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Timer;

public class main {
    public static void main(String[] args) throws InterruptedException {
        EntityManager manager = DatabaseController.getInstance();
        GenericPowerDriver driver  = PowerDriverFallbacker.getDriver();
        PowerGather gather = new PowerGather(driver);
        Thread th = Thread.currentThread();
        EntityTransaction trans = manager.getTransaction();
        BatteryState lastState = gather.getState();
        while (true) {
            th.sleep(1000);
            BatteryState state = gather.getState();
            if (lastState.compareTo(state) == 0) {
                continue;
            }
            trans.begin();
            manager.persist(gather.getState());
            trans.commit();
            lastState = state;
        }
    }
}
