package pl.edu.agh.iisg.to.visualizer;
/**
 * Created by Cinek on 2016-11-30.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to.collector.Packet;

import java.util.concurrent.ConcurrentLinkedQueue;


public class LiveLineChart extends Application {
    private int xSeriesData = 0;
    private int MAX_DATA_POINTS = 100;
    private XYChart.Series series;
    private ConcurrentLinkedQueue<Packet> packetsQueue = new ConcurrentLinkedQueue<>();
    private NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    ScrollPane scrollPane = new ScrollPane();
    Pane content = new Pane();
    AnimationTimer animationTimer;

    private void init(Stage primaryStage) {
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickMarkVisible(true);
        xAxis.setMinorTickVisible(true);
        yAxis.setAutoRanging(true);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        };

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
        prepareTimeline();
    }

    @Override
    public void stop() {
        animationTimer.stop();
    }

    protected void addPacket (Packet packet) {
        packetsQueue.add(packet);
    }

    private void prepareTimeline() {
        animationTimer.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (packetsQueue.isEmpty()) break;
            series.getData().add(new AreaChart.Data(xSeriesData++, (Integer) packetsQueue.remove().getProperty("length").getValue()));
        }
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS > 0 ? xSeriesData - MAX_DATA_POINTS : 0);
        xAxis.setUpperBound(xSeriesData - 1 > MAX_DATA_POINTS ? xSeriesData - 1 : MAX_DATA_POINTS);
    }
}