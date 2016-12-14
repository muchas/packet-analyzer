package main.java.pl.edu.agh.iisg.to.visualizer.demo; /**
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
    final static String class1 = "Class A";
    final static String class2 = "Class B";
    final static String class3 = "Class C";

    @Override public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        int sceneWidth = 800;
        int sceneHeight = 600;
        bc.setCategoryGap(sceneWidth/6);
        bc.setTitle("IP Class Segregation");
        xAxis.setLabel("Class");
        yAxis.setLabel("Value");

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(class1, 764));
        series.getData().add(new XYChart.Data(class2, 855));
        series.getData().add(new XYChart.Data(class3, 345));

        Scene scene  = new Scene(bc,sceneWidth,sceneHeight);
        bc.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }

}