import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TabPane tab_pane;
    @FXML
    private AnchorPane anch_pane;

    private LinkedList<ConnectionPlusName> connections = new LinkedList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionController.injectMainController(this);
        MainClass.injectMainController(this);

        try {
            tab_pane.getSelectionModel().getSelectedItem().setContent(FXMLLoader.load(getClass().getResource("tab.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNewTab() throws IOException{
        Tab newTab = new Tab("New Connection");
        tab_pane.getTabs().add(tab_pane.getTabs().size() - 1, newTab);
        tab_pane.getSelectionModel().select(newTab);

        Parent root = FXMLLoader.load(getClass().getResource("tab.fxml"));
        newTab.setContent(root);
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


    public void tabPaneRequestFocus(){
        tab_pane.requestFocus();
    }


    public void setPrefSizeTabPane(){
        tab_pane.setPrefWidth(tab_pane.getScene().getWidth());
        tab_pane.setPrefHeight(tab_pane.getScene().getHeight());
    }

}
