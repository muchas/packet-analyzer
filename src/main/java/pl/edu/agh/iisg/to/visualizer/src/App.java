package main.java.pl.edu.agh.iisg.to.visualizer.src;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Suota on 2016-12-13.
 */
public class App extends Application {

    private Tab getCurrentTab() {
        return ((Tab)(((TabPane)((BorderPane)root.getChildren().get(0)).getChildren().get(0)).getSelectionModel().getSelectedItem()));
    }

    private String [] tabNames = {"Start", "Realtime Visualization", "Statistics", "Help" };
    Button realtimeButton;
    Button openStatsButton;
    Button userGuideButton;
    ChoiceBox statisticsChoiseBox;
    File userGuideFile;
    Group root;
    Button stopButton;

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
    public void init(){
        realtimeButton = new Button("RealTime Chart");
        openStatsButton = new Button ("View statistics");
        userGuideButton = new Button("User Guide");
        statisticsChoiseBox = new ChoiceBox(FXCollections.observableArrayList("Medium size","Protocols","Destination ports"));
        userGuideFile = new File("userGuide.txt");
        stopButton = new Button("STOP");

    }
    @Override
    public void start(Stage primaryStage) {
        init();
        primaryStage.setTitle("Packet Analyzer");
        root = new Group();
        Scene scene = new Scene(root, 500, 300, Color.DARKGREY);
        initComponents(primaryStage);

        // Start Tab content
        BorderPane startPane = new BorderPane();
        Label startLabel = new Label("Welcome to Packet Analyzer Tool");
        startLabel.setFont(new Font("Arial", 30));
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
                                   @Override
                                   public void handle(ActionEvent event) {
                                       // TODO call collector's stop method
                                   }
                               });
        startPane.setTop(startLabel);
        startPane.setCenter(stopButton);

        // Stats Tab content
        Label label = new Label("Select statistics");
        label.setFont(new Font("Arial", 30));

        BorderPane statsPane= new BorderPane();
        statsPane.setTop(label);
        statsPane.setCenter(statisticsChoiseBox);
        statsPane.setBottom(openStatsButton);


        // Assign tabs content
        Node[] tabContent = {startPane, realtimeButton, statsPane, userGuideButton};

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

                //Creating TextArea with user guide text
                TextArea userGuideArea = new TextArea(userGuideText);
                userGuideArea.setFont(new Font("Arial", 14));
                userGuideArea.setEditable(false);

                //Getting User Guide Tab and Hbox that exists in this Tab
                Tab currentTab = getCurrentTab();
                HBox currentBox = (HBox)currentTab.getContent();

                //Replacing Button which display help into TextArea with help
                Button removedButton = (Button)currentBox.getChildren().remove(0);
                currentBox.getChildren().add(userGuideArea);

                //When another tab is selected TextArea is removed and button is added again
                currentTab.setOnSelectionChanged(eventOnExit -> {
                    currentBox.getChildren().remove(0);
                    currentBox.getChildren().add(removedButton);
                });
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

                if (statisticsChoiseBox.getSelectionModel().getSelectedIndex()>0 ){
                    StatisticChart pieChartSample = new PieChartSample();
                    pieChartSample.start(statisticsChartStage);
                    statisticsChartStage.show();
                }
                else{
                    //TODO show medium packet size 
                }


            }
        });
    }

    private void initStatisticsChoiseBox () {
        statisticsChoiseBox.setTooltip(new Tooltip("Select preferable statistics characteristic"));
    }

}
