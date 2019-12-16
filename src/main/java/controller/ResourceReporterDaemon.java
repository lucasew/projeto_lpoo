package controller;

import controller.database.DatabaseController;
import controller.lifecycle.WindowCounter;
import controller.reporter.Reporter;
import model.vo.Machine;
import model.vo.MachineState;
import model.vo.TimestampState;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ResourceReporterDaemon implements Runnable {
    EntityManager database;
    List<Reporter> reporters;
    Machine machine;
    int sleepTime;
    MachineState lastState;
    List<MachineStateListener> listeners = new ArrayList<>();

    public ResourceReporterDaemon(int sleepTime, List<Reporter> reporters, Machine machine) {
        this.sleepTime = sleepTime;
        this.reporters = reporters;
        this.machine = machine;
        database = DatabaseController.getInstance();
        lastState = new MachineState();
    }

    public void addListener(MachineStateListener listener) {
        listeners.add(listener);
    }

    private boolean isRunning = true;

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        try {
            while (isRunning && WindowCounter.getCounter() > 0) {
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
                        for (MachineStateListener listener : listeners) { // Notifica quem pediu que o estado mudou
                            listener.handleMachineStateChange(lastState);
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
