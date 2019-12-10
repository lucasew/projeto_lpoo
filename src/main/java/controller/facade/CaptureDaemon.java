package controller.facade;

import controller.DatabaseController;
import controller.reporter.Reporter;
import model.TimestampState;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;

public class CaptureDaemon implements Runnable {
    EntityManager database;
    List<Reporter> reporters;
    int sleepTime;

    public CaptureDaemon(int sleepTime, List<Reporter> reporters) {
        this.sleepTime = sleepTime;
        this.reporters = reporters;
        database = DatabaseController.getInstance();
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimestampState timestamp = new TimestampState();
                Thread.sleep(sleepTime);
                database.getTransaction().begin();
                try {
                    synchronized (reporters) {
                        Stream<Boolean> ret = reporters
                                .stream()
                                .map(t -> t.tickTask(database, timestamp));
                        if (ret.anyMatch(b -> b)) {
                            database.persist(timestamp);
                        }
                        database.getTransaction().commit();
                    }
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
