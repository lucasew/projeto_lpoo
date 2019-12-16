/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.chart;

import controller.database.MachineController;
import model.vo.BatteryState;
import model.vo.Machine;
import model.vo.MachineState;
import model.vo.PowerState;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import javax.swing.*;

/**
 *
 * @author lucasew
 */
public class BatteryChart extends CommonChart {
    public BatteryChart(Machine machine) {
        super(machine, "Histórico do uso de bateria", "Uso de bateria");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatteryChart example = new BatteryChart(MachineController.getMachine("acer-manjado"));
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    @Override
    public TimeSeriesCollection buildDataset() {
        final TimeSeries series = new TimeSeries("Bateria");
        final TimeSeries isCarregando = new TimeSeries("Está carregando?");
        for (MachineState este : this.amostras) {
            FixedMillisecond timestamp = new FixedMillisecond(este.getTimestampState().getTimestamp().getTime());
            BatteryState batteryState = este.getBatteryState();
            if (batteryState.getState() == PowerState.Discharging) {
                isCarregando.add(new TimeSeriesDataItem(timestamp, 0));
            } else {
                isCarregando.add(new TimeSeriesDataItem(timestamp, 100));
            }
            series.add(new TimeSeriesDataItem(timestamp, batteryState.getLevel()));
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(isCarregando);
        return dataset;
    }
}
