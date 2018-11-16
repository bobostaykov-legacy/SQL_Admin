import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppPreloader extends Preloader {

    private Stage stage;

    public void start(Stage stage) {
        this.stage = stage;

        VBox loading = new VBox(20);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);
        loading.setPadding(new Insets(30,0,0,0));
        ProgressBar progress = new ProgressBar();
        progress.setPrefWidth(170);
        loading.getChildren().add(progress);
        Label text = new Label("Starting SQL Admin...");
        text.setPrefWidth(170);
        text.setAlignment(Pos.CENTER);
        loading.getChildren().add(text);

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(300);
        stage.setHeight(200);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

}
