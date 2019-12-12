/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.database.MachineController;
import model.vo.Machine;
import model.vo.MachineState;
import model.vo.PingState;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import javax.swing.*;

/**
 *
 * @author lucasew
 */
public class PingChart extends CommonChart {
    public PingChart(Machine machine) {
        super(machine, "Histórico de ping",  "Tempo de ping");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PingChart example = new PingChart(MachineController.getMachine("acer-manjado"));
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    @Override
    public TimeSeriesCollection buildDataset() {
        final TimeSeries series = new TimeSeries("Ping");
        final TimeSeries falhaPing = new TimeSeries("Falhas");
        for (MachineState este : this.amostras) {
            PingState pingState = este.getPingState();
            FixedMillisecond timestamp = new FixedMillisecond(este.getTimestampState().getTimestamp().getTime());
            if (!pingState.isValido()) {
                falhaPing.add(new TimeSeriesDataItem(timestamp, 100));
            } else {
                falhaPing.add(new TimeSeriesDataItem(timestamp, 0));
            }
            series.add(new TimeSeriesDataItem(timestamp, pingState.getLatency()));
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(falhaPing);
        return dataset;
    }
}
