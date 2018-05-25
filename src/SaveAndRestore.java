import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class SaveAndRestore {

    MainController mc = new MainController();
    Connection con =  Database.createConnection("den1.mysql4.gear.host", 3306, "borisdatabase", "borisdatabase", "Ly3F!N20-16Z");

    public void saveConnections() {
        Statement stmt = null;
        String IP = getIP();

        try {
            stmt = con.createStatement();
            if (ipInDB()) {
                stmt.executeUpdate("update mymysqlwb set connections = '" + mc.connectionsAsString() + "' where ip = " + IP);
            } else {
                stmt.executeUpdate("insert into mymysqlwb values (" + IP + ",'')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    private boolean ipInDB() {
        String IP = getIP();
        Statement stmt = null;
        boolean res = false;
        ResultSet rs = null;
        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery("select ip from mymysqlwb");
            while (rs.next()) {
                String ips = rs.getString("ip");
                if (ips.equals(IP)) {
                    res = true;
                    break;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }


    private static String getIP() {
        InetAddress IP;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        return IP.toString();
    }

}
