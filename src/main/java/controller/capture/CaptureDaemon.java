package controller.capture;

import controller.DatabaseController;
import controller.reporter.Reporter;
import model.Machine;
import model.MachineState;
import model.TimestampState;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CaptureDaemon implements Runnable {
    EntityManager database;
    List<Reporter> reporters;
    Machine machine;
    int sleepTime;
    MachineState lastState;
    List<CaptureListener> listeners = new ArrayList<>();

    public CaptureDaemon(int sleepTime, List<Reporter> reporters, Machine machine) {
        this.sleepTime = sleepTime;
        this.reporters = reporters;
        this.machine = machine;
        database = DatabaseController.getInstance();
        lastState = new MachineState();
    }

    public void addListener(CaptureListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(sleepTime);
                database.getTransaction().begin();
                try {
                    synchronized (lastState) {
                        for (Reporter reporter : reporters) {
                            lastState = lastState.derivate(reporter.tickTask(database, lastState)); // Funcional btw
                        }
                        lastState.setTimestampState(new TimestampState());
                        lastState.setId(0); // Para não ficar atualizando a mesma coisa
                        lastState.setMachine(machine);
                        for (CaptureListener listener : listeners) { // Notifica quem pediu que o estado mudou
                            listener.handleMachineStateCapture(lastState);
                        }
                        database.persist(lastState);
                        database.getTransaction().commit();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    database.getTransaction().rollback();
                }
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public MachineState getLastState() {
        return lastState;
    }

    public void setLastState(MachineState lastState) {
        this.lastState = lastState;
    }
}
