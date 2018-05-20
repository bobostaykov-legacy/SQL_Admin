import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class NewConnectionController {

    @FXML private TextField con_name = new TextField();
    @FXML private TextField host_name = new TextField();
    @FXML private TextField port = new TextField();
    @FXML private TextField DB_name = new TextField();
    @FXML private TextField username = new TextField();
    @FXML private TextField password = new TextField();
    @FXML private Button okBtnNewCon = new Button();

    public void newConnection(){
        int port_num = parseInt(port.getText());
        //Database.createConnection(host_name.getText(), port_num, DB_name.getText(), username.getText(), password.getText());
        Stage stage = (Stage) okBtnNewCon.getScene().getWindow();
        stage.close();
    }
}
