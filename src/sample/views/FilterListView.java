package sample.views;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import sample.entities.Filter;


public class FilterListView extends BaseView {

    private int sceneWidth;
    private int sceneHeight;

    private ListView listView;

    private BorderPane layoutPane;

    private Button createButton;
    private Button editButton;
    private Button deleteButton;

    private ObservableList<Filter> filters;

    private Stage stage;

    public FilterListView(Stage stage, ObservableList<Filter> filters) {
        this.filters = filters;
        this.sceneWidth = 620;
        this.sceneHeight = 650;
        this.stage = stage;

        this.buildScene();
    }

    private void buildScene() {
        this.initializeListView();
        this.initializeButtons();
        this.initializePanes();

        scene = new Scene(layoutPane, sceneWidth, sceneHeight);
    }

    private void initializeListView() {
        listView = new ListView<Filter>(filters);
        listView.setPrefSize(550, 400);
    }

    private void initializeButtons() {
        createButton = new Button();
        createButton.setText("Dodaj nowy");
        createButton.setOnAction(event -> {
            FilterFormView addView = new FilterFormView(stage, scene, filters);
            stage.setScene(addView.getScene());
        });

        editButton = new Button();
        editButton.setText("Edytuj");
        editButton.setOnAction(event -> {
            Filter selectedFilter = (Filter) listView.getSelectionModel().getSelectedItem();
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();

            if(selectedIndex < 0) {
                System.out.println("No filters selected");
                return;
            }

            FilterFormView editView = new FilterFormView(stage, scene, filters, selectedFilter, selectedIndex);

            stage.setScene(editView.getScene());
        });

        deleteButton = new Button();
        deleteButton.setText("Usun");
        deleteButton.setOnAction(event -> System.out.println("Hello World 3!"));
    }

    private void initializePanes() {
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(listView);

        HBox hb = new HBox();
        hb.getChildren().addAll(createButton, editButton, deleteButton);

        TilePane bottomPane = new TilePane();
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setPrefColumns(3);
        bottomPane.getChildren().add(hb);

        layoutPane = new BorderPane();
        layoutPane.setCenter(centerPane);
        layoutPane.setBottom(bottomPane);
    }
}