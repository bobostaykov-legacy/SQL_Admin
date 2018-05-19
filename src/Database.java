import java.sql.*;

public class Database {

    private static Main main = new Main();

    public static Connection createConnection(String hostname, int port, String name, String username, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + name + "?autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected to database!");
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void runQuery(String query){
        Statement stmt;
        Connection con = main.getConnection();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
