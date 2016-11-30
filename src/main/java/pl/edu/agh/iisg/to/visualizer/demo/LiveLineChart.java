package main.java.pl.edu.agh.iisg.to.visualizer.demo; /**
 * Created by Cinek on 2016-11-30.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LiveLineChart extends Application {

    private int xSeriesData = 0;
    private int MAX_DATA_POINTS = 50;
    private XYChart.Series series;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private ConcurrentLinkedQueue<Number> data = new ConcurrentLinkedQueue<Number>();

    private NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

    private void init(Stage primaryStage) {
        xAxis = new NumberAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(true);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickMarkVisible(true);
        xAxis.setMinorTickVisible(true);


        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);

        final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
            @Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
        };
        sc.setAnimated(true);
        sc.setId("liveLineChart");
        sc.setTitle("Animated Line Chart");

        series = new XYChart.Series<Number, Number>();
        lineChart.getData().addAll(series);
        lineChart.setMouseTransparent(true);


        series.setName("Packet Size");
        primaryStage.setScene(new Scene(lineChart));
    }


    @Override public void start(Stage stage) {
        stage.setTitle("Animated Line Chart Sample");
        stage.setWidth(1500);
        init(stage);
        stage.show();


        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();


    }

    private class AddToQueue implements Runnable {
        Random random = new Random();
        @Override
        public void run() {
            try {
                data.add(random.nextInt(1518));
                Thread.sleep(300);
                executor.execute(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(LiveLineChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void prepareTimeline() {
        new AnimationTimer() {
            @Override public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (data.isEmpty()) break;
            series.getData().add(new AreaChart.Data(xSeriesData++, data.remove()));
        }

        int amountOfDataToRemove = 0;
        if (series.getData().size() > MAX_DATA_POINTS) {
            amountOfDataToRemove = series.getData().size() - MAX_DATA_POINTS;
            series.getData().remove(0, amountOfDataToRemove);
        }

        xAxis.setTranslateX(amountOfDataToRemove);
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData-1);
    }
}