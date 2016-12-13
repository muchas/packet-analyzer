package main.java.pl.edu.agh.iisg.to.visualizer.demo; /**
 * Created by Cinek on 2016-11-30.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LiveLineChart extends Application {
    private int xSeriesData = 0;
    private int MAX_DATA_POINTS = 100;
    private XYChart.Series series;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private ConcurrentLinkedQueue<Number> data = new ConcurrentLinkedQueue<Number>();

    private NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    ScrollPane scrollPane = new ScrollPane();
    Pane content = new Pane();

    private void init(Stage primaryStage) {
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickMarkVisible(true);
        xAxis.setMinorTickVisible(true);
        yAxis.setAutoRanging(true);

        series = new XYChart.Series<Number, Number>();
        lineChart.getData().addAll(series);
        series.setName("Packet Size");
        content.getChildren().add(lineChart);
        scrollPane.setContent(content);
        scrollPane.setPrefSize(1500, 800);
        scrollPane.setPannable(true);
        primaryStage.setScene(new Scene(scrollPane, 1500, 500));
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Animated Line Chart Sample");
        stage.setWidth(500);
        init(stage);
        stage.show();

        executor = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        prepareTimeline();
    }

    private class AddToQueue implements Runnable {
        Random random = new Random();

        @Override
        public void run() {
            try {
                data.add(random.nextInt(1518));
                Thread.sleep(random.nextInt(300));
                executor.execute(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(LiveLineChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void prepareTimeline() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (data.isEmpty()) break;
            series.getData().add(new AreaChart.Data(xSeriesData++, data.remove()));
        }

        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS > 0 ? xSeriesData - MAX_DATA_POINTS : 0);
        xAxis.setUpperBound(xSeriesData - 1 > MAX_DATA_POINTS ? xSeriesData - 1 : MAX_DATA_POINTS);
        System.out.println(series.getData().size());
    }
}