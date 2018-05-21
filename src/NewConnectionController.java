import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

import static java.lang.Integer.parseInt;

public class NewConnectionController {

    private static MainController mc;

    @FXML
    private TextField con_name, host_name, port, DB_name, username, password;
    @FXML
    private Button okBtnNewCon, cancelBtnNewCon;


    public static void injectMainController(MainController mainCont){
        mc = mainCont;
    }

    public void newConnection(){String conName = con_name.getText();

        int port_num = parseInt(port.getText());

        Connection connection = Database.createConnection(host_name.getText(), port_num, DB_name.getText(), username.getText(), password.getText());
        if (connection == null) {
            //errorDialog();
            return;
        }

        mc.addCon(conName, connection);
        mc.setTab();
        mc.switchToQueryView();

        Stage stage = (Stage) okBtnNewCon.getScene().getWindow();
        stage.close();
    }

    public void cancel(){
        Stage stage = (Stage) cancelBtnNewCon.getScene().getWindow();
        stage.close();
    }

}
