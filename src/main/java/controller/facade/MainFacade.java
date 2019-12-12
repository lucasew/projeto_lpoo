/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.facade;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import controller.capture.CaptureDaemon;
import controller.database.MachineController;
import controller.reporter.BatteryReporter;
import controller.reporter.PingReporter;
import controller.reporter.Reporter;

import java.util.ArrayList;

import model.exception.SingleInstanceException;
import model.vo.Machine;
import view.MessageBoxBuilder;
import view.UIDashboard;

import javax.swing.*;

/**
 *
 * @author lucasew
 */
public class MainFacade implements Runnable {
    Machine machine;

    private ArrayList<Reporter> getReporters() {
        ArrayList<Reporter> reporters = new ArrayList<>();
        reporters.add(new PingReporter("google.com"));
        reporters.add(new BatteryReporter());
        return reporters;
    }

    public MainFacade(String hostname) {
        machine = MachineController.registerMachine(hostname);
    }

    public void run() {
        CaptureDaemon captureDaemon = new CaptureDaemon(1000, getReporters(), machine);
        try {
            UIDashboard ui = new UIDashboard(machine, captureDaemon);
            ui.setVisible(true);
            new Thread(ui, "Dashboard").start();
        } catch (SingleInstanceException e) {
            MessageBoxBuilder.showError("Não é possível criar mais de uma janela deste tipo");
        }
    }
}
