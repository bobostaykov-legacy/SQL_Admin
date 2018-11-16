import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class NewConnectionController implements Initializable {

    @FXML
    private TextField con_name, host_name, port, DB_name, username, password;
    @FXML
    private Button okBtnNewCon, cancelBtnNewCon;
    @FXML
    private ChoiceBox<String> dropdown;

    private SaveAndRestore sar = new SaveAndRestore();

    //to be able to call methods from MainController and TabController classes
    private static MainController mc;
    private static TabController tc;
    static void injectMainController(MainController mainCont){
        mc = mainCont;
    }
    static void injectTabController(TabController tabCont){
        tc = tabCont;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropdown.setItems(FXCollections.observableArrayList("MySQL", "PostgreSQL"));
    }


    //using the input by the user info to connect to their database
    public void newConnection() {
        String conName = con_name.getText();

        Connection connection = Database.createConnection( host_name.getText(), port.getText(), DB_name.getText(), username.getText(), password.getText(), dropdown.getValue() );

        //if there was a problem while connecting to the database
        if (connection == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There was a problem connecting to the database...");
            alert.showAndWait();
            return;
        }

        //if a connection with the same name already exists
        if (mc.isConNameInList(conName)){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection name already exists");
            alert.setHeaderText(null);
            alert.setContentText("A connection with the same name already exists!");
            alert.showAndWait();
            return;
        }

        //if a connection to that database is already active
        for (ConnectionInfo curr : mc.allConnections()) {
            if (curr.getDatabaseName().equals(DB_name.getText()) && curr.getHostName().equals(host_name.getText())) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Connection already exists");
                alert.setHeaderText(null);
                alert.setContentText("A connection to that server/database already exists!");
                alert.showAndWait();
                return;
            }
        }

        //saving the information so that the user does not have to input it again next time they use the program
        sar.saveToDB( conName, host_name.getText(), port.getText(), DB_name.getText(), username.getText(), /*EncryptDecrypt.encrypt(password.getText(), Const.KEY)*/ password.getText(), dropdown.getValue());

        mc.addCon(conName, host_name.getText(), port.getText(), DB_name.getText(), username.getText(), password.getText(), connection);
        mc.setTab(mc.getCon());

        /*
        The initial tab that opens up when the program is started uses an interface (which is in main.fxml) identical to the one in tab.fxml,
        but provides a way to dynamically resize the window so that the contents resize as well and everything looks good. Unfortunately, I
        didn't find a way to retain that functionality with the other tabs, where the content of tab.fxml is used.
        */
        if (mc.isMainTab()) mc.switchToQueryView();
        else tc.switchToQueryView();

        //closing the new connection window
        Stage stage = (Stage) okBtnNewCon.getScene().getWindow();
        stage.close();
    }


    //when the "cancel" button is pressed
    public void cancel(){
        Stage stage = (Stage) cancelBtnNewCon.getScene().getWindow();
        stage.close();
    }

}
