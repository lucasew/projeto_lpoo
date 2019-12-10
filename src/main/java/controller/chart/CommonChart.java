/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chart;

import controller.DatabaseController;

import java.util.List;

import model.TimestampState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

/**
 *
 * @author lucasew
 */
public abstract class CommonChart extends JFrame {
    List<TimestampState> amostras;

    public CommonChart(String titulo, String descricao, String eixo) {
        super(titulo);

        this.amostras = DatabaseController.getInstance()
                .createQuery("select t from TimestampState t")
                .getResultList();
        System.out.printf("Amostras: %d\n", this.amostras.size());
        JFreeChart chart = ChartFactory.createXYLineChart(
                titulo,
                "Tempo",
                eixo,
                buildDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = (XYPlot)chart.getPlot();

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

    }
    
    public abstract XYSeriesCollection buildDataset();
}
