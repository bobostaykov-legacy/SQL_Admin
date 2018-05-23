import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.*;

public class Database {

    private static MainClass main = new MainClass();
    private static ObservableList<Object> data;
    @FXML
    private static TableView<Object> sqlTable;
    @FXML
    private static TableColumn<Object, String> col;


    //creating connection to the database
    public static Connection createConnection(String hostname, int port, String DBname, String username, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + DBname + "?autoReconnect=true&useSSL=false";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
    }


    public static void runQuery(String query, Connection con){
        try {
            ResultSet rs = con.createStatement().executeQuery(query);

            for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                //col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                sqlTable.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++) row.add(rs.getString(i));
                System.out.println("Row [1] added "+row );
                data.add(row);
            }

            sqlTable.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
