package sample.views;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import sample.*;
import sample.entities.Filter;
import sample.validators.FilterValidator;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FilterFormView extends BaseView {

    private static final String[] KEYWORDS = new String[] {
            "abstract", "arguments", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "volatile", "while",
            "function", "true", "false"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "function filter(packet) {",
            "    /*",
            "     * multi-line comment",
            "     */",
            "     if(packet.getProtocol().getName() === 'tcp') {",
            "          return true;",
            "     }",
            "",
            "     return false;",
            "}"
    });

    private TextField nameInput;
    private CodeArea codeArea;

    private Button saveButton;
    private Button cancelButton;

    private BorderPane layoutPane;

    private int sceneWidth;
    private int sceneHeight;

    private Stage stage;
    private Scene parentScene;

    private ObservableList<Filter> filters;

    private int instanceIndex;

    public FilterFormView(Stage stage, Scene parentScene, ObservableList<Filter> filters) {
        sceneWidth = 620;
        sceneHeight = 650;

        this.stage = stage;
        this.parentScene = parentScene;
        this.filters = filters;

        this.instanceIndex = -1;

        this.buildScene();

        codeArea.replaceText(0, 0, sampleCode);
    }

    public FilterFormView(Stage stage, Scene parentScene, ObservableList<Filter> filters, Filter instance, int index) {
        sceneWidth = 620;
        sceneHeight = 650;

        this.stage = stage;
        this.parentScene = parentScene;
        this.filters = filters;

        this.instanceIndex = index;

        this.buildScene();

        this.initializeFields(instance);
    }

    private void initializeFields(Filter filter) {
        nameInput.setText(filter.getName());
        codeArea.insertText(0, filter.getCode());
    }

    private void buildScene() {
        initializeNameInput();
        initializeCodeArea();
        initializeButtons();
        initializePanes();

        scene = new Scene(layoutPane, sceneWidth, sceneHeight);
        scene.getStylesheets().add(Main.class.getResource("java-keywords.css").toExternalForm());
    }

    private void initializeCodeArea() {
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });
    }

    private void initializeNameInput() {
        nameInput = new TextField();
        nameInput.setPrefWidth(600);
        nameInput.setPromptText("Filter name");
    }

    private void initializeButtons() {
        saveButton = new Button();
        saveButton.setText("Zapisz");
        saveButton.setOnAction(event -> {

            Filter filter = new Filter(nameInput.getText(), codeArea.getText());
            FilterValidator validator = new FilterValidator(filter, new FilteringContext(filters));


            if(validator.validate()) {
                if(this.instanceIndex >= 0) {
                    this.filters.remove(this.instanceIndex);
                    this.filters.add(this.instanceIndex, filter);
                } else {
                    this.filters.add(filter);
                }

                try {
                    FilterSaver.save(filter);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                stage.setScene(parentScene);


            } else {
                for(String error: validator.getErrors()) {
                    System.out.println(error);
                }
            }
        });

        cancelButton = new Button();
        cancelButton.setText("Anuluj");
        cancelButton.setOnAction(event -> stage.setScene(parentScene));

        // walidacja pod katem istnienia odpowiedniego API w JS (funkcja, odpowiednia nazwa)

        // zapisywanie do pliku

        // ladowanie z dysku istniejacych filtrow

        // dodanie metod do filtrowania bufora
    }

    private void initializePanes() {
        HBox inputBox = new HBox();
        inputBox.getChildren().addAll(nameInput);

        StackPane topPane = new StackPane();
        topPane.getChildren().add(inputBox);
        topPane.setPadding(new Insets(10, 10, 10, 10));

        StackPane centerPane = new StackPane(new VirtualizedScrollPane<>(codeArea));

        HBox hb = new HBox();
        hb.getChildren().addAll(saveButton, cancelButton);

        TilePane bottomPane = new TilePane();
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setPrefColumns(2);
        bottomPane.getChildren().add(hb);

        layoutPane = new BorderPane();
        layoutPane.setTop(topPane);
        layoutPane.setCenter(centerPane);
        layoutPane.setBottom(bottomPane);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
