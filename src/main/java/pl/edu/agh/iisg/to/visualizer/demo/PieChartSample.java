package main.java.pl.edu.agh.iisg.to.visualizer.demo; /**
 * Created by Cinek on 2016-11-29.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

public class PieChartSample extends Application {

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Statistics");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("TCP", 432),
                        new PieChart.Data("UDP", 256));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Transport Layer Protocols Usage");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
}