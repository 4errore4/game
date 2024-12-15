package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsChart {
    private final GameStatistics statistics;

    public StatisticsChart(GameStatistics statistics) {
        this.statistics = statistics;
    }

    public void DisplayCharts() {
        JFrame frame = new JFrame("Game Statistics");
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(2, 1));

        frame.add(CreateResourceChart());

        frame.add(CreateHouseChart());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel CreateResourceChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        addDataToDataset(dataset, statistics.getDays(), statistics.getPlayerRice(), "Player - Rice");
        addDataToDataset(dataset, statistics.getDays(), statistics.getPlayerWater(), "Player - Water");
        addDataToDataset(dataset, statistics.getDays(), statistics.getPlayerPeople(), "Player - People");

        addDataToDataset(dataset, statistics.getDays(), statistics.getAiRice(), "AI - Rice");
        addDataToDataset(dataset, statistics.getDays(), statistics.getAiWater(), "AI - Water");
        addDataToDataset(dataset, statistics.getDays(), statistics.getAiPeople(), "AI - People");

        JFreeChart chart = ChartFactory.createLineChart(
                "Resource Trends", "Day", "Amount", dataset
        );
        configureChartAxis(chart);

        return new ChartPanel(chart);
    }

    private JPanel CreateHouseChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        addDataToDataset(dataset, statistics.getDays(), statistics.getPlayerHouses(), "Player - Houses");
        addDataToDataset(dataset, statistics.getDays(), statistics.getPlayerDiscoveredCells(), "Player - DiscoveredCells");

        addDataToDataset(dataset, statistics.getDays(), statistics.getAiHouses(), "AI - Houses");
        addDataToDataset(dataset, statistics.getDays(), statistics.getAiDiscoveredCells(), "AI - DiscoveredCells");

        JFreeChart chart = ChartFactory.createLineChart(
                "House Trends", "Day", "CellsStatistic", dataset
        );
        configureChartAxis(chart);

        return new ChartPanel(chart);
    }

    private void addDataToDataset(DefaultCategoryDataset dataset, List<Integer> days, List<Integer> values, String seriesName) {
        for (int i = 0; i < days.size(); i++) {
            dataset.addValue(values.get(i), seriesName, String.valueOf(days.get(i)));
        }
    }

    private void configureChartAxis(JFreeChart chart) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    }
}
