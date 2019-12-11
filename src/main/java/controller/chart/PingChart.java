/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;
import model.BatteryState;
import model.PingState;
import model.TimestampState;
import org.jfree.data.xy.XYSeries;
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
    public XYSeriesCollection buildDataset() {
        final XYSeries series = new XYSeries("Ping");
        final XYSeries falhaPing = new XYSeries("Falhas");
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
            long timestamp = este.getTimestamp().getTimeInMillis() / 1000;
            if (!latency.isValido()) {
                falhaPing.add(timestamp, 100);
            } else {
                falhaPing.add(timestamp, 0);
            }
            series.add(timestamp, b.get(0).getLatency());
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(falhaPing);
        return dataset;
    }
}
