package main.java.pl.edu.agh.iisg.to.visualizer.demo;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Suota on 2016-12-13.
 */
public class App extends Application {

    private String [] tabNames = {"Start", "Realtime Visualization", "Statistics", "Help" };
    Button realtimeButton = new Button("RealTime Chart");
    Button openStatsButton = new Button ("View statistics");
    Button userGuideButton = new Button("User Guide");
    ChoiceBox statisticsChoiseBox = new ChoiceBox(FXCollections.observableArrayList("Size","Protocols","Ports"));
    File userGuideFile = new File("userGuide.txt");

    public static void main(String[] args) {
        Application.launch(args);
    }


    private void initComponents(Stage primaryStage) {
        initRealTimeButton();
        initOpenStatsButton();
        initUserGuideButton();
        initStatisticsChoiseBox();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Packet Analyzer");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 300, Color.DARKGREY);
        initComponents(primaryStage);

        // Start Tab content
        Label startLabel = new Label("Welcome to Packet Analyzer Tool");
        startLabel.setFont(new Font("Arial", 30));

        // Stats Tab content
        Label label = new Label("Select statistics");
        label.setFont(new Font("Arial", 30));

        String[] charts = {"Size stats","Protocols stats","Ports stats"};
        BorderPane statsPane= new BorderPane();
        statsPane.setTop(label);
        statsPane.setCenter(statisticsChoiseBox);


        // Assign tabs content
        Node[] tabContent = {startLabel, realtimeButton, statsPane, userGuideButton};

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Adding tabs to tabPane
        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 4; i++) {
            Tab tab = new Tab();
            tab.setText(tabNames[i]);
            HBox hbox = new HBox();
            hbox.getChildren().add(tabContent[i]);
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
    }


    private void initRealTimeButton() {
        realtimeButton.setFont(new Font("Arial", 20));
        realtimeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label liveLineChartLabel = new Label("LiveLineChart");
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(liveLineChartLabel);

                Scene liveLineChartScene = new Scene(secondaryLayout, 1000, 500);
                Stage liveLineChartStage = new Stage();
                liveLineChartStage.setTitle("New Stage");
                liveLineChartStage.setScene(liveLineChartScene);

                LiveLineChart liveLineChart = new LiveLineChart();
                liveLineChart.start(liveLineChartStage);
                liveLineChartStage.show();
            }
        });
    }

    private void initUserGuideButton() {
        userGuideButton.setFont(new Font("Arial", 20));
        userGuideButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userGuideText = "";
                try(BufferedReader br = new BufferedReader(new FileReader(userGuideFile))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    userGuideText = sb.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Label userGuideLabel = new Label(userGuideText);
            //    Label userGuideLabel = new Label("Tutaj bedzie help");
                userGuideLabel.setFont(new Font("Arial", 14));
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(userGuideLabel);

                Scene userGuideScene = new Scene(secondaryLayout, 500, 500);
                Stage userGuideStage = new Stage();
                userGuideStage.setTitle("New Stage");
                userGuideStage.setScene(userGuideScene);

                userGuideStage.show();
            }
        });
    }
    private void initOpenStatsButton () {
        openStatsButton.setFont(new Font("Arial", 20));
        openStatsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label statisticsChartLabel = new Label("Statistics Chart");
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(statisticsChartLabel);

                Scene statisticsChartScene = new Scene(secondaryLayout, 1000, 500);
                Stage statisticsChartStage = new Stage();
                statisticsChartStage.setTitle("New Stage");
                statisticsChartStage.setScene(statisticsChartScene);

                StatisticChart pieChartSample = new PieChartSample();
                pieChartSample.start(statisticsChartStage);
                statisticsChartStage.show();
            }
        });
    }

    private void initStatisticsChoiseBox () {
        statisticsChoiseBox.setTooltip(new Tooltip("Select preferable statistics characteristic"));
    }
}
