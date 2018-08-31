import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.*;

public class Database {

    private static MainController mc = new MainController();
    private static TabController tc = new TabController();
    public static void injectMainController (MainController mainCont){
        mc = mainCont;
    }
    public static void injectTabController (TabController tabCont) {
        tc = tabCont;
    }


    //creating connection to the database
    public static Connection createConnection(String hostname, String port, String DBname, String username, String password, String DBtype){
        String url;
        try {
            if (DBtype.equals("MySQL")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                url = "jdbc:mysql://" + hostname + ":" + port + "/" + DBname + "?unicode=true&autoReconnect=true&useSSL=false";
            } else /* if (DBtype.equals("PostgreSQL") */ {
                Class.forName("org.postgresql.Driver");
                url = "jdbc:postgresql://" + hostname + ":" + port + "/" + DBname + "?unicode=true&autoReconnect=true&useSSL=false";
            }
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }


    public static boolean executeUpdate(String query, Connection con, TableView<ObservableList> sqlTable, Label msg) {
        boolean result;

        try {
            con.createStatement().executeUpdate(query);
            msg.setText("The query was executed successfully");
            sqlTable.getColumns().clear();
            result = true;
        } catch (SQLException e) {
            msg.setText(e.getMessage());
            result = false;
        }

        return result;
    }


    public static boolean executeQuery(String query, Connection con, TableView<ObservableList> sqlTable, Label msg) {

        boolean result;

        try (ResultSet rs = con.createStatement().executeQuery(query)) {

            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            sqlTable.getColumns().clear();

            // --- getting all items from the database table to fill the table in query_view ---

            //getting column names
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn<ObservableList, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                sqlTable.getColumns().add(col);
            }

            while (rs.next()) {
                //iterate row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //iterate column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            sqlTable.setItems(data);
            msg.setText("The query was executed successfully");
            result = true;

        } catch (SQLException e) {
            msg.setText(e.getMessage().replace("\n", ""));
            result = false;
        }
        return result;
    }
}
