package pl.edu.agh.iisg.to.visualizer; /**
 * Created by Cinek on 2016-11-30.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class BarChartSample extends Application implements StatisticChart {

    private XYChart.Series series;

    public void setData(String label, int amount) {
        series.getData().add(new XYChart.Data(label, amount));
    }

    @Override public void init() {
        series = new XYChart.Series();
    }

    @Override public void start(Stage stage) {

        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        int sceneWidth = 800;
        int sceneHeight = 600;
        bc.setCategoryGap(sceneWidth/6);
        bc.setTitle("Ports Segregation");
        xAxis.setLabel("Class");
        yAxis.setLabel("Value");

        Scene scene  = new Scene(bc,sceneWidth,sceneHeight);
        bc.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }

}