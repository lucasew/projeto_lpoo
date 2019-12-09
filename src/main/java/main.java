import controller.DatabaseController;
import controller.daemon.CaptureDaemon;
import controller.daemon.task.PingTask;
import controller.daemon.task.PowerTask;
import controller.daemon.task.Task;
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
import java.util.ArrayList;
import java.util.Timer;

public class main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new PingTask("google.com"));
        tasks.add(new PowerTask());
        Thread th = new Thread(new CaptureDaemon(1000, tasks));
        th.start();
    }
}
