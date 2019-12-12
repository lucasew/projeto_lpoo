/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;
import model.BatteryState;
import model.PowerState;
import model.TimestampState;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

/**
 *
 * @author lucasew
 */
public class BatteryChart extends CommonChart {
    public BatteryChart() {
        super("Histórico do uso de bateria", "Uso de bateria");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatteryChart example = new BatteryChart();
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
        for (TimestampState este : this.amostras) {
            Query query = DatabaseController.getInstance()
                    .createQuery("select b from BatteryState b where id = :id");
            query.setParameter("id", este.getId());
            List<BatteryState> b = query.getResultList();
            if (b.size() != 1) {
                System.out.println(".");
                continue;
            }
            FixedMillisecond timestamp = new FixedMillisecond(este.getTimestamp().getTime());
            BatteryState state = b.get(0);
            if (state.getState() == PowerState.Discharging) {
                isCarregando.add(new TimeSeriesDataItem(timestamp, 0));
            } else {
                isCarregando.add(new TimeSeriesDataItem(timestamp, 100));
            }
            series.add(new TimeSeriesDataItem(timestamp, state.getLevel()));
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(isCarregando);
        return dataset;
    }
}
