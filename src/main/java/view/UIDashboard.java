/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.database.MachineController;
import controller.driver.hostname.HostnameDriverFallbacker;
import controller.lifecycle.Closeable;
import controller.lifecycle.DestroyWindowEventHandler;
import controller.lifecycle.WindowCounter;
import controller.capture.CaptureDaemon;
import controller.chart.BatteryChart;
import controller.chart.PingChart;
import controller.extractor.BatteryStateExtractor;
import controller.extractor.PingStateExtractor;
import controller.reporter.BatteryReporter;
import controller.reporter.PingReporter;
import controller.reporter.Reporter;
import model.exception.SingleInstanceException;
import model.vo.Machine;
import view.components.ChartViewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucasew
 */
public class UIDashboard extends javax.swing.JFrame implements Closeable {
    private static int instancias = 0;
    private Machine machine;
    private CaptureDaemon captureDaemon;

    private static List<Reporter> getDefaultReporters() {
        ArrayList<Reporter> ret = new ArrayList<Reporter>() {{
            add(new PingReporter("google.com"));
            add(new BatteryReporter());
        }};
        return ret;
    }

    public static void main(String[] args) throws IOException, SingleInstanceException {
        new UIDashboard().setVisible(true);
    }

    public UIDashboard() throws IOException, SingleInstanceException {
        this(HostnameDriverFallbacker.getDriver().getHostname());
    }

    public UIDashboard(String hostname) throws SingleInstanceException {
        this(hostname, getDefaultReporters());
    }
    public UIDashboard(String hostname, List<Reporter> reporters) throws SingleInstanceException {
        this.machine = MachineController.registerMachine(hostname);
        this.captureDaemon = new CaptureDaemon(1000, reporters, machine);
        if (instancias > 0) {
            throw new SingleInstanceException("Apenas uma instância desta view pode existir ao mesmo tempo");
        }
        instancias++;
        initComponents();
        this.setTitle("Dashboard - Monitor de Recursos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowCounter.increment();
        this.addWindowListener(new DestroyWindowEventHandler(this));
        this.run();
    }

    public void close() {
        instancias--;
        WindowCounter.decrement();
        this.captureDaemon.stop();
        System.out.println("Parando daemon...");
    }

    private void run() {
        captureDaemon.addListener(this.btnDashboardPing);
        captureDaemon.addListener(this.btnDashboardBattery);
        this.btnDashboardPing.setExtractor(new PingStateExtractor(1000));
        this.btnDashboardBattery.setExtractor(new BatteryStateExtractor());
        new Thread(captureDaemon).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDashboardPing = new view.components.DashboardButton();
        btnDashboardBattery = new view.components.DashboardButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnDashboardPing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/icon/desconectado.png"))); // NOI18N
        btnDashboardPing.setText("Sem informações");
        btnDashboardPing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardPingActionPerformed(evt);
            }
        });

        btnDashboardBattery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/icon/battery_unknown.png"))); // NOI18N
        btnDashboardBattery.setText("Sem informações");
        btnDashboardBattery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardBatteryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDashboardBattery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDashboardPing, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnDashboardPing, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDashboardBattery, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardPingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardPingActionPerformed
        new ChartViewer(new PingChart(machine)).run();
    }//GEN-LAST:event_btnDashboardPingActionPerformed

    private void btnDashboardBatteryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardBatteryActionPerformed
        new ChartViewer(new BatteryChart(machine)).run();
    }//GEN-LAST:event_btnDashboardBatteryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.components.DashboardButton btnDashboardBattery;
    private view.components.DashboardButton btnDashboardPing;
    // End of variables declaration//GEN-END:variables
}
