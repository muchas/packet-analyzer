package pl.edu.agh.iisg.to.filter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to.filter.entities.Filter;
import pl.edu.agh.iisg.to.filter.views.FilterListView;

import java.util.List;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Filter> filters = FXCollections.observableArrayList();
        FilterStorage storage = new FilterFileStorage();
        List<Filter> existingFilters = storage.loadAll();
        filters.addAll(existingFilters);

        FilterListView listView = new FilterListView(primaryStage, filters);
        primaryStage.setScene(listView.getScene());

        primaryStage.setTitle("Packet Analyzer Filter");
        primaryStage.show();
    }
}