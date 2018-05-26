import java.sql.Connection;

public class ConnectionInfo {

    private String conName, hostName, port, databaseName, user, pass;
    private Connection connection;

    public ConnectionInfo(String conName, String hostName, String port, String databaseName, String user, String pass, Connection connection){
        this.conName = conName;
        this.hostName = hostName;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.pass = pass;
        this.connection = connection;
    }

    public String getConName(){
        return conName;
    }

    public String getHostName(){
        return hostName;
    }

    public String getPort(){
        return port;
    }

    public String getDatabaseName(){
        return databaseName;
    }

    public String getUser(){
        return user;
    }

    public String getPass(){
        return pass;
    }

    public Connection getConnection(){
        return connection;
    }

    @Override
    public String toString(){
        return conName;
    }
}
