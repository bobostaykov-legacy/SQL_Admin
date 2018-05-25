import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class SaveAndRestore {

    private Connection con =  Database.createConnection("den1.mysql4.gear.host", "3306", "borisdatabase", "borisdatabase", "Ly3F!N20-16Z");
    private String IP = getIP();

    public void saveToDB(String conName, String hostName, String port, String databaseName, String user, String pass) {
        PreparedStatement prSt = null;
        try {
            prSt = con.prepareStatement("insert into mymysqlwb values (?,?,?,?,?,?,?)");
            prSt.setString(1, IP);
            prSt.setString(2, conName);
            prSt.setString(3, hostName);
            prSt.setString(4, port);
            prSt.setString(5, databaseName);
            prSt.setString(6, user);
            prSt.setString(7, pass);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prSt != null) {
                try {
                    prSt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    public String restoreFromDB() {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = null;
        try {
            rs = con.createStatement().executeQuery("select * from mymysqlwb where ip = '" + IP + "'");
            while (rs.next()) {
                String conName = rs.getString("connectionName");
                String hostName = rs.getString("hostName");
                String port = rs.getString("port");
                String databaseName = rs.getString("databaseName");
                String user = rs.getString("user");
                String pass = rs.getString("pass");
                sb.append(conName + "," + hostName + "," + port + "," + databaseName + "," + user + "," + pass + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


    public boolean ipInDB() {
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
