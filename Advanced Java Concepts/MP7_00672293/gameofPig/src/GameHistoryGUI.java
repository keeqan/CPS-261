import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameHistoryGUI { // extends Application {
    private TableView<GameRecord> table;

    public void start(Stage stage) {
        table = new TableView<>();
        setupTable();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void setupTable() {
    
    }
}