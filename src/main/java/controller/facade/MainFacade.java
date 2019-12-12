/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.facade;

import controller.capture.CaptureDaemon;
import controller.database.MachineController;
import controller.reporter.BatteryReporter;
import controller.reporter.PingReporter;
import controller.reporter.Reporter;

import java.util.ArrayList;

import model.Machine;
import view.UIDashboard;

/**
 *
 * @author lucasew
 */
public class MainFacade implements Runnable {
    CaptureDaemon captureDaemon;
    Machine machine;

    private ArrayList<Reporter> getReporters() {
        ArrayList<Reporter> reporters = new ArrayList<>();
        reporters.add(new PingReporter("google.com"));
        reporters.add(new BatteryReporter());
        return reporters;
    }

    private UIDashboard getDashboard() {
        UIDashboard dashboard = new UIDashboard(machine);
        dashboard.setVisible(true);
        return dashboard;
    }

    public MainFacade(String hostname) {
        machine = MachineController.registerMachine(hostname);
        captureDaemon = new CaptureDaemon(1000, getReporters(), machine);
    }

    public void run() {
        UIDashboard dashboard = getDashboard();
        captureDaemon.addListener(dashboard);
        new Thread(captureDaemon, "CaptureDaemon").start();
        new Thread(dashboard, "Dashboard").start();
    }
}
