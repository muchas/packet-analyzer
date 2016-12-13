package main.java.pl.edu.agh.iisg.to.visualizer.demo;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by Suota on 2016-12-13.
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    private String[] tabNames = {"Start", "Realtime Visualization", "Statistics", "Help" };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Packet Analyzer");
        Group root = new Group();
        Scene scene = new Scene(root, 1500, 900, Color.WHITE);

        // Specifying tabs content

        // Start Tab content
        Label startLabel = new Label("Welcome to Packet Analyzer Tool");
        startLabel.setFont(new Font("Arial", 30));

        // RealTime Tab content
        Button realtime = new Button("RealTime Chart");
        realtime.setFont(new Font("Arial", 20));
        realtime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //TODO run LiveLineChart content here
            }
        });

        // Stats Tab content
        ChoiceBox stats = new ChoiceBox(FXCollections.observableArrayList("Size","Protocols","Ports"));
        stats.setTooltip(new Tooltip("Select preferable statistics characteristic"));

        Label label = new Label("Select statistics");
        label.setFont(new Font("Arial", 30));

        String[] charts = {"Size stats","Protocols stats","Ports stats"};


        stats.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number oldValue, Number newValue) {
                        label.setText(charts[newValue.intValue()]);

                    }
                }
        );
        BorderPane statsPane= new BorderPane();
        statsPane.setTop(label);
        statsPane.setCenter(stats);

        // Help Tab content
        Button userGuide = new Button("User Guide");
        userGuide.setFont(new Font("Arial", 20));
        userGuide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //TODO open user guide content here
            }
        });

        // Assign tabs content
        Node[] tabContent = {startLabel, realtime, statsPane, userGuide};

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
        primaryStage.show();
    }

}
