/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.chart;

import java.util.List;

import controller.database.MachineStateController;
import controller.lifecycle.Closeable;
import controller.lifecycle.DestroyWindowEventHandler;
import controller.lifecycle.WindowCounter;
import model.vo.Machine;
import model.vo.MachineState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

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
        this.amostras = MachineStateController.getByMachine(machine);
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
        panel.setMouseWheelEnabled(true);
        panel.setHorizontalAxisTrace(true);
        panel.setVerticalAxisTrace(true);
        setContentPane(panel);

        this.addWindowListener(new DestroyWindowEventHandler(this));
    }

    public void close() {
        WindowCounter.decrement();
    }



    public abstract TimeSeriesCollection buildDataset();
}
