import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    public static void executeUpdate(String query, Connection con) {
        try {

            con.createStatement().executeUpdate(query);

            /*
            The initial tab that opens up when the program is started uses an interface (which is in main.fxml) identical to the one in tab.fxml,
            but provides a way to dynamically resize the window so that the contents resize as well and everything looks good. Unfortunately, I
            didn't find a way to retain that functionality with the other tabs, where the content of tab.fxml is used.
            */
            if (mc.isMainTab()) mc.setMsg("The query was executed successfully!");
            else tc.setMsg("The query was executed successfully!");

        } catch (SQLException e) {

            if (mc.isMainTab()) mc.setMsg(e.getMessage());
            else tc.setMsg(e.getMessage());

        }
    }


    public static void executeQuery(String query, Connection con) throws SQLException {

        TableView<ObservableList> sqlTable;
        if (mc.isMainTab()) sqlTable = mc.getSQLTable();
        else sqlTable = tc.getSQLTable();

        ResultSet rs = null;

        try {
            rs = con.createStatement().executeQuery(query);

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            if (mc.isMainTab()) mc.clearTable();
            else tc.clearTable();

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

            if (mc.isMainTab()) mc.setMsg("The query was executed successfully!");
            else tc.setMsg("The query was executed successfully!");

        } catch (SQLException e) {

            if (mc.isMainTab()) mc.setMsg(e.getMessage());
            else tc.setMsg(e.getMessage());

        } finally {
            if (rs != null) rs.close();
        }

    }
}
