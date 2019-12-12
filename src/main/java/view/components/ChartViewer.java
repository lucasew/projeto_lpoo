package view.components;

import controller.chart.CommonChart;

public class ChartViewer implements Runnable {
    private CommonChart chart;

    public ChartViewer(CommonChart chart) {
        this.chart = chart;
    }

    @Override
    public void run() {
        chart.setLocationRelativeTo(null);
        chart.setSize(800, 600);
        chart.setVisible(true);
    }
}
