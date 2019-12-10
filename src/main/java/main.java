import controller.daemon.CaptureDaemon;
import controller.daemon.task.PingTask;
import controller.daemon.task.BatteryTask;
import controller.daemon.task.Task;

import java.util.ArrayList;
import view.UIDashboard;

public class main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        PingTask pingt = new PingTask("google.com");
        BatteryTask powert = new BatteryTask();
        tasks.add(pingt);
        tasks.add(powert);
        Thread th = new Thread(new CaptureDaemon(1000, tasks));
        th.start();
        UIDashboard dashboard = new UIDashboard(pingt, powert);
        dashboard.setVisible(true);
        dashboard.run();
    }
}
