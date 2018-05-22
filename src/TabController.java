import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabController implements Initializable {

    @FXML
    private TextField query_field;
    @FXML
    private GridPane query_view, connections_view;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionController.injectTabController(this);
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


    public void switchToQueryView(){
        connections_view.setVisible(false);
        query_view.setVisible(true);
        //tabPaneRequestFocus();
    }


    public void switchToConnectionsView(){
        query_view.setVisible(false);
        connections_view.setVisible(true);
        //tabPaneRequestFocus();
    }


    public void runAction(){
        Database.runQuery(query_field.getText(), null);
    }
}
