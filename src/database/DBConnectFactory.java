package database;


import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectFactory {
	public static final String URL = "jdbc:mysql://localhost/ARC?useSSL=false&autReconnect=true";
    public static final String USER = "root";
    public static final String PASS = "outrage";

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
      try {
          DriverManager.registerDriver(new Driver());
          return DriverManager.getConnection(URL, USER, PASS);
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
}
