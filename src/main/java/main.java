import controller.DatabaseController;
import controller.driver.ping.GenericPingDriver;
import controller.driver.ping.PingDriverFallbacker;
import controller.driver.power.GenericPowerDriver;
import controller.driver.power.PowerDriverFallbacker;
import controller.gather.PingGather;
import controller.gather.PowerGather;
import static java.lang.System.out;
import model.BatteryState;
import model.PingState;
import model.Timestamp;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.util.Timer;

public class main {
    public static void main(String[] args) throws InterruptedException, IOException {
        EntityManager manager = DatabaseController.getInstance();
        GenericPowerDriver powDriver  = PowerDriverFallbacker.getDriver();
        PowerGather powGather = new PowerGather(powDriver);
        GenericPingDriver pingDriver = PingDriverFallbacker.getDriver();
        PingGather pingGather = new PingGather(pingDriver);
        Thread th = Thread.currentThread();
        EntityTransaction trans = manager.getTransaction();
        Timestamp timestamp = new Timestamp();
        BatteryState lastPowState = powGather.getState(timestamp);
        PingState lastPingState = pingGather.getState(timestamp);
        while (true) {
            th.sleep(1000);
            boolean isPersistTimestamp = false;
            timestamp = new Timestamp();
            BatteryState powState = powGather.getState(timestamp);
            PingState pingState = pingGather.getState(timestamp);
            trans.begin();
            if (lastPowState.compareTo(powState) != 0) {
                manager.persist(powState);
                isPersistTimestamp = true;
                out.println("Persist power");
            }
            if (lastPingState.compareTo(pingState) != 0) {
                manager.persist(pingState);
                isPersistTimestamp = true;
                out.println("Persist ping");
            }
            if (isPersistTimestamp) {
                manager.persist(timestamp);
                out.println("Persist timestamp");
            }
            manager.flush();
            trans.commit();
            lastPowState = powState;
            lastPingState = pingState;
            out.println("Fim de loop");
        }
    }
}
