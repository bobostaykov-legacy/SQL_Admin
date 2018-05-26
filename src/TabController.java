import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TabController implements Initializable {

    @FXML
    private TextArea query_field;
    @FXML
    private GridPane tab, query_view, connections_view;
    @FXML
    private TableView<ObservableList> sql_table;
    @FXML
    private Label msg;
    @FXML
    private Button exe_btn;

    private SaveAndRestore sar = new SaveAndRestore();

    //to be able to call methods from MainController class
    private static MainController mc = new MainController();
    public static void injectMainController(MainController mainCont){
        mc = mainCont;
    }


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        NewConnectionController.injectTabController(this);
        MainController.injectTabController(this);
        Database.injectTabController(this);

        addConButtons();

        exe_btn.setTooltip(new Tooltip("Or use [Ctrl+Enter]"));

        //the query can also be executed with [Ctrl+Enter]
        query_field.setOnKeyPressed(e ->  {
            if ((new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN)).match(e)) {
                try {
                    executeAction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
    public void executeAction() throws SQLException {

        String query = query_field.getText();
        Connection con = null;
        String firstWord = query.split(" ")[0];

        for (ConnectionInfo current : mc.allConnections()) {
            if (current.getConName().equals(mc.getCurrentTabName())) con = current.getConnection();
        }

        boolean insert = firstWord.equalsIgnoreCase("insert");
        boolean update = firstWord.equalsIgnoreCase("update");
        boolean delete = firstWord.equalsIgnoreCase("delete");
        boolean create = firstWord.equalsIgnoreCase("create");
        boolean drop = firstWord.equalsIgnoreCase("drop");
        boolean use = firstWord.equalsIgnoreCase("use");
        boolean alter = firstWord.equalsIgnoreCase("alter");

        if (insert || update || delete || create || drop || use || alter) {
            Database.executeUpdate(query, con);
        } else {
            Database.executeQuery(query, con);
        }
    }


    public void setPrefSizeTab(double width, double height){
        tab.setPrefWidth(width);
        tab.setPrefHeight(height);
    }


    public TableView<ObservableList> getSQLTable(){
        return sql_table;
    }


    //the message to be displayed to the user
    public void setMsg (String m) {
        msg.setText(m);
    }


    public void clearTable () {
        sql_table.getColumns().clear();
    }


    public void closeConnection() throws SQLException{
        String conName = mc.getCurrentTabName();
        for (ConnectionInfo curr : mc.allConnections()) {
            if (curr.getConName().equals(conName)) {
                curr.getConnection().close();
                mc.allConnections().remove(curr);
                sar.removeFromDB(curr.getConName());
                break;
            }
        }
        removeConButtons();
        mc.removeButton(conName);
        addConButtons();
        mc.setTab("New Connection");
        mc.switchToConnectionsView();
    }


    //adding the active connections to the connections_view as buttons
    private void addConButtons() {
        int i = 1, j = 0;
        for (ConnectionInfo current : mc.allConnections()) {
            Button conButton = new Button(current.getConName());
            if (!mc.buttonInList(conButton)) mc.allButtons().add(new Buttons(current.getConName(), conButton));
            conButton.setMaxWidth(125);
            conButton.setPrefHeight(62);
            conButton.setBlendMode(BlendMode.MULTIPLY);
            conButton.setOnAction(e -> {
                switchToQueryView();
                mc.setTab(current.getConName());
            });
            connections_view.add(conButton, i, j);
            i++;
            if (i == 4) {
                i = 0;
                j++;
            }
        }
    }


    private void removeConButtons() {
        for (Buttons curr : mc.allButtons()) {
            connections_view.getChildren().remove(curr.getButton());
        }
    }
}
