import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private GridPane tab, query_view, connections_view;

    //to be able to call methods from MainController class
    private static MainController mc = new MainController();
    public static void injectMainController(MainController mainCont){
        mc = mainCont;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionController.injectTabController(this);
        MainController.injectTabController(this);

        //adding the active connections to the connections_view as buttons
        int i = 1, j = 0;
        for (ConnectionPlusName current : mc.allConnections()){
            Button conButton = new Button(current.getName());
            conButton.setMaxWidth(125);
            conButton.setPrefHeight(62);
            conButton.setOnAction(e -> {
                switchToQueryView();
                mc.setTab(current.getName());
            });
            connections_view.add(conButton, i, j);
            i++;
            if (i == 4) {
                i = 0;
                j++;
            }
        }
    }


    //the window in which needed information about the database is input by the user
    public void openNewConWindow(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("newConnection.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Create a new Connection");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchToQueryView(){
        connections_view.setVisible(false);
        query_view.setVisible(true);
    }


    //after pressing the button "Execute" the query is being sent to the database and executed
    public void runAction(){
        Database.runQuery(query_field.getText(), null);
    }


    public void setPrefSizeTab(double width, double height){
        tab.setPrefWidth(width);
        tab.setPrefHeight(height);
    }
}
