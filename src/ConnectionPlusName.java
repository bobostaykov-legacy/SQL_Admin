import java.sql.Connection;

public class ConnectionPlusName {

    private String name;
    private Connection connection;

    public ConnectionPlusName(String name, Connection connection){
        this.name = name;
        this.connection = connection;
    }

    public String getName(){
        return name;
    }

    public Connection getConnection(){
        return connection;
    }

    @Override
    public String toString(){
        return name;
    }
}
