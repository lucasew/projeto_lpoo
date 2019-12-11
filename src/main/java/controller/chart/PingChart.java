/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;
import model.PingState;
import model.TimestampState;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYSeriesCollection;

import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

/**
 *
 * @author lucasew
 */
public class PingChart extends CommonChart {
    public PingChart() {
        super("Histórico de ping",  "Tempo de ping");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PingChart example = new PingChart();
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
        for (TimestampState este : this.amostras) {
            Query query = DatabaseController.getInstance()
                    .createQuery("select p from PingState p where id = :id");
            query.setParameter("id", este.getId());
            List<PingState> b = query.getResultList();
            if (b.size() != 1) {
                System.out.println(".");
                continue;
            }
            PingState latency = b.get(0);
            FixedMillisecond timestamp = new FixedMillisecond(este.getTimestamp().getTime());

            if (!latency.isValido()) {
                falhaPing.add(new TimeSeriesDataItem(timestamp, 100));
            } else {
                falhaPing.add(new TimeSeriesDataItem(timestamp, 0));
            }
            series.add(new TimeSeriesDataItem(timestamp, b.get(0).getLatency()));
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(falhaPing);
        return dataset;
    }
}
