package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import sample.entities.Filter;
import sample.views.FilterListView;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Filter> filters = FXCollections.observableArrayList();

        FilterListView listView = new FilterListView(primaryStage, filters);
        primaryStage.setScene(listView.getScene());

        primaryStage.setTitle("Packet Analyzer Filter");
        primaryStage.show();
    }
}