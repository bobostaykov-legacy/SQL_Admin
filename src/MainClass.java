import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClass extends Application {

    private static MainController mc;

    public static void injectMainController(MainController mainCont){
        mc = mainCont;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        int sceneWidth = 0;
        int sceneHeight = 0;

        //dynamically set window size, depending on the screen size
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 550;
            sceneHeight = 378;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 800;
            sceneHeight = 550;
        } else {
            sceneWidth = 1850;
            sceneHeight = 1272;
        }

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Bobo's MySQL Workbench");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
        mc.tabPaneRequestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
