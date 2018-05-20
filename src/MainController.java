import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private static TextField query_field = new TextField();
    @FXML private static TabPane tab_pane = new TabPane();

    public void runAction(){
        Database.runQuery(query_field.getText());
    }

    public void addNewTab(){
        Tab newTab = new Tab("newTab");
        tab_pane.getTabs().add(newTab);
        tab_pane.getSelectionModel().select(newTab);
    }

    public void openNewConWindow(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("newConnection.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Create a new Connection");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
