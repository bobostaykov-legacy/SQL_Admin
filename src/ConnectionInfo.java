import java.sql.Connection;

public class ConnectionInfo {

    private String conName, hostName, port, databaseName, user, pass;
    private Connection connection;

    ConnectionInfo(String conName, String hostName, String port, String databaseName, String user, String pass, Connection connection){
        this.conName = conName;
        this.hostName = hostName;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.pass = pass;
        this.connection = connection;
    }

    String getConName(){
        return conName;
    }

    String getHostName(){
        return hostName;
    }

    String getDatabaseName(){
        return databaseName;
    }

    Connection getConnection(){
        return connection;
    }

    @Override
    public String toString(){
        return conName;
    }
}
