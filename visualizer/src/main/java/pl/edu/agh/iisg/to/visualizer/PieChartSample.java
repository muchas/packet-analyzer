package pl.edu.agh.iisg.to.visualizer;
/**
 * Created by Cinek on 2016-11-29.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

import java.util.concurrent.CountDownLatch;

public class PieChartSample extends Application implements StatisticChart {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static PieChartSample pieChartSample = null;
    PieChart chart;
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    public void setAmountForSet(String label, int amount) {
        pieChartData.add(new PieChart.Data(label, amount));
    }
    public void initChart(String t) {
        chart = new PieChart(pieChartData);
        chart.setTitle(t);
    }

    public static PieChartSample waitPieChartSample() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pieChartSample;
    }

    public static void setPieChartSample(PieChartSample pieChartSample0) {
        pieChartSample = pieChartSample0;
        latch.countDown();
    }

    public PieChartSample() {
        setPieChartSample(this);
    }

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Statistics");
        stage.setWidth(500);
        stage.setHeight(500);

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}