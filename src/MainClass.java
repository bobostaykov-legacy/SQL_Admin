import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClass extends Application {

    private static MainController mc;
    private Parent root;

    static void injectMainController(MainController mainCont){
        mc = mainCont;
    }

    @Override
    public void init() throws Exception {
        root = FXMLLoader.load(getClass().getResource("main.fxml"));
    }

    @Override
    public void start(Stage primaryStage) {

        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        int sceneWidth;
        int sceneHeight;

        //dynamically set window size, depending on the screen size
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 550;
            sceneHeight = 378;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1000;
            sceneHeight = 670;
        } else {
            sceneWidth = 3300;
            sceneHeight = 2550;
        }

        primaryStage.setTitle("Bobo's SQL Admin");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
        mc.tabPaneRequestFocus();

    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainClass.class, AppPreloader.class, args);
    }
}
