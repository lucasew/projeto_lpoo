/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.facade;

import controller.reporter.BatteryReporter;
import controller.reporter.PingReporter;
import controller.reporter.Reporter;
import java.util.ArrayList;
import view.UIDashboard;

/**
 *
 * @author lucasew
 */
public class MainFacade implements Runnable {
    public void run() {
        ArrayList<Reporter> reporters = new ArrayList<Reporter>();
        PingReporter pingt = new PingReporter("google.com");
        BatteryReporter powert = new BatteryReporter();
        reporters.add(pingt);
        reporters.add(powert);
        Thread th = new Thread(new CaptureDaemon(1000, reporters));
        th.start();
        UIDashboard dashboard = new UIDashboard(pingt, powert);
        dashboard.setVisible(true);
        dashboard.run();
    }
}
