package controller.daemon;

import controller.DatabaseController;
import controller.daemon.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class CaptureDaemon implements Runnable {
    EntityManager database;
    List<Task> tasks;
    int sleepTime;

    public CaptureDaemon(int sleepTime, List<Task> tasks) {
        this.sleepTime = sleepTime;
        this.tasks = tasks;
        database = DatabaseController.getInstance();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(sleepTime);
                database.getTransaction().begin();
                try {
                    tasks.forEach(t -> t.tickTask(database));
                    database.getTransaction().commit();
                } catch (Exception e){
                    e.printStackTrace();
                    database.getTransaction().rollback();
                }
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
