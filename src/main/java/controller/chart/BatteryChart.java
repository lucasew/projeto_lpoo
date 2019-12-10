/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;
import model.BatteryState;
import model.TimestampState;
import org.hibernate.metamodel.relational.Database;
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
        super("Histórico do uso de bateria", "Exibição do histórico do uso de bateria", "Uso de bateria");
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
    public XYSeriesCollection buildDataset() {
        final XYSeries series = new XYSeries("Bateria");
        for (TimestampState este : this.amostras) {
            Query query = DatabaseController.getInstance()
                    .createQuery("select b from BatteryState b where id = :id");
            query.setParameter("id", este.getId());
            List<BatteryState> b = query.getResultList();
            if (b.size() != 1) {
                System.out.println(".");
                continue;
            }
            series.add(este.getTimestamp().getTimeInMillis(), b.get(0).getLevel());
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;

    }
    
}
