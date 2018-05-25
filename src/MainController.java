import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TabPane tab_pane;
    @FXML
    private GridPane tab_1, query_view_1, connections_view_1;
    @FXML
    private TextField query_field_1;
    @FXML
    private TableView<ObservableList> sql_table_1;
    @FXML
    private Label msg_1;
    @FXML
    private Tab main_tab;
    @FXML
    private Button exe_btn_1;


    //to store the active connections
    private LinkedList<ConnectionPlusName> connections = new LinkedList<>();

    //to be able to call methods from TabController class
    private static TabController tc = new TabController();
    public static void injectTabController(TabController tabCont){
        tc = tabCont;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionController.injectMainController(this);
        MainClass.injectMainController(this);
        TabController.injectMainController(this);
        Database.injectMainController(this);
        exe_btn_1.setTooltip(new Tooltip("Or press Enter"));
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
        connections_view_1.setVisible(false);
        query_view_1.setVisible(true);
    }


    //creating a new tab and switching to it after pressing the button "New"
    public void addNewTab() throws IOException{
        Tab newTab = new Tab("New Connection");
        tab_pane.getTabs().add(tab_pane.getTabs().size() - 1, newTab);
        tab_pane.getSelectionModel().select(newTab);

        Parent root = FXMLLoader.load(getClass().getResource("tab.fxml"));
        newTab.setContent(root);

        tc.setPrefSizeTab(tab_1.getWidth(), tab_1.getHeight());
    }


    //giving the current tab a name
    public void setTab(String name){
        Tab currentTab = tab_pane.getSelectionModel().getSelectedItem();
        currentTab.setText(name);
    }


    //adding a connection to the LinkedList
    public void addCon(String name, Connection con){
        connections.add(new ConnectionPlusName(name, con));
    }


    //getting lastly added connection
    public String getCon(){
        if (connections.size() == 0) return "null";
        return connections.getLast().getName();
    }


    public LinkedList<ConnectionPlusName> allConnections(){
        return connections;
    }


    public void tabPaneRequestFocus(){
        tab_pane.requestFocus();
    }


    public boolean isMainTab () {
        return tab_pane.getSelectionModel().getSelectedItem() == main_tab;
    }


    public String getCurrentTabName(){
        return tab_pane.getSelectionModel().getSelectedItem().getText();
    }


    //checks if a connection is already active
    public boolean isConNameInList(String name){
        for (ConnectionPlusName current : connections){
            if (current.getName().equals(name)) return true;
        }
        return false;
    }


    //after pressing the button "Execute" the query is being sent to the database and executed
    public void executeAction() throws SQLException {

        String query = query_field_1.getText();
        Connection con = null;
        String firstWord = query.split(" ")[0];

        for (ConnectionPlusName current : connections) {
            if (current.getName().equals(getCurrentTabName())) con = current.getConnection();
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


    public TableView<ObservableList> getSQLTable(){
        return sql_table_1;
    }


    //the message to be displayed to the user
    public void setMsg (String m) {
        msg_1.setText(m);
    }


    public void clearTable () {
        sql_table_1.getColumns().clear();
    }


    public String connectionsAsString() {
       StringBuilder sb = new StringBuilder();
       for (ConnectionPlusName curr : connections) {
           sb.append(curr.getName());
           sb.append(",");
           sb.append(curr.getConnection());
           sb.append(";");
       }
       return sb.toString();
    }

}
