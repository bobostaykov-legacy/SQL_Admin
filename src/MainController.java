import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField query_field;
    @FXML
    private TabPane tab_pane;
    @FXML
    private AnchorPane query_view, connections_view;

    private LinkedList<ConnectionPlusName> connections = new LinkedList<>();
    //NewConnectionController ncc = new NewConnectionController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionController.injectMainController(this);
    }

    public void runAction(){
        Database.runQuery(query_field.getText(), null);
    }

    public void addNewTab(){
        Tab newTab = new Tab("New Connection");
        tab_pane.getTabs().add(tab_pane.getTabs().size() - 1, newTab);
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

    public void switchToQueryView(){
        connections_view.setVisible(false);
        query_view.setVisible(true);
    }

    public void switchToConnectionsView(){
        query_view.setVisible(false);
        connections_view.setVisible(true);
    }

    public void setTab(){
        Tab currentTab = tab_pane.getSelectionModel().getSelectedItem();
        currentTab.setText(getCon());
    }

    public void addCon(String name, Connection con){
        connections.add(new ConnectionPlusName(name, con));
    }

    public String getCon(){
        if (connections.size() == 0) return "null";
        return connections.getLast().getName();
    }

    public void displayConnections(){
        System.out.println(connections);
    }

}
