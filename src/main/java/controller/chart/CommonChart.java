/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;

import java.util.List;

import controller.lifecycle.Closeable;
import controller.lifecycle.DestroyWindowEventHandler;
import controller.lifecycle.WindowCounter;
import model.Machine;
import model.MachineState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

import javax.persistence.Query;
import javax.swing.*;

/**
 *
 * @author lucasew
 */
public abstract class CommonChart extends JFrame implements Closeable {
    List<MachineState> amostras;
    Machine machine;

    public CommonChart(Machine machine, String titulo, String eixo) {
        super(String.format("%s: %s", titulo, machine.getHostname()));
        WindowCounter.increment();
        this.machine = machine;

        Query query = DatabaseController.getInstance()
                .createQuery("select s from MachineState s where machine = :machine_id")
                .setParameter("machine_id", machine);
        this.amostras = query.getResultList();
        System.out.printf("Amostras: %d\n", this.amostras.size());
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                null,
                "Tempo",
                eixo,
                buildDataset(),
                true,
                true,
                false
        );
        XYPlot plot = (XYPlot)chart.getPlot();

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        this.addWindowListener(new DestroyWindowEventHandler(this));
    }

    public void close() {
        WindowCounter.decrement();
    }

    public abstract TimeSeriesCollection buildDataset();
}
